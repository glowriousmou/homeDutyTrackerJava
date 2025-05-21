package com.oumou.homeDutyTracker.dao;

import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Enfant;
import com.oumou.homeDutyTracker.domain.Parent;
import com.oumou.homeDutyTracker.domain.Tache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    public static void addTask(Tache t) throws Exception {
        String sql = "INSERT INTO tache (nom, description, date_creation, date_limite, statut, createur_id,superviseur_id, responsable_id) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, t.getNom());
            stmt.setString(2, t.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(t.getDateCreation()));
            stmt.setTimestamp(4, Timestamp.valueOf(t.getDateLimite()));
            stmt.setString(5, t.getStatut());
            stmt.setInt(6, t.getCreateur().getId());
            stmt.setInt(7, t.getSuperviseur().getId());
            stmt.setInt(8, t.getResponsable().getId());
            stmt.executeUpdate();
        }
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
                Tache tache = new Tache(
                        rs.getInt("t_id"),
                        rs.getString("t_nom"),
                        rs.getString("t_description"),
                        responsable,
                        superviseur,
                        createur,
                        rs.getString("t_statut"),
                        rs.getTimestamp("t_date_creation").toLocalDateTime(),
                        rs.getTimestamp("t_date_limite").toLocalDateTime()
                );

                listTaches.add(tache);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTaches;
    }
}
