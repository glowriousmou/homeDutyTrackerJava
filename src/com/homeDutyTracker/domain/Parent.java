package com.homeDutyTracker.domain;

public class Parent extends  Enfant{
    public Parent( String prenom, String nom ,String email, String motPasse) {
        super(prenom, nom,email, motPasse);
    }
    public Parent( int id,String prenom, String nom ,String motPasse, String email) {
        super(id,prenom, nom,email, motPasse);
    }

}
