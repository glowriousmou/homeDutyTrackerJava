package com.oumou.homeDutyTracker.dao.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import  org.apache.log4j.Logger;

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
            logger.fatal("Message FATAL");
            e.printStackTrace();
        }
    }
    public static void initializeDBd() {

        try  {
            FileInputStream input = new FileInputStream("config/db.propertiesf");
            if (input == null) {
                System.out.println("Fichier db.properties non trouvé !");
                return;
            }
            Properties props = new Properties();
            props.load(input);

            DB_NAME = props.getProperty("db.name");
            URL_BASE = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            Connection connectionDB = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
            System.out.println("Connexion réussie !");

            // 1. Créer la base si elle n'existe pas
            Statement stmt = connectionDB.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            connectionDB.close();

            // 2. Créer les tables
            initializeTables();
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier de config");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur de connexion à la base");
            e.printStackTrace();
        }
    }
    public static void initializeDB() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("config/db.properties")) {
            // Chargement du fichier
            props.load(input);

            DB_NAME = props.getProperty("db.name");
            URL_BASE = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            try (Connection connectionDB = DriverManager.getConnection(URL_BASE, USER, PASSWORD)) {
                // System.out.println("Connexion réussie !");
                logger.info("Connexion réussie !");

                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            }

            initializeTables();

        } catch (FileNotFoundException e) {
            // System.out.println("Fichier de configuration introuvable.");
            logger.error("Fichier de configuration introuvable.");
        } catch (IOException e) {
            //System.out.println("Erreur lors de la lecture du fichier de configuration.");
            logger.error("Erreur lors de la lecture du fichier de configuration.");
        } catch (SQLException e) {
            //System.out.println("Erreur lors de la connexion ou la création de la base de données.");
            logger.error("Erreur lors de la connexion ou la création de la base de données.");
        } catch (Exception e) {
            //System.out.println("Erreur inconnue lors de l'initialisation.");
            logger.error("Erreur inconnue lors de l'initialisation.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL_BASE + DB_NAME, USER, PASSWORD);
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
                    FOREIGN KEY (createur_id) REFERENCES utilisateur(id)
                );
            """);

            // Tu peux continuer ici avec les tables : assignation, notification, etc.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}