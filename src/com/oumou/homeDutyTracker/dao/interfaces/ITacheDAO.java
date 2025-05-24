package com.oumou.homeDutyTracker.dao.interfaces;

import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.domain.enumeration.StatutTache;

import java.sql.SQLException;
import java.util.List;

public interface ITacheDAO extends IDAO<Tache, Utilisateur> {
    int create(Tache tache) throws SQLException;

    // boolean update(Tache tache) throws SQLException;

    // boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws SQLException;

    List<Tache> getAll() throws SQLException;

    List<Tache> getAll(Utilisateur utilisateur) throws SQLException;
}