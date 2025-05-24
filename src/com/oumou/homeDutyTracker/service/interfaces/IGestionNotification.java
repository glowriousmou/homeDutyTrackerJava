package com.oumou.homeDutyTracker.service.interfaces;

import com.oumou.homeDutyTracker.domain.Notification;
import com.oumou.homeDutyTracker.domain.Utilisateur;

import java.util.List;

public interface IGestionNotification {
    int creerNotification(Notification notification) throws Exception;
    List<Notification> afficherHistoriqueNotification(Utilisateur utilisateur)throws Exception;
}
