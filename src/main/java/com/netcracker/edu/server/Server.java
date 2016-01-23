package com.netcracker.edu.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket server
 * Created by Zhassulan on 29.11.2015.
 */
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static final int PORT_NUMBER = 4444;
    public static volatile boolean isStopped;

    public static void execute() throws IOException {
        try (ServerSocket sSock = new ServerSocket(PORT_NUMBER)) {
            logger.info("Listening");
            isStopped=false;
            // TODO: 21.01.2016
            while (!isStopped) {
                logger.info("Waiting for client");
                Socket sock = sSock.accept();
                logger.info("Connected");
                new Thread(new MultiThreadServer(sock)).start();
            }
        }
    }
}
