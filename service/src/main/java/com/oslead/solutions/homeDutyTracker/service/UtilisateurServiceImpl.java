package com.oslead.solutions.homeDutyTracker.service;

import org.mindrot.jbcrypt.BCrypt;

public class UtilisateurServiceImpl {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
