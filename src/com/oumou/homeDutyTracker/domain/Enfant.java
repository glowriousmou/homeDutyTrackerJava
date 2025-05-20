package com.oumou.homeDutyTracker.domain;

public class Enfant extends Utilisateur {
    public Enfant( String prenom, String nom,  String email, String motPasse) {
        super( prenom, nom, email, motPasse);
    }
    public Enfant( int id,String prenom, String nom ,String motPasse, String email) {
        super(id,prenom, nom,email, motPasse);
    }


}
