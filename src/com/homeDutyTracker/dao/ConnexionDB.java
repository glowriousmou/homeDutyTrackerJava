package com.homeDutyTracker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnexionDB {
    private static final String DB_NAME = "homeDutyTracker_db";
    private static final String URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            // 1. Créer la base si elle n'existe pas
            Connection con = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            con.close();

            // 2. Créer les tables
            initialiserTables();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL_BASE + DB_NAME, USER, PASSWORD);
    }

    public static void initialiserTables() {
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