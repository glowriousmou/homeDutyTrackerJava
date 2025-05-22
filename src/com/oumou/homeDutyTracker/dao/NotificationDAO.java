package com.oumou.homeDutyTracker.dao;

import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Enfant;
import com.oumou.homeDutyTracker.domain.Notification;
import com.oumou.homeDutyTracker.domain.Tache;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public static int addNotification(Notification notification) throws Exception {
        String sql = "INSERT INTO notification (message, date_creation, tache_id) VALUES (?, ?, ?)";
        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, notification.getMessage());
            stmt.setTimestamp(2, Timestamp.valueOf(notification.getDateCreation()));
            stmt.setInt(3, notification.getTache().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static List<Notification> getAllNotification() {
        List<Notification> listNotification = new ArrayList<>();
        String sql = """
        SELECT 
            n.id AS n_id,
            n.message AS n_message,
            n.date_creation AS n_dateCreation,

             -- Infos de la tache
              t.id AS tache_id,
              t.nom AS tache_nom,
               
               
  -- Infos du responsable
              r.id AS responsable_id,
              r.nom AS responsable_nom,
              r.prenom AS responsable_prenom,
              r.mot_de_passe AS responsable_mot_de_passe,
              r.email AS responsable_email

          FROM notification n

          LEFT JOIN tache t ON n.tache_id = t.id
          LEFT JOIN utilisateur r ON t.responsable_id = r.id;
    """;

        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Enfant responsable = new Enfant(
                        rs.getInt("responsable_id"),
                        rs.getString("responsable_nom"),
                        rs.getString("responsable_prenom"),
                        rs.getString("responsable_mot_de_passe"),
                        rs.getString("responsable_email")
                );
                Tache tache = new Tache(
                        rs.getInt("tache_id"),
                        rs.getString("tache_nom"),
                        responsable
                );
                Notification notification = new Notification(
                        rs.getInt("n_id"),
                        rs.getString("n_message"),
                        rs.getTimestamp("n_dateCreation").toLocalDateTime(),
                        tache
                );

                listNotification.add(notification);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des tâches : " + e.getMessage());
            e.printStackTrace(); // ou log dans un fichier avec log4j/slf4j
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }

        return listNotification;
    }

}
