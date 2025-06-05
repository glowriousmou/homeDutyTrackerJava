package com.oslead.solutions.homeDutyTracker.dao.interfaces;
import com.oslead.solutions.homeDutyTracker.domain.Notification;

import java.sql.SQLException;
import java.util.List;

public interface INotificationDaoV1 {
    int create(Notification notification) throws SQLException;
    List<Notification> getAll() throws SQLException;
}
