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
public class Utilisateur {
    
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String genre;
    private Date date_naissance;
    
    // getters

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGenre() {
        return genre;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }
    
    // setters

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
    
    // constructeur

    public Utilisateur() {
    }

    public Utilisateur(long id, String nom, String prenom, String email, String password, String genre, Date date_naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.date_naissance = date_naissance;
    }
    
    // toString

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    
    
    
    
}
