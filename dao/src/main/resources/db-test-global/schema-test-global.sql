CREATE DATABASE IF NOT EXISTS homeDutyTracker_dbTest;

USE homeDutyTracker_dbTest;

CREATE TABLE IF NOT EXISTS utilisateur (
     id INT PRIMARY KEY AUTO_INCREMENT,
     role VARCHAR(20), -- 'parent' ou 'enfant'
     nom VARCHAR(50),
     prenom VARCHAR(50),
     email VARCHAR(100),
     telephone VARCHAR(20),
     mot_de_passe VARCHAR(100)
 );

 CREATE TABLE IF NOT EXISTS  tache (
     id INT PRIMARY KEY AUTO_INCREMENT,
     nom VARCHAR(100),
     description TEXT,
     date_creation DATETIME,
     date_limite DATETIME,
     statut VARCHAR(20),
     createur_id INT,
     responsable_id INT,
     superviseur_id INT,
     FOREIGN KEY (createur_id) REFERENCES utilisateur(id),
     FOREIGN KEY (responsable_id) REFERENCES utilisateur(id),
     FOREIGN KEY (superviseur_id) REFERENCES utilisateur(id)
 );

 CREATE TABLE IF NOT EXISTS  notification (
     id INT PRIMARY KEY AUTO_INCREMENT,
     message VARCHAR(250),
     date_creation DATETIME,
    tache_id INT,
     FOREIGN KEY (tache_id) REFERENCES tache(id)
 );
