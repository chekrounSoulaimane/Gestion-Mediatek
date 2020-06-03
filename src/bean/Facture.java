/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Soulaimane
 */
public class Facture {
    
    private long id;
    private String libelle;
    private String date_etablissement;
    private double total;
    private Client client;
    
    // Getters

    public long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDate_etablissement() {
        return date_etablissement;
    }

    public double getTotal() {
        return total;
    }

    public Client getClient() {
        return client;
    }
    
    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDate_etablissement(String date_etablissement) {
        this.date_etablissement = date_etablissement;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    // Constructeur

    public Facture() {
    }

    public Facture(long id, String libelle, String date_etablissement, double total, Client client) {
        this.id = id;
        this.libelle = libelle;
        this.date_etablissement = date_etablissement;
        this.total = total;
        this.client = client;
    }
    
    // toString

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", libelle=" + libelle + ", date_etablissement=" + date_etablissement + ", total=" + total + ", client=" + client + '}';
    }
    
    
    
}
