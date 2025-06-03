package com.oslead.solutions.homeDutyTracker.dao.database;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class ConnexionDB {
    private static  String DB_NAME = "";
    private  static String URL_BASE = "";
    private static  String USER = "";
    private  static String PASSWORD = "";


    private static final Logger logger = Logger.getLogger(ConnexionDB.class);

    static {
        try {

            //logger.debug("Message DEBUG");
            //logger.warn("Message WARN");
            initializeDB();
        } catch (Exception e) {
            logger.fatal("Message FATAL "+ e);

        }
    }

    public static void initializeDB() {
        Properties props = new Properties();

        // try (FileInputStream input = new FileInputStream("config/db.properties")) {
        try (InputStream input = ConnexionDB.class
                .getClassLoader()
                .getResourceAsStream("db/db.properties")) {
            if (input == null) {
                //throw new IllegalStateException("db.properties introuvable");
                logger.error("InitializeDB db.properties introuvable");
            }
            //dbProps.load(is);
            props.load(input);


            DB_NAME = props.getProperty("db.name");
            URL_BASE = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            // logger.info("database info "+ URL_BASE+" "+USER);
            try (Connection connectionDB = DriverManager.getConnection(URL_BASE, USER, PASSWORD)) {
                logger.info("Connexion réussie !");
                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            initializeTables();

            } catch (SQLException e) {
                logger.error("Erreur SQL : impossible de créer ou de se connecter à la base.");
            } catch (Exception e) {
                logger.error("Erreur inconnue lors de l'initialisation.");
            }
        } catch (IOException ex) {
            //throw new RuntimeException(ex);
            logger.error("Erreur inconnue InitializeDB.");
        }


    }


    public static Connection getConnection() throws Exception {
        try {
        return DriverManager.getConnection(URL_BASE + DB_NAME, USER, PASSWORD);
        } catch (SQLException e) {
            // Ne surtout pas faire e.printStackTrace()
            logger.error("Erreur de connexion à la base de données : " + e.getMessage());
            return null; // ou lever une exception métier
        }
    }

    public static void initializeTables() {
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS utilisateur (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    role VARCHAR(20),
                    nom VARCHAR(50),
                    prenom VARCHAR(50),
                    email VARCHAR(100),
                    telephone VARCHAR(20),
                    mot_de_passe VARCHAR(100)
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tache (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nom VARCHAR(100),
                    description TEXT,
                    date_creation DATETIME,
                    date_limite DATETIME,
                    statut VARCHAR(20),
                    createur_id INT,
                    superviseur_id INT,
                    responsable_id INT,
                    FOREIGN KEY (createur_id) REFERENCES utilisateur(id),
                    FOREIGN KEY (superviseur_id) REFERENCES utilisateur(id),
                    FOREIGN KEY (responsable_id) REFERENCES utilisateur(id)
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS notification (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    message VARCHAR(500),
                    date_creation DATETIME,
                    tache_id INT,
                    FOREIGN KEY (tache_id) REFERENCES tache(id)
                );
            """);


        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("Erreur lors de l'initialisation des table : " + e.getMessage());
        }
    }
}