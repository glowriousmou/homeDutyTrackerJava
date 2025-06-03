package com.oslead.solutions.homeDutyTracker.dao.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface IUtilisateurDao {
    void save(Utilisateur utilisateur);
    Optional<Utilisateur> findById(int id);
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findAll();
    void deleteById(int id);
}
