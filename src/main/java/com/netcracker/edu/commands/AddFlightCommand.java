package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.Flight;
import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.util.IdGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Command
 * Created by Zhassulan on 03.11.2015.
 */
public class AddFlightCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(AddFlightCommand.class);
    DAObject dao = DAOFactory.getDAObject();

    public AddFlightCommand() {
        super(User.Roles.ADMIN);
    }

    @Override
    public String getName() {
        return "add_flight";
    }

    @Override
    protected int execute(String[] parameters) throws IOException {
        if (parameters.length != 6) {
            throw new IllegalArgumentException("required 6 args");
        }
        String from = parameters[0];
        String to = parameters[1];
        Time departureTime = Time.valueOf(parameters[2]);
        Time arrivalTime = Time.valueOf(parameters[3]);
        String airplane = parameters[4];
        double price = Double.parseDouble(parameters[5]);

        try {
            if (dao.findCityByName(from) == null) {
                logger.warn("city is not found");
                return 1;
            }
            if (dao.findCityByName(to) == null) {
                logger.warn("city is not found");
                return 1;
            }
            if (dao.findAirplaneByName(airplane) == null) {
                logger.warn("No such airplane type");
                return 1;
            }

            Flight flight = new Flight(IdGenerator.getInstance().getId(), from, to, departureTime, arrivalTime, airplane, price);
            dao.addFlight(flight);
            logger.info("flight added, id = " + flight.getId().toString());
            return 0;
        } catch (SQLException sqle) {
            logger.error(sqle);
            return -1;
        }

    }

    @Override
    public String getHelp() {
        return "AddFlightCommand usage: " + getName() + " DEP_CITY ARR_CITY DEP_TIME ARR_TIME AIRPLANE PRICE";
    }
}
