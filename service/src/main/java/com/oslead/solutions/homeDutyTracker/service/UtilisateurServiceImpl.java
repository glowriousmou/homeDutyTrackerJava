package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IUtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements IUtilisateurService {
    private final IUtilisateurDao utilisateurDao;

    public UtilisateurServiceImpl(IUtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public int enregistrerUtilisateur(Utilisateur utilisateur) {
        // Business rule can be added here
        return utilisateurDao.save(utilisateur);
    }

    @Override
    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurDao.findAll();
    }
}
