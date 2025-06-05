package com.oslead.solutions.homeDutyTracker.service.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Tache;

import java.util.List;

public interface ITacheService {
    int enregistrerTache(Tache tache);
    List<Tache> listerTaches();

}
