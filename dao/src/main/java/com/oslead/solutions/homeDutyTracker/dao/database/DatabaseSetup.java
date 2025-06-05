package com.oslead.solutions.homeDutyTracker.dao.database;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseSetup {
    private static final Logger logger = Logger.getLogger(DatabaseSetup.class);
    public static void createDatabaseIfNotExists() {

        try (InputStream input = DatabaseSetup.class.getClassLoader().getResourceAsStream("db/db.properties")) {

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String dbName = props.getProperty("db.name");
            //String baseUrl = fullUrl.substring(0, fullUrl.indexOf(dbName));
            String username = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
                logger.info("Database creates: " + dbName);

            }

        } catch (Exception e) {
           // stem.err.println("❌ Error loading db.properties or creating database: " + e.getMessage());
            logger.error("Error loading db.properties or creating database "+ e);
            throw new RuntimeException(e);
        }
    }
}
