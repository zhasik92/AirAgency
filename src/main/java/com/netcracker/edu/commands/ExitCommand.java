package com.netcracker.edu.commands;

import com.netcracker.edu.bobjects.User;
import com.netcracker.edu.server.Server;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command
 * Created by Zhassulan on 23.10.2015.
 */
public class ExitCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(ExitCommand.class);

    public ExitCommand() {
        super(User.Roles.ADMIN);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    protected synchronized int execute(String[] parameters, ResultHandler resultHandler) throws IOException {
        Server.isStopped=true;
        return 0;
    }

    @Override
    public String getHelp() {
        return "ExitCommand usage: " + getName();
    }
}
