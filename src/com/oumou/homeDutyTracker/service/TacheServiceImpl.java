package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.TacheDAO;
import com.oumou.homeDutyTracker.domain.Notification;
import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.domain.enumeration.StatutTache;
import com.oumou.homeDutyTracker.service.interfaces.IGestionTache;

import java.time.LocalDateTime;
import java.util.List;

public class TacheServiceImpl implements IGestionTache {
    // private TacheDAO tacheDAO = new TacheDAO();
    public boolean creerTache(Tache tache) throws Exception {
        tache.setStatut(StatutTache.INITIALIZED);
        int tacheId = TacheDAO.addTask(tache);
        tache.setId(tacheId);
        // TODO : générer notifications
        Notification notification = new Notification(
                "le parent "+tache.getCreateur().getPrenom()+" "+tache.getCreateur().getNom()+" a marqué la tache "+tache.getNom()+" comme "+ tache.getStatut(),
                LocalDateTime.now(),
                tache
        );
        NotificationServiceImpl notificationService = new NotificationServiceImpl();
        notificationService.creerNotification(notification);
        return true;
    };
    public boolean modifierTache(Tache tache) throws Exception {
        return true;
    };
    public boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws Exception {
        return true;
    };

    public List<Tache> afficherListTache() throws Exception {
       return  TacheDAO.getAllTask();
    }
    @Override
    public  List<Tache> afficherListTache(Utilisateur utilisateur) throws Exception {
        return  TacheDAO.getAllTask(utilisateur);
    }

    public static StatutTache fromStringToEnum(String value, StatutTache defaultValue) {
        try {
            return StatutTache.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }


}
