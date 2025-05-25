package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.NotificationDAOImpl;
import com.oslead.solutions.homeDutyTracker.dao.TacheDAOImpl;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IGestionTache;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class TacheServiceImpl implements IGestionTache {
    private static final Logger logger = Logger.getLogger(TacheServiceImpl.class);
    // private TacheDAOImpl tacheDAOImpl = new TacheDAOImpl();
    private final TacheDAOImpl tacheDAOImpl;


    public TacheServiceImpl(TacheDAOImpl tacheDAOImpl) {
        this.tacheDAOImpl = tacheDAOImpl;
    }

    public boolean creerTache(Tache tache) throws Exception {
        tache.setStatut(StatutTache.INITIALIZED);
        int tacheId = tacheDAOImpl.create(tache);
        tache.setId(tacheId);

        if(tacheId>0) {
            // TODO : générer notifications
            Notification notification = new Notification(
                    "le parent " + tache.getCreateur().getPrenom() + " " + tache.getCreateur().getNom() + " a marqué la tache " + tache.getNom() + " comme " + tache.getStatut(),
                    LocalDateTime.now(),
                    tache
            );
            NotificationDAOImpl notificationDAOImpl= new NotificationDAOImpl();
            NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationDAOImpl);
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
       return  tacheDAOImpl.getAll();
    }
    @Override
    public  List<Tache> afficherListTache(Utilisateur utilisateur) throws Exception {
        return  tacheDAOImpl.getAll(utilisateur);
    }

    public static StatutTache fromStringToEnum(String value, StatutTache defaultValue) {
        try {
            return StatutTache.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }


}
