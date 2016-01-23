package com.netcracker.edu.util;

import com.netcracker.edu.commands.AbstractCommand;
import com.netcracker.edu.commands.CommandsEngine;
import com.netcracker.edu.session.SecurityContextHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Zhassulan on 25.11.2015.
 */

//// TODO: 26.11.2015 Rename class
public class CommandsReader {
    private final static Logger logger = LogManager.getLogger(CommandsReader.class);

    public static int parseAndExecuteCommand(String string) throws IOException {
        String[] splittedCommand = string.toLowerCase().split(" ");
        AbstractCommand command = CommandsEngine.getInstance().getCommand(splittedCommand[0].toLowerCase());
        if (command != null) {
            logger.trace(command);
            return command.execute(Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length), SecurityContextHolder.getLoggedHolder());
        }
        if (splittedCommand[0].equals("help")) {
            CommandsEngine.getInstance().getHelp();
            return 0;
        }
        logger.warn("Unsupported command");
        return -5;
    }
}

