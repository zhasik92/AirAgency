package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.*;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.session.SecurityContextHolder;
import com.netcracker.edu.util.CommandsUtils;
import com.netcracker.edu.util.IdGenerator;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Command
 * Created by Zhassulan on 07.12.2015.
 */
public class BuyTicketToShortestRouteCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(FindRoutesCommand.class);
    private static final DAObject dao = DAOFactory.getDAObject();

    public BuyTicketToShortestRouteCommand() {
        super(User.Roles.USER);
    }

    @Override
    public String getName() {
        return "buy_short_ticket";
    }

    @Override
    protected int execute(String[] parameters, ResultHandler resultHandler) throws IOException {
        if (parameters.length != 5) {
            throw new IllegalArgumentException("required 5 parameters");
        }
        Calendar flightDate = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            City from = dao.findCityByName(parameters[0]);
            City to = dao.findCityByName(parameters[1]);
            flightDate.setTime(df.parse(parameters[2]));
            String passport = parameters[3];
            String citizenship = parameters[4];
            Passenger passenger = dao.findPassengerByPassportNumberAndCitizenship(passport, citizenship);
            if (passenger == null) {
                throw new IllegalArgumentException("wrong passport number or citizenship or passenger haven't registered");
            }
            if (from == null || to == null) {
                logger.warn("illegal cities");
                return 1;
            }
            List<Ticket> tickets = buyTicket(passenger, from, to, flightDate, resultHandler);
            if (tickets == null) {
                logger.warn("Sorry, all tickets have been sold");
                return 1;
            }
            for (Ticket it : tickets) {
                logger.info(it.toString());
            }
            return 0;
        } catch (ParseException | SQLException e) {
            logger.error(e);
            return -1;
        }
    }

    public List<Ticket> buyTicket(Passenger passenger, City from, City to, Calendar flightDate, ResultHandler resultHandler) throws SQLException {
        if (passenger == null || from == null || to == null || flightDate == null) {
            throw new IllegalArgumentException();
        }
        FindShortestRoutesCommand findShortestRoute = (FindShortestRoutesCommand) CommandsEngine.getInstance().getCommand("find_short_route");
        List<Flight> path = findShortestRoute.getPath(from.getName(), to.getName(), "00:00");
        LinkedList<Ticket> currentTickets = new LinkedList<>();
        List<Calendar> flightDates = new LinkedList<>();
        Calendar currentDate = (Calendar) flightDate.clone();
        Flight temp = path.get(0);
        synchronized (dao) {
            for (Flight it : path) {
                if (CommandsUtils.compareTime(temp.getArrivalTime(),it.getDepartureTime())>0) {
                    flightDate.add(Calendar.DATE, 1);
                    currentDate = (Calendar) flightDate.clone();
                }
                flightDates.add(currentDate);
                int airplaneCapacity = dao.findAirplaneByName(it.getAirplaneName()).getCapacity();
                int numberOfSoldTickets = dao.getNumberOfSoldTicketsInFlight(it.getId(), currentDate);
                if (airplaneCapacity - numberOfSoldTickets < 1) {
                    logger.warn("No available tickets in flight, id = " + it.getId() +
                            ", from: " + it.getDepartureAirportName() +
                            ", to: " + it.getArrivalAirportName() +
                            " at: " + currentDate.get(Calendar.YEAR) + " " + currentDate.get(Calendar.MONTH) + " " + currentDate.get(Calendar.DATE));
                    return null;
                }
                temp = it;
            }
            int i = 0;
            for (Flight it : path) {
                Ticket ticket = new Ticket(IdGenerator.getInstance().getId(), passenger.getId(), it.getId(), flightDates.get(i++), Calendar.getInstance());
                logger.trace("Ticket created,  id = " + ticket.getId());
                currentTickets.add(ticket);
                SecurityContextHolder.getLoggedHolder().addTicket(ticket.getId());
            }
            dao.addAllTickets(currentTickets);
            logger.trace("tickets saved");
        }
        currentTickets.forEach(resultHandler::addObject);
        return currentTickets;

    }

    @Override
    public String getHelp() {
        return "Usage: " + getName() + " DEP_CITY ARR_CITY DATE PASSPORT CITIZENSHIP";
    }
}
