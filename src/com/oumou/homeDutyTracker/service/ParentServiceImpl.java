package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.UtilisateurDAO;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.service.interfaces.IGestionParent;

public class ParentServiceImpl implements IGestionParent {
    public  int  creerUser(Utilisateur utilisateur, String role) throws Exception {
        return UtilisateurDAO.addUser(utilisateur, role);

    }


}
