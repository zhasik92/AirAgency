package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.dao.DAOFactory;
import com.netcracker.edu.dao.DAObject;
import com.netcracker.edu.session.SecurityContextHolder;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Zhassulan on 19.11.2015.
 */
public class SignInCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);
    private DAObject dao = DAOFactory.getDAObject();

    public SignInCommand() {
        super(User.Roles.USER);
    }

    @Override
    public String getName() {
        return "sign_in";
    }

    @Override
    public int execute(String[] parameters, User user, ResultHandler result) throws IOException {
        if (user != null) {
            throw new AccessControlException("quit first");
        }
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
            if (user == null || !Arrays.equals(user.getPassword(), password)) {
                logger.warn("Login and password are incorrect");
                return 1;
            }
            SecurityContextHolder.setLoggedUser(user);
            logger.info("signed in");
            return 0;
        } catch (SQLException sqle) {
            logger.error(sqle);
            return -1;
        }
    }

    @Override
    public String getHelp() {
        return "SignInCommand usage:" + "\"" + getName() + " login password\"";
    }
}
