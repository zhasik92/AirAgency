package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.session.SecurityContextHolder;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Zhassulan on 04.12.2015.
 */
public class QuitCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(QuitCommand.class);

    public QuitCommand() {
        super(User.Roles.USER);
    }

    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public int execute(String[] parameters, User user, ResultHandler resultHandler) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null, sign in first");
        }
        try {
            DAOFactory.getDAObject().updateUser(user);
        } catch (SQLException e) {
            logger.error(e);
        }
        execute(parameters,resultHandler);
        Thread.currentThread().interrupt();
        return 0;
    }

    @Override
    protected int execute(String[] parameters,ResultHandler resultHandler) throws IOException {
        SecurityContextHolder.removeUserFromSignedUsers();
        SecurityContextHolder.setLoggedUser(null);
        return 0;
    }

    @Override
    public String getHelp() {
        return "quit";
    }
}
