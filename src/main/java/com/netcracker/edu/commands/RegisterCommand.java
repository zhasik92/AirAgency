package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Command
 * Created by Zhassulan on 19.11.2015.
 */
public class RegisterCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static DAObject dao = DAOFactory.getDAObject();

    public RegisterCommand() {
        super(User.Roles.USER);
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public int execute(String[] parameters, User user, ResultHandler result) throws IOException {
        return execute(parameters,result);
    }

    @Override
    protected int execute(String[] parameters,ResultHandler resultHandler) throws IOException {
        if (parameters.length != 2) {
            throw new IllegalArgumentException("required 2 parameters");
        }
        String login = parameters[0];
        char[] password = parameters[1].toCharArray();
        try {
            User user = dao.findUserByLogin(login);
            if (user != null) {
                logger.warn("login already registered");
                return 1;
            }
            user = createUser(login, password);
            dao.addUser(user);
            logger.info("successfully registered");
            return 0;
        } catch (SQLException sqle) {
            logger.error(sqle);
            return -1;
        }
    }

    public User createUser(String login, char[] password) {
        return new User(login, password);
    }

    @Override
    public String getHelp() {
        return getName() + " login password";
    }
}
