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
public class Facture_ligne {
    
    private long id;
    private int quantite;
    private Produit produit;
    private Facture facture;
    
    // Getters

    public long getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public Facture getFacture() {
        return facture;
    }
    
    // Setters 

    public void setId(long id) {
        this.id = id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
    
    // Constructeur

    public Facture_ligne() {
    }

    public Facture_ligne(long id, int quantite, Produit produit, Facture facture) {
        this.id = id;
        this.quantite = quantite;
        this.produit = produit;
        this.facture = facture;
    }
    
    // toString

    @Override
    public String toString() {
        return "Facture_ligne{" + "id=" + id + ", quantite=" + quantite + ", produit=" + produit + ", facture=" + facture + '}';
    }
    
    
    
}
