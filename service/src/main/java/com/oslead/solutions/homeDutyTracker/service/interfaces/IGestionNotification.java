package com.oslead.solutions.homeDutyTracker.service.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;

import java.util.List;

public interface IGestionNotification {
    int creerNotification(Notification notification) throws Exception;
    List<Notification> afficherHistoriqueNotification(Utilisateur utilisateur)throws Exception;
}
