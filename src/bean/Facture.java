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
public class Facture {
    
    private long id;
    private String libelle;
    private Date date_etablissement;
    private double total;
    private double total_paye;
    private Client client;
    
    // Getters

    public long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Date getDate_etablissement() {
        return date_etablissement;
    }

    public double getTotal() {
        return total;
    }

    public Client getClient() {
        return client;
    }
    
    public double getTotal_paye() {
        return total_paye;
    }
    
    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDate_etablissement(Date date_etablissement) {
        this.date_etablissement = date_etablissement;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTotal_paye(double total_paye) {
        this.total_paye = total_paye;
    }

    

    // Constructeur

    public Facture() {
    }

    public Facture(long id, String libelle, Date date_etablissement, double total, Client client) {
        this.id = id;
        this.libelle = libelle;
        this.date_etablissement = date_etablissement;
        this.total = total;
        this.client = client;
    }
    
    // toString

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    

    

    
    
    
}
