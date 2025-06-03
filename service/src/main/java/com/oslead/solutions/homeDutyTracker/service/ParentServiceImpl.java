package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.UtilisateurDAOImplV1;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IGestionParent;
import org.apache.log4j.Logger;

public class ParentServiceImpl implements IGestionParent {
    private static final Logger logger = Logger.getLogger(TacheServiceImpl.class);
    // private TacheDAOImpl tacheDAOImpl = new TacheDAOImpl();
    private final UtilisateurDAOImplV1 utilisateurDAOImplV1;

    public ParentServiceImpl(UtilisateurDAOImplV1 utilisateurDAOImplV1) {
        this.utilisateurDAOImplV1 = utilisateurDAOImplV1;
    }

    public  int  creerUser(Utilisateur utilisateur, String role) throws Exception {
        return this.utilisateurDAOImplV1.create(utilisateur, role);

    }


}
