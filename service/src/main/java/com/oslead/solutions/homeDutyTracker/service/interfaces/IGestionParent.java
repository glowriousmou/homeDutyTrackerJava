package com.oslead.solutions.homeDutyTracker.service.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

public interface IGestionParent {
    int creerUser(Utilisateur utilisateur,String role) throws Exception;
}
