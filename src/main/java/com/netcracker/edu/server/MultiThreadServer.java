package com.netcracker.edu.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netcracker.edu.bobjects.BusinessObject;
import com.netcracker.edu.commands.CommandsEngine;
import com.netcracker.edu.session.SecurityContextHolder;
import com.netcracker.edu.util.CommandsUtils;
import com.netcracker.edu.util.ResultHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.AccessControlException;
import java.util.LinkedList;
import java.util.List;

/**
 * Multithreaded server
 * Created by Zhassulan on 29.11.2015.
 */
public class MultiThreadServer implements Runnable {
    private static final Logger logger = LogManager.getLogger(MultiThreadServer.class);
    private Socket cSocket;

    public MultiThreadServer(Socket cSocket) {
        this.cSocket = cSocket;
    }

    @Override
    public void run() {
        ResultHandler<BusinessObject> result = new ResultHandler<>();
        try (PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()))) {
            String input;
            while (!Thread.currentThread().isInterrupted() && (input = in.readLine()) != null) {
                try {
                    if (Server.isStopped) {
                        CommandsEngine.getInstance().getCommand("quit").execute(null, SecurityContextHolder.getLoggedHolder(), result);
                        continue;
                    }
                    // out.println(CommandsUtils.parseAndExecuteCommand(input,result));
                    result.setStatus(CommandsUtils.parseAndExecuteCommand(input, result));
                    result.setUsersRole(SecurityContextHolder.getLoggedHolder());
                    out.println(result);
                    result.clear();

                } catch (IllegalArgumentException | AccessControlException e) {
                    logger.error(e);
                    out.println(e);
                }
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                if (SecurityContextHolder.getLoggedHolder() != null) {
                    CommandsEngine.getInstance().getCommand("quit").execute(null, SecurityContextHolder.getLoggedHolder(), result);
                }
                cSocket.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
