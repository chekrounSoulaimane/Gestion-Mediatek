/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Produit;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class ProduitDao extends AbstractDao<Produit>{
    
    public ProduitDao() {
        super(Produit.class);
    }
    
    public List<Produit> findByDesignation(String designation) throws Exception {
        String requette = "SELECT * FROM produit WHERE designation LIKE '%" + designation + "%'";
        return load(requette);
    }
    
    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM produit";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }
    
    public String getDemande(long id) throws Exception {
        String demande = new String();
        String requette = "SELECT f_produit_demande(id) AS demande FROM produit WHERE id = " + id ;
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            demande = resultSet.getString("demande");
        }
        return demande;
    }
}
