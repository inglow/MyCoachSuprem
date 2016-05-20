package com.example.inglow.mycoachsuprem.prog;

/**
 * Created by inglow on 16/02/16.
 */
public class Produit {
    private String reference, designation, categorie;
    private float prix;
    private int qte;

    public Produit(){
        this.reference=this.designation=this.categorie="";
        this.prix=0;
        this.qte=0;
    }
    public Produit(String reference, String designation, float prix, int qte, String categorie){
        this.reference=reference;
        this.designation=designation;
        this.prix=prix;
        this.qte=qte;
        this.categorie=categorie;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}

