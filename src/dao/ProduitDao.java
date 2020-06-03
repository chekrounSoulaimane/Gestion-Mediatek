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
}
