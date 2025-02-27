package models;

import java.util.Date;

public class Employe {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String fonction;
    private String service;
    private Date dateEmbauche;
    private String sexe;
    private double salaireBase;
    private Entreprise entreprise;

    // Constructeur complet
    public Employe(int id, String matricule, String nom, String prenom, String fonction, String service, 
                   Date dateEmbauche, String sexe, double salaireBase, Entreprise entreprise) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.service = service;
        this.dateEmbauche = dateEmbauche;
        this.sexe = sexe;
        this.salaireBase = salaireBase;
        this.entreprise = entreprise;
    }

    // Constructeur simplifié pour la gestion des employés
    public Employe(int id, String matricule, String nom, String prenom, String fonction, String service, 
                   Date dateEmbauche) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.service = service;
        this.dateEmbauche = dateEmbauche;
        this.sexe = "N/A";
        this.salaireBase = 0.0;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getFonction() { return fonction; }
    public void setFonction(String fonction) { this.fonction = fonction; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public Date getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(Date dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public double getSalaireBase() { return salaireBase; }
    public void setSalaireBase(double salaireBase) { this.salaireBase = salaireBase; }

    public Entreprise getEntreprise() { return entreprise; }
    public void setEntreprise(Entreprise entreprise) { this.entreprise = entreprise; }
}
