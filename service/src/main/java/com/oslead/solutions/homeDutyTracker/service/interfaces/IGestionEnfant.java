package com.oslead.solutions.homeDutyTracker.service.interfaces;


import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.util.List;

public interface IGestionEnfant {
    boolean authentifier(String email, String motPasse) throws Exception;
    boolean modifierUser(Utilisateur utilisateur) throws Exception;
    List<Utilisateur> afficherListUser() throws Exception;
}
