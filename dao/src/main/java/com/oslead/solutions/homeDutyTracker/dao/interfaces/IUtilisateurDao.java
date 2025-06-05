package com.oslead.solutions.homeDutyTracker.dao.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUtilisateurDao {
    int save(Utilisateur utilisateur);
    List<Utilisateur> findAll();
    //Optional<Utilisateur> findById(int id);
    //Optional<Utilisateur> findByEmail(String email);

    void deleteById(int id);
}
