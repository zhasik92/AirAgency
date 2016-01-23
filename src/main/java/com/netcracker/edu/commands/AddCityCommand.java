package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.City;
import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Command
 * Created by Zhassulan on 03.11.2015.
 */
public class AddCityCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(AddCityCommand.class);
    private static DAObject dao = DAOFactory.getDAObject();

    public AddCityCommand() {
        super(User.Roles.ADMIN);
    }

    @Override
    public String getName() {
        return "add_city";
    }

    @Override
    protected int execute(String[] parameters) throws IOException {
        if (parameters.length != 1 || parameters[0].isEmpty()) {
            throw new IllegalArgumentException("City name is required");
        }
        try {
            City city = dao.findCityByName(parameters[0]);
            if (city != null) {
                logger.info("City already exist");
                return 1;
            }

            city = new City(parameters[0]);
            dao.addCity(city);
            logger.info("city added");
            return 0;
        } catch (SQLException sqle) {
            logger.error(sqle);
            return -1;
        }
    }

    @Override
    public String getHelp() {
        return "AddCityCommand usage: " + "\"" + getName() + "\"";
    }
}
