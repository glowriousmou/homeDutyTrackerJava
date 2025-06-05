package com.oslead.solutions.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.service.interfaces.ITacheService;

import java.util.List;

public class TacheServiceImpl implements ITacheService {
    private final ITacheDao tacheDao;

    public TacheServiceImpl(ITacheDao tacheDao) {
        this.tacheDao = tacheDao;
    }

    @Override
    public int enregistrerTache(Tache tache) {
        // Business rule can be added here
        return tacheDao.save(tache);
    }

    @Override
    public List<Tache> listerTaches() {
        return tacheDao.findAll();
    }
}
