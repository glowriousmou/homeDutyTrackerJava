package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
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

public class TacheDaoImpl implements ITacheDao {
    private static final Logger logger = Logger.getLogger(TacheDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    public TacheDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Tache> tacheRowMapper = (rs, rowNum) -> {
        Tache tache= new Tache();
        try {
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
            StatutTache statutTache = fromStringToEnum(rs.getString("t_statut"), StatutTache.INITIALIZED);
            ;

            tache.setId(rs.getInt("t_id"));
            tache.setNom(rs.getString("t_nom"));
            tache.setDescription(rs.getString("t_description"));
            tache.setResponsable(responsable);
            tache.setSuperviseur(superviseur);
            tache.setCreateur(createur);
            tache.setStatut(statutTache);
            tache.setDateCreation(  rs.getTimestamp("t_date_creation").toLocalDateTime());
            tache.setDateLimite(rs.getTimestamp("t_date_limite").toLocalDateTime());


        }catch (Exception e) {
            logger.error("Erreur inconnue du mapping d'une tache. "+ e);
        }
        return tache;
    };
    public static StatutTache fromStringToEnum(String value, StatutTache defaultValue) {
        try {
            return StatutTache.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }

    @Override
    public int save(Tache tache)  {
        try {
            String sql ="INSERT INTO tache (nom, description, date_creation, date_limite, statut, createur_id,superviseur_id, responsable_id) VALUES (?, ?, ?, ?, ?, ?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tache.getNom());
                ps.setString(2, tache.getDescription());
                ps.setTimestamp(3, Timestamp.valueOf(tache.getDateCreation()));
                ps.setTimestamp(4, Timestamp.valueOf(tache.getDateLimite()));
                ps.setString(5, tache.getStatut().toString());
                ps.setInt(6, tache.getCreateur().getId());
                ps.setInt(7, tache.getSuperviseur().getId());
                ps.setInt(8, tache.getResponsable().getId());
                return ps;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de créer une tache "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors de la création d'une tache. "+ e);
        }
        return -1;
    }

    @Override
    public List<Tache> findAll() {
        try {
            String sql = "SELECT \n" +
                    "            t.id AS t_id,\n" +
                    "            t.nom AS t_nom,\n" +
                    "            t.description AS t_description,\n" +
                    "            t.date_creation AS t_date_creation,\n" +
                    "            t.date_limite AS t_date_limite,\n" +
                    "            t.statut AS t_statut,\n" +
                    "\n" +
                    "             -- Infos du créateur\n" +
                    "              c.id AS createur_id,\n" +
                    "              c.nom AS createur_nom,\n" +
                    "              c.prenom AS createur_prenom,\n" +
                    "              c.mot_de_passe AS createur_mot_de_passe,\n" +
                    "              c.email AS createur_email,\n" +
                    "\n" +
                    "              -- Infos du superviseur\n" +
                    "              s.id AS superviseur_id,\n" +
                    "              s.nom AS superviseur_nom,\n" +
                    "              s.prenom AS superviseur_prenom,\n" +
                    "              s.mot_de_passe AS superviseur_mot_de_passe,\n" +
                    "              s.email AS superviseur_email,\n" +
                    "\n" +
                    "              -- Infos du responsable\n" +
                    "              r.id AS responsable_id,\n" +
                    "              r.nom AS responsable_nom,\n" +
                    "              r.prenom AS responsable_prenom,\n" +
                    "              r.mot_de_passe AS responsable_mot_de_passe,\n" +
                    "              r.email AS responsable_email\n" +
                    "\n" +
                    "          FROM tache t\n" +
                    "\n" +
                    "          LEFT JOIN utilisateur c ON t.createur_id = c.id\n" +
                    "          LEFT JOIN utilisateur s ON t.superviseur_id = s.id\n" +
                    "          LEFT JOIN utilisateur r ON t.responsable_id = r.id;";
            return jdbcTemplate.query(sql, tacheRowMapper);
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
            String sql = "DELETE FROM tache WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }catch (DataAccessException e) {
            logger.error("Erreur SQL : impossible de lister  tache "+ e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lister  tache. "+ e);
        }
    }
}
