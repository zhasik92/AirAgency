package com.netcracker.edu.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Zhassulan on 08.01.2016.
 */
public class PropertiesHandler {
    private static final Logger logger = LogManager.getLogger(PropertiesHandler.class);
    private static final Properties properties = new Properties();
    private static  String DBURL;
    private static  String DAO;
    private static  String DATABASE_LOGIN;
    private static  char[] DATABASE_PASSWORD;
    private static  int JDBC_POOL_SIZE;
    private static String propertyPath="src/main/resources/config.properties";
    private PropertiesHandler() {

    }

  public static void init() {
        try {
            // TODO: 08.01.2016 where to store configs in production?
            FileInputStream fis = new FileInputStream(propertyPath);
            properties.load(fis);
        } catch (IOException e) {
            logger.error(e);
            System.exit(-1);
        }
        DAO = properties.getProperty("DAO");
        DBURL = properties.getProperty("DBURL");
        DATABASE_LOGIN = properties.getProperty("LOGIN");
        DATABASE_PASSWORD = properties.getProperty("PASSWORD").toCharArray();
        JDBC_POOL_SIZE = Integer.parseInt(properties.getProperty("JDBC_POOL_SIZE"));
        properties.values().forEach(logger::trace);
    }

    public static void setPropertyFile(String path){
        propertyPath=path;
    }
    public static String getDAO() {
        return DAO;
    }

    public static char[] getDatabasePassword() {
        return DATABASE_PASSWORD;
    }

    public static String getDatabaseLogin() {
        return DATABASE_LOGIN;
    }

    public static String getDBURL() {
        return DBURL;
    }
    public static int getJdbcPoolSize(){
        return JDBC_POOL_SIZE;
    }
}
