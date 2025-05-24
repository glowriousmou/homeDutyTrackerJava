package com.oumou.homeDutyTracker.dao.interfaces;



import com.oumou.homeDutyTracker.domain.Utilisateur;

import java.sql.SQLException;

public interface IUtilisateurDAO {
    int create(Utilisateur utilisateur,String string) throws SQLException;
}
