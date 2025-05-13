package com.homeDutyTracker.domain;

public class Notification {
    private String identifiant;
    private String message;
    private String dateCreation;
    private Tache tache;

    public Notification(String identifiant, String message, String dateCreation, Tache assignationTache) {
        this.identifiant = identifiant;
        this.message = message;
        this.dateCreation = dateCreation;
        this.tache = tache;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
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
