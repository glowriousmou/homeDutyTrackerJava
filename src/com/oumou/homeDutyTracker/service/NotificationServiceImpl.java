package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.NotificationDAOImpl;
import com.oumou.homeDutyTracker.dao.TacheDAOImpl;
import com.oumou.homeDutyTracker.domain.Notification;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.service.interfaces.IGestionNotification;
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
