package com.netcracker.edu.server;

import com.netcracker.edu.commands.CommandsEngine;
import com.netcracker.edu.session.SecurityContextHolder;
import com.netcracker.edu.util.CommandsReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.AccessControlException;

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
        try (PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()))) {
            String input;
            while (!Thread.currentThread().isInterrupted()&&(input = in.readLine()) != null ) {
                try {
                    if (Server.isStopped) {
                        CommandsEngine.getInstance().getCommand("quit").execute(null, SecurityContextHolder.getLoggedHolder());
                        continue;
                    }
                    out.println(CommandsReader.parseAndExecuteCommand(input));
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
                    CommandsEngine.getInstance().getCommand("quit").execute(null, SecurityContextHolder.getLoggedHolder());
                }
                cSocket.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
