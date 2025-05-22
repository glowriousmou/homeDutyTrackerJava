package com.oumou.homeDutyTracker.service.interfaces;

import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.domain.Utilisateur;
import com.oumou.homeDutyTracker.domain.enumeration.StatutTache;

import java.util.List;

public interface IGestionTache {
    boolean creerTache(Tache tache)throws Exception;
    boolean modifierTache(Tache tache) throws Exception;
    boolean modifierStatutTache(Tache tache, StatutTache statutTache) throws Exception;
    List<Tache> afficherListTache() throws Exception;
    List<Tache> afficherListTache(Utilisateur utilisateur) throws Exception;
}
