package com.oslead.solutions.homeDutyTracker.service.interfaces;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    int enregistrerUtilisateur(Utilisateur utilisateur);
    List<Utilisateur> listerUtilisateurs();
}
