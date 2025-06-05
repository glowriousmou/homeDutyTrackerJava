package com.oslead.solutions.homeDutyTracker.dao.interfaces;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface ITacheDAOV1 {
    int create(Tache tache) throws SQLException;

    // boolean update(Tache tache) throws SQLException;

    // boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws SQLException;

    List<Tache> getAll() throws SQLException;

    List<Tache> getAll(Utilisateur utilisateur) throws SQLException;
}