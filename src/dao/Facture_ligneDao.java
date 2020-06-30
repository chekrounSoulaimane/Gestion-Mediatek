/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Facture_ligne;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class Facture_ligneDao extends AbstractDao<Facture_ligne> {
    
    public Facture_ligneDao() {
        super(Facture_ligne.class);
    }
    
    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM facture_ligne";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }
    
    public List<String[]> findFactureLigneNumberByProduct() throws SQLException {
        List<String[]> strings = new ArrayList<>();
        String requette = "SELECT SUM(quantite), produit FROM facture_ligne GROUP BY produit ORDER BY SUM(quantite) DESC";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            final String[] resultat = {resultSet.getString("produit"), resultSet.getString("SUM(quantite)")};
            strings.add(resultat);
        }
        return strings;
    }
}
