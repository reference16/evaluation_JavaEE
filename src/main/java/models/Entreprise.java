package models;

import java.util.Date;

public class Entreprise {
    private int id;
    private String nom;
    private String adresse;
    private double chiffreAffaire;
    private Date dateCreation;

    // Constructeur
    public Entreprise(int id, String nom, String adresse, double chiffreAffaire, Date dateCreation) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.chiffreAffaire = chiffreAffaire;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public double getChiffreAffaire() { return chiffreAffaire; }
    public void setChiffreAffaire(double chiffreAffaire) { this.chiffreAffaire = chiffreAffaire; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}
