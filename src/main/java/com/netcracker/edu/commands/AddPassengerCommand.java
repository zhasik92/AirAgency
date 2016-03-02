package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.Passenger;
import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.util.IdGenerator;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Command
 * Created by Zhassulan on 23.10.2015.
 */
public class AddPassengerCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(AddPassengerCommand.class);
    private DAObject dao = DAOFactory.getDAObject();

    public AddPassengerCommand() {
        super(User.Roles.USER);
    }

    @Override
    public String getName() {
        return "add_passenger";
    }

    @Override
    protected int execute(String[] parameters,ResultHandler resultHandler) throws IOException {
        Passenger passenger;
        if ((passenger= createPassenger(parameters)) == null) {
            return 1;
        }
        resultHandler.addObject(passenger);
        return 0;
    }

    public Passenger createPassenger(String[] parameters) throws IOException {
        try {
            if (parameters.length != 6) {
                throw new IllegalArgumentException("six parameters required");
            }
            String passportNumber = parameters[0];
            String citizenship = parameters[1];
            String firstName = parameters[2];
            String lastName = parameters[3];
            Date dateOfBirth = Date.valueOf(parameters[4]);
            String email = parameters[5];

            if (dao.findPassengerByPassportNumberAndCitizenship(passportNumber, citizenship) != null) {
                logger.warn("passenger already exist");
                return null;
            }
            Passenger passenger = new Passenger(IdGenerator.getInstance().getId(), email, firstName, lastName, dateOfBirth, passportNumber, citizenship);
            dao.addPassenger(passenger);
            logger.info("passenger saved, id = " + passenger.getId().toString());
            return passenger;
        } catch (SQLException sqle) {
            logger.error(sqle);
            return null;
        }
    }

    @Override
    public String getHelp() {
        return "AddPassengerCommand usage: " + getName()+" passport citizenship name last_name dateOfBirth email";
    }
}
