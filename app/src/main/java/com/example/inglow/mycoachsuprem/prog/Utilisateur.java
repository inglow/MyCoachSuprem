package com.example.inglow.mycoachsuprem.prog;

/**
 * Created by inglow on 09/02/16.
 */
public class Utilisateur
{
    private String nom, prenom, email, mdp;

    public Utilisateur()
    {
        this.nom="";
        this.prenom="";
        this.email="";
        this.mdp="";
    }

    public Utilisateur(String nom, String prenom, String email, String mdp)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.mdp=mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
