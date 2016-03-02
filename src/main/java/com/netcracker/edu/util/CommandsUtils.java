package com.netcracker.edu.util;

import com.netcracker.edu.bobjects.BusinessObject;
import com.netcracker.edu.bobjects.Flight;
import com.netcracker.edu.bobjects.Ticket;
import com.netcracker.edu.commands.AbstractCommand;
import com.netcracker.edu.commands.CommandsEngine;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.session.SecurityContextHolder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

/**
 * Created by Zhassulan on 25.11.2015.
 */

//// TODO: 26.11.2015 Rename class
public class CommandsUtils {
    private final static Logger logger = LogManager.getLogger(CommandsUtils.class);

    public static int compareTime(Time time1, Time time2) {
        long result = DateUtils.getFragmentInMilliseconds(time1, Calendar.DAY_OF_YEAR) - DateUtils.getFragmentInMilliseconds(time2, Calendar.DAY_OF_YEAR);
        System.out.println(result/1000/60);
        if (result == 0) {
            return 0;
        }
        return result > 0 ? 1 : -1;
    }

    public static int parseAndExecuteCommand(String string, ResultHandler<BusinessObject> result) throws IOException {
        String[] splittedCommand = string.toLowerCase().split(" ");
        AbstractCommand command = CommandsEngine.getInstance().getCommand(splittedCommand[0].toLowerCase());
        if (command != null) {
            logger.trace(command);
            return command.execute(Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length), SecurityContextHolder.getLoggedHolder(), result);
        }
        if (splittedCommand[0].equals("help")) {
            CommandsEngine.getInstance().getHelp();
            return 0;
        }
        logger.warn("Unsupported command: "+splittedCommand[0]);
        return -5;
    }

   /* public static boolean checkAndBuyTicketsLogic(DAObject daObject, List<Calendar> flightDates, LinkedList<Flight> path, Collection<Ticket> result){
        for (Flight it : path) {
            if (temp.getArrivalTime().compareTo(it.getDepartureTime()) > 0) {
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
                return false;
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

    }*/
}

