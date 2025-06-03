package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class UtilisateurDaoImpl implements IUtilisateurDao {
    private final JdbcTemplate jdbcTemplate;
    public UtilisateurDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Utilisateur> utilisateurRowMapper = (rs, rowNum) -> {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(rs.getInt("id"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setMotPasse(rs.getString("mot_de_passe"));
        // utilisateur.setRole(User.Role.valueOf(rs.getString("role")));
        return utilisateur;
    };

    @Override
    public void save(Utilisateur user) {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getNom(),user.getPrenom(), user.getEmail(), user.getMotPasse());
    }

    @Override
    public Optional<Utilisateur> findById(int id) {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        return jdbcTemplate.query(sql, utilisateurRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        return jdbcTemplate.query(sql, utilisateurRowMapper, email).stream().findFirst();
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM utilisateur";
        return jdbcTemplate.query(sql, utilisateurRowMapper);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
