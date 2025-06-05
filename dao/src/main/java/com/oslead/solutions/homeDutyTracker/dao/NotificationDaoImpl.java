package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.INotificationDao;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NotificationDaoImpl implements INotificationDao {
    private static final Logger logger = Logger.getLogger(NotificationDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    public NotificationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> {
        Notification notification = new Notification();
        try {
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
            notification.setIdentifiant(rs.getInt("n_id"));
            notification.setMessage(rs.getString("n_message"));
            notification.setDateCreation(  rs.getTimestamp("n_date_creation").toLocalDateTime());
            notification.setTache(tache);

        } catch (Exception e) {
            logger.error("Erreur inconnue lors du mapping d'une notification. "+ e);
        }
        return notification;
    };

    @Override
    public int save(Notification notification)  {
        try {
            String sql = "INSERT INTO notification (message, date_creation, tache_id) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();


            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, notification.getMessage());
                ps.setTimestamp(2, Timestamp.valueOf(notification.getDateCreation()));
                ps.setInt(3, notification.getTache().getId());
                return ps;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de créer une notification "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la création d'une notification. "+ e);
        }
        return -1;
    }

    @Override
    public List<Notification> findAll() {
        try {
            String sql = " SELECT \n" +
                    "            n.id AS n_id,\n" +
                    "            n.message AS n_message,\n" +
                    "            n.date_creation AS n_dateCreation,\n" +
                    "\n" +
                    "             -- Infos de la tache\n" +
                    "              t.id AS tache_id,\n" +
                    "              t.nom AS tache_nom,\n" +
                    "               \n" +
                    "               \n" +
                    "  -- Infos du responsable\n" +
                    "              r.id AS responsable_id,\n" +
                    "              r.nom AS responsable_nom,\n" +
                    "              r.prenom AS responsable_prenom,\n" +
                    "              r.mot_de_passe AS responsable_mot_de_passe,\n" +
                    "              r.email AS responsable_email\n" +
                    "\n" +
                    "          FROM notification n\n" +
                    "\n" +
                    "          LEFT JOIN tache t ON n.tache_id = t.id\n" +
                    "          LEFT JOIN utilisateur r ON t.responsable_id = r.id;";
            return jdbcTemplate.query(sql, notificationRowMapper);
        }catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de lister  notification "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lister  notification. "+ e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(int id) {
        try {
            String sql = "DELETE FROM notification WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de lister  notification "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lister  notification. "+ e);
        }
    }

}
