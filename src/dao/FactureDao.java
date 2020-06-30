/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Facture;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class FactureDao extends AbstractDao<Facture> {
    
    public FactureDao() {
        super(Facture.class);
    }
    
    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM facture";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }
    
    public List<Facture> findFacturePaye() throws Exception {
        String requette = "SELECT * FROM facture WHERE total = total_paye";
        return load(requette);
    }
    
    public List<Facture> findFactureNonComplet() throws Exception {
        String requette = "SELECT * FROM facture WHERE total > total_paye";
        return load(requette);
    }
    
    public List<Facture> findByIdOrLibelle(long id, String libelle) throws Exception {
        String requette = "SELECT * FROM facture WHERE id = " + id + " OR libelle LIKE '%" + libelle + "%'";
        return load(requette);
    }
    
    public double findTotalByMonth(int month, int year) throws Exception {
        double total = 0;
        String requette = "SELECT SUM(total) FROM facture WHERE EXTRACT(MONTH FROM date_etablissement) = " + month + 
                " AND EXTRACT(YEAR FROM date_etablissement) = " + year;
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            total = resultSet.getDouble("SUM(total)");
        }
        return total;
    }
}
