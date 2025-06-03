package com.oslead.solutions.homeDutyTracker.domain;

import com.oslead.solutions.homeDutyTracker.domain.enumeration.RoleUser;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur   {
    private int id;
    private String prenom;
    private String nom;
    private String motPasse;
    private String email;
    private List<Tache> tacheList;
    private RoleUser roleUser;

    public Utilisateur() {

    }

    public Utilisateur(String prenom, String nom, String email, String motPasse) {

        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motPasse = motPasse;
        this.tacheList = new ArrayList<>();
    }

    public Utilisateur(int id, String prenom, String nom, String motPasse, String email, List<Tache> tacheList) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.motPasse = motPasse;
        this.email = email;
        this.tacheList = tacheList;
    }
    public Utilisateur(int id, String prenom, String nom, String motPasse, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.motPasse = motPasse;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tache> getAssignationTacheList() {
        return tacheList;
    }

    public void setAssignationTacheList(List<Tache> tacheList) {
        this.tacheList = tacheList;
    }

    public RoleUser getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(RoleUser roleUser) {
        this.roleUser = roleUser;
    }
   /*  @Override
 public boolean authentication (String email, String motPasse){
        System.out.println("Authentication succeed");
        return true;
 }
*/

    @Override
    public String toString() {
        return "Utilisateur{" +
                "identifiant='" + id +
                ", prenom='" + prenom +
                ", nom='" + nom +
                '}';
    }

    /*@Override
    public boolean editUser(Utilisateur utilisateur) {
        setNom(utilisateur.getNom());
        setPrenom(utilisateur.getPrenom());
       return true;
    } */
}
