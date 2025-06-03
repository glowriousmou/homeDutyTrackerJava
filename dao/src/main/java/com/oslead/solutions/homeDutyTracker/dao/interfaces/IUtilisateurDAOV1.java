package com.oslead.solutions.homeDutyTracker.dao.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.sql.SQLException;

public interface IUtilisateurDAOV1 {
    int create(Utilisateur utilisateur, String string) throws SQLException;
}
