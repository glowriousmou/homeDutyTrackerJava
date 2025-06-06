package com.oslead.solutions.homeDutyTracker.dao.interfaces;


import com.oslead.solutions.homeDutyTracker.domain.Notification;

import java.sql.SQLException;
import java.util.List;

public interface INotificationDao {

    int save(Notification notification);
    List<Notification> findAll();
    void deleteById(int id);
}
