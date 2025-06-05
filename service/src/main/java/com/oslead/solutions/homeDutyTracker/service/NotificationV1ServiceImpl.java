package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.NotificationDAOV1Impl;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IGestionNotificationV1;
import org.apache.log4j.Logger;

import java.util.List;

public class NotificationV1ServiceImpl implements IGestionNotificationV1
{
    private static final Logger logger = Logger.getLogger(NotificationV1ServiceImpl.class);
    private final NotificationDAOV1Impl notificationDAOImpl;

    public NotificationV1ServiceImpl(NotificationDAOV1Impl notificationDAOImpl) {
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
