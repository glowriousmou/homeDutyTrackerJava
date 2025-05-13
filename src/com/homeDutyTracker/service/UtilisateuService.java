package com.homeDutyTracker.service;

import com.homeDutyTracker.dao.TacheDAO;
import com.homeDutyTracker.dao.UtilisateurDAO;
import com.homeDutyTracker.domain.Utilisateur;

public class UtilisateuService {
    public static int  creerUtilisateur(Utilisateur utilisateur, String role) throws Exception {
        return UtilisateurDAO.addUser(utilisateur, role);

    }
}
