package com.oslead.solutions.homeDutyTracker.dao;


import com.oslead.solutions.homeDutyTracker.dao.database.ConnexionDB;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDAO;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAOImpl implements ITacheDAO {
    private static final Logger logger = Logger.getLogger(TacheDAOImpl.class);
    public int create(Tache t) throws SQLException {
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
        }  catch (SQLException e) {
            logger.error("Erreur SQL : impossible de créer une tache "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la création d'une tache. "+ e);
        }
        return -1;
    }

    public  List<Tache> getAll() {
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
                StatutTache statutTache = fromStringToEnum(rs.getString("t_statut"), StatutTache.INITIALIZED);;
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

        }  catch (SQLException e) {
            logger.error("Erreur SQL : impossible de récupérer la liste des taches "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la récupération de la liste des taches. "+ e);
        }

        return listTaches;
    }

    public  List<Tache> getAll(Utilisateur utilisateur) {
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

                    StatutTache statutTache = fromStringToEnum(
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

        }   catch (SQLException e) {
            logger.error("Erreur SQL : impossible de récupérer la liste des taches par utilisateurs "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la récupération de la liste des taches par utilisateurs. "+ e);
        }

        return listTaches;
    }
    public static StatutTache fromStringToEnum(String value, StatutTache defaultValue) {
        try {
            return StatutTache.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }

}
