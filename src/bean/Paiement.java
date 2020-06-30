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
public class Paiement {
    
    private long id;
    private Facture facture;
    private Date date_paiement;
    private double montant;
    
    //getters

    public long getId() {
        return id;
    }

    public Facture getFacture() {
        return facture;
    }

    public Date getDate_paiement() {
        return date_paiement;
    }

    public double getMontant() {
        return montant;
    }
    
    //setters

    public void setId(long id) {
        this.id = id;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public void setDate_paiement(Date date_paiement) {
        this.date_paiement = date_paiement;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    //constructeurs

    public Paiement() {
    }

    public Paiement(long id, Facture facture, Date date_paiement, double montant) {
        this.id = id;
        this.facture = facture;
        this.date_paiement = date_paiement;
        this.montant = montant;
    }
    
    //toString

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    
    
}
