package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.UtilisateurDAO;
import com.oumou.homeDutyTracker.domain.Utilisateur;

public class ParentService {
    public static int  creerUtilisateur(Utilisateur utilisateur, String role) throws Exception {
        return UtilisateurDAO.addUser(utilisateur, role);

    }
}
