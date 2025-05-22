package com.oumou.homeDutyTracker.domain;

import java.time.LocalDateTime;

public class Notification {
    private int identifiant;
    private String message;
    private LocalDateTime dateCreation;
    private Tache tache;

    public Notification(int identifiant, String message, LocalDateTime dateCreation, Tache tache) {
        this.identifiant = identifiant;
        this.message = message;
        this.dateCreation = dateCreation;
        this.tache = tache;
    }
    public Notification(String message, LocalDateTime dateCreation, Tache tache) {
        this.message = message;
        this.dateCreation = dateCreation;
        this.tache = tache;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "identifiant='" + identifiant + '\'' +
                ", message='" + message + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                '}';
    }
}
