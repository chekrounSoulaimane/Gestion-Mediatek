/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Date;

/**
 *
 * @author Soulaimane
 */
public class Client {
    
    private long id;
    private String cin;
    private String nom;
    private String prenom;
    private Date date_naissance;
    
    // Getters 

    public long getId() {
        return id;
    }

    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }
    
    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
    
    // Constructeur

    public Client() {
    }

    public Client(long id, String cin, String nom, String prenom, Date date_naissance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
    }
    
    // toString

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    
    
    
}
