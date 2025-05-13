package com.homeDutyTracker.service;

import com.homeDutyTracker.dao.TacheDAO;
import com.homeDutyTracker.domain.Tache;

import java.util.List;

public class TacheService {
    // private TacheDAO tacheDAO = new TacheDAO();

    public static void creerTache(Tache tache) throws Exception {
        tache.setStatut("INITIALISE");
        TacheDAO.addTask(tache);
        // TODO : générer notifications
    }
    public List<Tache> getAllTask() throws Exception {

       return  TacheDAO.getAllTask();

    }


}
