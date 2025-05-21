package com.oumou.homeDutyTracker.service;

import org.mindrot.jbcrypt.BCrypt;

public class UtilisateurService {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
