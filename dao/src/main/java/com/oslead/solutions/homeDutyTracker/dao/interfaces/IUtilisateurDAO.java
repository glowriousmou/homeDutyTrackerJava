package com.oslead.solutions.homeDutyTracker.dao.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.sql.SQLException;

public interface IUtilisateurDAO {
    int create(Utilisateur utilisateur, String string) throws SQLException;
}
