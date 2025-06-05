package com.oslead.solutions.homeDutyTracker.service.interfaces;



import com.oslead.solutions.homeDutyTracker.domain.Notification;

import java.util.List;

public interface INotificationService {
    int enregistrerNotification(Notification notification);
    List<Notification> listerNotifications();
}
