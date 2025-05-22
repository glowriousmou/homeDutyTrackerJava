package com.oumou.homeDutyTracker.service.interfaces;

import com.oumou.homeDutyTracker.domain.Utilisateur;

public interface IGestionParent {
    int creerUser(Utilisateur utilisateur,String role) throws Exception;
}
