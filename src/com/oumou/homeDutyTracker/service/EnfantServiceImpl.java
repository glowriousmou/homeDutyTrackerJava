package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.service.interfaces.IGestionEnfant;

import java.util.ArrayList;
import java.util.List;

public class EnfantServiceImpl implements IGestionEnfant {

    public boolean authentifier(String email, String motPasse) throws Exception {
        return true;
    };

    public boolean modifierUser(Utilisateur utilisateur) throws Exception {
        return true;
    };

    public List<Utilisateur> afficherListUser() throws Exception {
        List<Utilisateur> listUser = new ArrayList<>();
        return listUser;
    };
}
