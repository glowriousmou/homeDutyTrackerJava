package com.oslead.solutions.homeDutyTracker.dao.interfaces;


import com.oslead.solutions.homeDutyTracker.domain.Tache;

import java.sql.SQLException;
import java.util.List;

public interface ITacheDao {
    int save(Tache tache);
    List<Tache> findAll();
    void deleteById(int id);
}
