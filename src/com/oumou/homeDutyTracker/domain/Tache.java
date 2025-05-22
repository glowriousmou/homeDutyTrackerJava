package com.oumou.homeDutyTracker.domain;

import com.oumou.homeDutyTracker.domain.enumeration.StatutTache;

import java.time.LocalDateTime;

public class Tache {
    private int id;
    private String nom;
    private String description;
    private Enfant responsable;
    private Enfant superviseur;
    private Parent createur;
    private StatutTache statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLimite;

    public Tache(
            int id, String nom, String description, Enfant responsable, Enfant superviseur,
            Parent createur, StatutTache statut,LocalDateTime dateCreation, LocalDateTime dateLimite
    ) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.responsable = responsable;
        this.superviseur = superviseur;
        this.createur = createur;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
    }
    public Tache(
            int id, String nom, Enfant responsable
    ) {
        this.id = id;
        this.nom = nom;
        this.responsable = responsable;
    }
    public Tache(
             String nom, String description, Enfant responsable, Enfant superviseur,
            Parent createur, StatutTache statut,LocalDateTime dateCreation, LocalDateTime dateLimite
    ) {
        this.nom = nom;
        this.description = description;
        this.responsable = responsable;
        this.superviseur = superviseur;
        this.createur = createur;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
    }
    public Tache(
            int id, String nom, String description, Parent createur, StatutTache statut,LocalDateTime dateCreation, LocalDateTime dateLimite
    ) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.createur = createur;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
    }
    public Tache(
             String nom, String description,
            Parent createur, StatutTache statut,LocalDateTime dateCreation, LocalDateTime dateLimite
    ) {

        this.nom = nom;
        this.description = description;
        this.responsable = createur;
        this.superviseur = createur;
        this.createur = createur;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enfant getResponsable() {
        return responsable;
    }

    public void setResponsable(Enfant responsable) {
        this.responsable = responsable;
    }

    public Enfant getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(Enfant superviseur) {
        this.superviseur = superviseur;
    }

    public Parent getCreateur() {
        return createur;
    }

    public void setCreateur(Parent createur) {
        this.createur = createur;
    }

    public StatutTache getStatut() {
        return statut;
    }

    public void setStatut(StatutTache statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDateTime dateLimite) {
        this.dateLimite = dateLimite;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", responsable=" + responsable +
                ", superviseur=" + superviseur +
                ", createur=" + createur +
                ", statut='" + statut + '\'' +
                '}';
    }
}
