package com.oumou.homeDutyTracker.service;

import com.oumou.homeDutyTracker.dao.TacheDAO;
import com.oumou.homeDutyTracker.domain.Tache;

import java.util.List;

public class TacheService {
    // private TacheDAO tacheDAO = new TacheDAO();

    public static void creerTache(Tache tache) throws Exception {
        tache.setStatut("INITIALISE");
        TacheDAO.addTask(tache);
        // TODO : générer notifications
    }
    public static List<Tache> getAllTask() throws Exception {

       return  TacheDAO.getAllTask();

    }


}
