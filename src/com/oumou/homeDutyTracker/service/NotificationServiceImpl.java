package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.NotificationDAO;
import com.oumou.homeDutyTracker.domain.Notification;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.service.interfaces.IGestionNotification;

import java.util.List;

public class NotificationServiceImpl implements IGestionNotification
{
    @Override
    public int creerNotification(Notification notification) throws Exception {
        return NotificationDAO.addNotification(notification);
    }

    @Override
    public List<Notification> afficherHistoriqueNotification(Utilisateur utilisateur) {
        List<Notification> listAllNotification = NotificationDAO.getAllNotification();
        return listAllNotification.stream()
                .filter(n -> n.getTache().getResponsable().getId() == utilisateur.getId())
                .toList();
//        db.stream() transforme la liste simulée de notifications en flux.
//        .filter(...) garde uniquement les notifications destinées à l'utilisateur.
//        .toList() retourne le résultat filtré sous forme de liste.


    }
}
