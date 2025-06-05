package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UtilisateurDaoImpl implements IUtilisateurDao {
    private static final Logger logger = Logger.getLogger(UtilisateurDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    public UtilisateurDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Utilisateur> utilisateurRowMapper = (rs, rowNum) -> {
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur.setId(rs.getInt("id"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setMotPasse(rs.getString("mot_de_passe"));
        } catch (Exception e) {
        logger.error("Erreur inconnue lors du mapping d'un utilisateur. "+ e);
    }
        return utilisateur;
    };

   @Override
    public int save(Utilisateur user)  {
        try {
            String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
            logger.info("eeee "+user.getNom());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            // jdbcTemplate.update(sql, user.getNom(), user.getPrenom(), user.getEmail(), user.getMotPasse());
            String password= hashPassword( user.getMotPasse());
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getNom());
                ps.setString(2, user.getPrenom());
                ps.setString(3, user.getEmail());
                ps.setString(4,password);
                return ps;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (DataAccessException e) {
                logger.error("Erreur SQL : impossible de créer un utilisateur "+ e);
            } catch (Exception e) {
                logger.error("Erreur inconnue lors de la création d'un utilisateur. "+ e);
            }
       return -1;
}
   /* @Override
    public Optional<Utilisateur> findById(int id) {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        return jdbcTemplate.query(sql, utilisateurRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        return jdbcTemplate.query(sql, utilisateurRowMapper, email).stream().findFirst();
    }*/

    @Override
    public List<Utilisateur> findAll() {
        try {
        String sql = "SELECT * FROM utilisateur";
        return jdbcTemplate.query(sql, utilisateurRowMapper);
        }catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de lister  utilisateur "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lister  utilisateur. "+ e);
        }
        return Collections.emptyList();
    }

   @Override
    public void deleteById(int id) {
        try {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de lister  utilisateur "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lister  utilisateur. "+ e);
        }
    }
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
