package com.oumou.homeDutyTracker.service.interfaces;


import com.oumou.homeDutyTracker.domain.Utilisateur;

import java.util.List;

public interface IGestionEnfant {
    boolean authentifier(String email, String motPasse) throws Exception;
    boolean modifierUser(Utilisateur utilisateur) throws Exception;
    List<Utilisateur> afficherListUser() throws Exception;
}
