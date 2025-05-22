package com.oumou.homeDutyTracker.dao;

import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Enfant;
import com.oumou.homeDutyTracker.domain.Parent;
import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.domain.enumeration.StatutTache;
import com.oumou.homeDutyTracker.service.TacheServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    public static int addTask(Tache t) throws Exception {
        String sql = "INSERT INTO tache (nom, description, date_creation, date_limite, statut, createur_id,superviseur_id, responsable_id) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, t.getNom());
            stmt.setString(2, t.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(t.getDateCreation()));
            stmt.setTimestamp(4, Timestamp.valueOf(t.getDateLimite()));
            stmt.setString(5, t.getStatut().toString());
            stmt.setInt(6, t.getCreateur().getId());
            stmt.setInt(7, t.getSuperviseur().getId());
            stmt.setInt(8, t.getResponsable().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static List<Tache> getAllTask() {
        List<Tache> listTaches = new ArrayList<>();
        String sql = """
        SELECT 
            t.id AS t_id,
            t.nom AS t_nom,
            t.description AS t_description,
            t.date_creation AS t_date_creation,
            t.date_limite AS t_date_limite,
            t.statut AS t_statut,

             -- Infos du créateur
              c.id AS createur_id,
              c.nom AS createur_nom,
              c.prenom AS createur_prenom,
              c.mot_de_passe AS createur_mot_de_passe,
              c.email AS createur_email,

              -- Infos du superviseur
              s.id AS superviseur_id,
              s.nom AS superviseur_nom,
              s.prenom AS superviseur_prenom,
              s.mot_de_passe AS superviseur_mot_de_passe,
              s.email AS superviseur_email,

              -- Infos du responsable
              r.id AS responsable_id,
              r.nom AS responsable_nom,
              r.prenom AS responsable_prenom,
              r.mot_de_passe AS responsable_mot_de_passe,
              r.email AS responsable_email

          FROM tache t

          LEFT JOIN utilisateur c ON t.createur_id = c.id
          LEFT JOIN utilisateur s ON t.superviseur_id = s.id
          LEFT JOIN utilisateur r ON t.responsable_id = r.id;
    """;

        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Parent createur = new Parent(
                        rs.getInt("createur_id"),
                        rs.getString("createur_nom"),
                        rs.getString("createur_prenom"),
                        rs.getString("createur_mot_de_passe"),
                        rs.getString("createur_email")
                );
                Enfant responsable = new Enfant(
                        rs.getInt("responsable_id"),
                        rs.getString("responsable_nom"),
                        rs.getString("responsable_prenom"),
                        rs.getString("responsable_mot_de_passe"),
                        rs.getString("responsable_email")
                );
                Enfant superviseur = new Enfant(
                        rs.getInt("superviseur_id"),
                        rs.getString("superviseur_nom"),
                        rs.getString("superviseur_prenom"),
                        rs.getString("superviseur_mot_de_passe"),
                        rs.getString("superviseur_email")
                );
                StatutTache statutTache = TacheServiceImpl.fromStringToEnum(rs.getString("t_statut"), StatutTache.INITIALIZED);;
                Tache tache = new Tache(
                        rs.getInt("t_id"),
                        rs.getString("t_nom"),
                        rs.getString("t_description"),
                        responsable,
                        superviseur,
                        createur,
                        statutTache,
                        rs.getTimestamp("t_date_creation").toLocalDateTime(),
                        rs.getTimestamp("t_date_limite").toLocalDateTime()
                );

                listTaches.add(tache);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des tâches : " + e.getMessage());
            e.printStackTrace(); // ou log dans un fichier avec log4j/slf4j
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }

        return listTaches;
    }

    public static List<Tache> getAllTask(Utilisateur utilisateur) {
        List<Tache> listTaches = new ArrayList<>();

        String sql = """
        SELECT 
            t.id AS t_id,
            t.nom AS t_nom,
            t.description AS t_description,
            t.date_creation AS t_date_creation,
            t.date_limite AS t_date_limite,
            t.statut AS t_statut,

            -- Infos du créateur
            c.id AS createur_id,
            c.nom AS createur_nom,
            c.prenom AS createur_prenom,
            c.mot_de_passe AS createur_mot_de_passe,
            c.email AS createur_email,

            -- Infos du superviseur
            s.id AS superviseur_id,
            s.nom AS superviseur_nom,
            s.prenom AS superviseur_prenom,
            s.mot_de_passe AS superviseur_mot_de_passe,
            s.email AS superviseur_email,

            -- Infos du responsable
            r.id AS responsable_id,
            r.nom AS responsable_nom,
            r.prenom AS responsable_prenom,
            r.mot_de_passe AS responsable_mot_de_passe,
            r.email AS responsable_email

        FROM tache t
        LEFT JOIN utilisateur c ON t.createur_id = c.id
        LEFT JOIN utilisateur s ON t.superviseur_id = s.id
        LEFT JOIN utilisateur r ON t.responsable_id = r.id
        WHERE t.createur_id = ? OR t.superviseur_id = ? OR t.responsable_id = ?
    """;

        try (
                Connection con = ConnexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            // On injecte 3 fois l'ID de l'utilisateur dans la requête préparée
            ps.setInt(1, utilisateur.getId());
            ps.setInt(2, utilisateur.getId());
            ps.setInt(3, utilisateur.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Parent createur = new Parent(
                            rs.getInt("createur_id"),
                            rs.getString("createur_nom"),
                            rs.getString("createur_prenom"),
                            rs.getString("createur_mot_de_passe"),
                            rs.getString("createur_email")
                    );
                    Enfant responsable = new Enfant(
                            rs.getInt("responsable_id"),
                            rs.getString("responsable_nom"),
                            rs.getString("responsable_prenom"),
                            rs.getString("responsable_mot_de_passe"),
                            rs.getString("responsable_email")
                    );
                    Enfant superviseur = new Enfant(
                            rs.getInt("superviseur_id"),
                            rs.getString("superviseur_nom"),
                            rs.getString("superviseur_prenom"),
                            rs.getString("superviseur_mot_de_passe"),
                            rs.getString("superviseur_email")
                    );

                    StatutTache statutTache = TacheServiceImpl.fromStringToEnum(
                            rs.getString("t_statut"),
                            StatutTache.INITIALIZED
                    );

                    Tache tache = new Tache(
                            rs.getInt("t_id"),
                            rs.getString("t_nom"),
                            rs.getString("t_description"),
                            responsable,
                            superviseur,
                            createur,
                            statutTache,
                            rs.getTimestamp("t_date_creation").toLocalDateTime(),
                            rs.getTimestamp("t_date_limite").toLocalDateTime()
                    );

                    listTaches.add(tache);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des tâches : " + e.getMessage());
            e.printStackTrace(); // ou log dans un fichier avec log4j/slf4j
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }

        return listTaches;
    }

}
