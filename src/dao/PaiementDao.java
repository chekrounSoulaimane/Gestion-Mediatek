/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Paiement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class PaiementDao extends AbstractDao<Paiement> {

    public PaiementDao() {
        super(Paiement.class);
    }

    public List<Paiement> findByFacture(long idFacture) throws Exception {
        String requette = "SELECT * FROM paiement WHERE facture = '" + idFacture + "'";
        return load(requette);
    }

    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM paiement";
        ResultSet resultSet = ConnectDB.load(requette);
        while (resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }

}
