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
public class Journal_avertissement {
    
    private long id;
    private String date_avertissement; 
    private Produit produit;
    private int stock_restant;
    
    // getters

    public long getId() {
        return id;
    }

    public String getDate_avertissement() {
        return date_avertissement;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getStock_restant() {
        return stock_restant;
    }
    
    //setters 

    public void setId(long id) {
        this.id = id;
    }

    public void setDate_avertissement(String date_avertissement) {
        this.date_avertissement = date_avertissement;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setStock_restant(int stock_restant) {
        this.stock_restant = stock_restant;
    }
    
    //constructeur

    public Journal_avertissement() {
    }

    public Journal_avertissement(long id, String date_avertissement, Produit produit, int stock_restant) {
        this.id = id;
        this.date_avertissement = date_avertissement;
        this.produit = produit;
        this.stock_restant = stock_restant;
    }
    
    //toString

    
    /*
            @Override
    public String toString() {
        return String.valueOf(id);
    }
    */

    @Override
    public String toString() {
        return "Journal_avertissement{" + "id=" + id + ", date_avertissement=" + date_avertissement + ", produit=" + produit + ", stock_restant=" + stock_restant + '}';
    }
    
}
