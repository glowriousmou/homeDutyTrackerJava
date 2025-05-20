package com.oumou.homeDutyTracker.dao;

import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UtilisateurDAO {
    public static int addUser(Utilisateur u, String role) throws Exception {
        String sql = "INSERT INTO utilisateur (role, nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnexionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role);
            stmt.setString(2, u.getNom());
            stmt.setString(3, u.getPrenom());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getMotPasse());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
}
