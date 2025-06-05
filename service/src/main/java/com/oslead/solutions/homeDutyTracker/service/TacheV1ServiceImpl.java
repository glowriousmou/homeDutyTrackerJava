package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.NotificationDAOV1Impl;
import com.oslead.solutions.homeDutyTracker.dao.TacheDAOV1ImplV1;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IGestionTacheV1;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class TacheV1ServiceImpl implements IGestionTacheV1 {
    private static final Logger logger = Logger.getLogger(TacheV1ServiceImpl.class);
    // private TacheDAOImpl tacheDAOImpl = new TacheDAOImpl();
    private final TacheDAOV1ImplV1 tacheDAOImplV1;


    public TacheV1ServiceImpl(TacheDAOV1ImplV1 tacheDAOImplV1) {
        this.tacheDAOImplV1 = tacheDAOImplV1;
    }

    public boolean creerTache(Tache tache) throws Exception {
        tache.setStatut(StatutTache.INITIALIZED);
        int tacheId = tacheDAOImplV1.create(tache);
        tache.setId(tacheId);

        if(tacheId>0) {
            // TODO : générer notifications
            Notification notification = new Notification(
                    "le parent " + tache.getCreateur().getPrenom() + " " + tache.getCreateur().getNom() + " a marqué la tache " + tache.getNom() + " comme " + tache.getStatut(),
                    LocalDateTime.now(),
                    tache
            );
            NotificationDAOV1Impl notificationDAOImpl= new NotificationDAOV1Impl();
            NotificationV1ServiceImpl notificationService = new NotificationV1ServiceImpl(notificationDAOImpl);
            notificationService.creerNotification(notification);
            logger.info("Tâche créée avec succès !");
            return true;
        } else {
            logger.warn("tache non créee ");
            return false;
        }
    };
    public boolean modifierTache(Tache tache) throws Exception {
        return true;
    };
    public boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws Exception {
        return true;
    };

    public List<Tache> afficherListTache() throws Exception {
       return  tacheDAOImplV1.getAll();
    }
    @Override
    public  List<Tache> afficherListTache(Utilisateur utilisateur) throws Exception {
        return  tacheDAOImplV1.getAll(utilisateur);
    }

    public static StatutTache fromStringToEnum(String value, StatutTache defaultValue) {
        try {
            return StatutTache.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }


}
