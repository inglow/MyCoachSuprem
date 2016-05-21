package com.example.inglow.mycoachsuprem.prog;

/**
 * Created by inglow on 21/05/16.
 */
public class Coach {
    private String nom, prenom, email, avatar;
    private int telephone, cp;

    public Coach()
    {
        this.nom="";
        this.prenom="";
        this.email="";
        this.telephone=0;
        this.cp=0;
        this.avatar="";

    }

    public Coach(String nom, String prenom, String email, String avatar, int telephone, int cp)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.prenom=prenom;
        this.avatar=avatar;
        this.telephone=telephone;
        this.cp=cp;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
