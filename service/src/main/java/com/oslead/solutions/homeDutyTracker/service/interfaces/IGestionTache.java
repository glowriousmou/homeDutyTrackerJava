package com.oslead.solutions.homeDutyTracker.service.interfaces;

import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;

import java.util.List;

public interface IGestionTache {
    boolean creerTache(Tache tache)throws Exception;
    boolean modifierTache(Tache tache) throws Exception;
    boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws Exception;
    List<Tache> afficherListTache() throws Exception;
    List<Tache> afficherListTache(Utilisateur utilisateur) throws Exception;
}
