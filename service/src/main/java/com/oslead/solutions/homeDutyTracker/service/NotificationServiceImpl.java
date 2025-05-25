package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.NotificationDAOImpl;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IGestionNotification;
import org.apache.log4j.Logger;

import java.util.List;

public class NotificationServiceImpl implements IGestionNotification
{
    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);
    private final NotificationDAOImpl notificationDAOImpl;

    public NotificationServiceImpl(NotificationDAOImpl notificationDAOImpl) {
        this.notificationDAOImpl = notificationDAOImpl;
    }

    @Override
    public int creerNotification(Notification notification) throws Exception {
        return notificationDAOImpl.create(notification);
    }

    @Override
    public List<Notification> afficherHistoriqueNotification(Utilisateur utilisateur) throws Exception  {
        List<Notification> listAllNotification = notificationDAOImpl.getAll();
        return listAllNotification.stream()
                .filter(n -> n.getTache().getResponsable().getId() == utilisateur.getId())
                .toList();
//        db.stream() transforme la liste simulée de notifications en flux.
//        .filter(...) garde uniquement les notifications destinées à l'utilisateur.
//        .toList() retourne le résultat filtré sous forme de liste.


    }
}
