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
public class Produit {
    
    private long id;
    private String designation;
    private double prix;
    private int quantite_stock;
    
    // Getters

    public long getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantite_stock() {
        return quantite_stock;
    }
    
    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setQuantite_stock(int quantite_stock) {
        this.quantite_stock = quantite_stock;
    }
    
    // Constructeur

    public Produit() {
    }

    public Produit(long id, String designation, double prix, int quantite_stock) {
        this.id = id;
        this.designation = designation;
        this.prix = prix;
        this.quantite_stock = quantite_stock;
    }
    
    // toString

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", designation=" + designation + ", prix=" + prix + ", quantite_stock=" + quantite_stock + '}';
    }
    
    
}
