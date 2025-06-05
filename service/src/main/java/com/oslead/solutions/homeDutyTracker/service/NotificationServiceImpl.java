package com.oslead.solutions.homeDutyTracker.service;



import com.oslead.solutions.homeDutyTracker.dao.interfaces.INotificationDao;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.service.interfaces.INotificationService;

import java.util.List;

public class NotificationServiceImpl implements INotificationService {
    private final INotificationDao notificationDao;

    public NotificationServiceImpl(INotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public int enregistrerNotification(Notification notification) {
        // Business rule can be added here
        return notificationDao.save(notification);
    }

    @Override
    public List<Notification> listerNotifications() {
        return notificationDao.findAll();
    }
}
