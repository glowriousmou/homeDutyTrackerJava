package com.homeDutyTracker.dao;

import com.homeDutyTracker.domain.Parent;
import com.homeDutyTracker.domain.Tache;
import com.homeDutyTracker.domain.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    public static void addTask(Tache t) throws Exception {
        String sql = "INSERT INTO tache (nom, description, date_creation, date_limite, statut, createur_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, t.getNom());
            stmt.setString(2, t.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(t.getDateCreation()));
            stmt.setTimestamp(4, Timestamp.valueOf(t.getDateLimite()));
            stmt.setString(5, t.getStatut());
            stmt.setInt(6, t.getCreateur().getId());
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

            u.id AS u_id,
            u.nom AS u_nom,
            u.prenom AS u_prenom,
            u.email AS u_email,
            u.telephone AS u_telephone,
            u.mot_de_passe AS u_mot_de_passe,
            u.role AS u_role

        FROM tache t
        JOIN utilisateur u ON t.createur_id = u.id
    """;

        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Parent createur = new Parent(
                        rs.getInt("u_id"),
                        rs.getString("u_nom"),
                        rs.getString("u_prenom"),
                        rs.getString("u_mot_de_passe"),
                        rs.getString("u_email")
                        // rs.getString("u_role")
                );
                Tache tache = new Tache(
                        rs.getInt("t_id"),
                        rs.getString("t_nom"),
                        rs.getString("t_description"),
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
