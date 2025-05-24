package com.oumou.homeDutyTracker.dao;

import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.dao.interfaces.IUtilisateurDAO;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import org.apache.log4j.Logger;

import java.sql.*;

public class UtilisateurDAOImpl implements IUtilisateurDAO {
    private static final Logger logger = Logger.getLogger(UtilisateurDAOImpl.class);
    public  int create(Utilisateur u, String role) throws SQLException {
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
        } catch (SQLException e) {
            logger.error("Erreur SQL : impossible de créer un utilisateur "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la création d'un utilisateur. "+ e);
        }
        return -1;
    }
}
