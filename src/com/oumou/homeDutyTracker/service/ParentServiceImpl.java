package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.UtilisateurDAOImpl;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.service.interfaces.IGestionParent;
import org.apache.log4j.Logger;

public class ParentServiceImpl implements IGestionParent {
    private static final Logger logger = Logger.getLogger(TacheServiceImpl.class);
    // private TacheDAOImpl tacheDAOImpl = new TacheDAOImpl();
    private final UtilisateurDAOImpl utilisateurDAOImpl;

    public ParentServiceImpl(UtilisateurDAOImpl utilisateurDAOImpl) {
        this.utilisateurDAOImpl = utilisateurDAOImpl;
    }

    public  int  creerUser(Utilisateur utilisateur, String role) throws Exception {
        return this.utilisateurDAOImpl.create(utilisateur, role);

    }


}
