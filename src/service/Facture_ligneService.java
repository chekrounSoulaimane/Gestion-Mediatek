/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Facture_ligne;
import dao.Facture_ligneDao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class Facture_ligneService {

    private final Facture_ligneDao facture_ligneDao = new Facture_ligneDao();

    public void save(Facture_ligne facture_ligne) throws Exception {
        if (facture_ligne != null) {
            facture_ligne.setId(facture_ligneDao.findMaxId() + 1);
            facture_ligneDao.save(facture_ligne);
        }
    }

    public void saveList(List<Facture_ligne> facture_lignes) throws Exception {
        if (facture_lignes != null && !facture_lignes.isEmpty()) {
            for (Facture_ligne facture_ligne : facture_lignes) {
                save(facture_ligne);
            }
        }
    }

    public List<String[]> getBestSellers() throws SQLException {
        List<String[]> strings = facture_ligneDao.findFactureLigneNumberByProduct();
        int quantite = 0;
        for (int i = 3; i < strings.size(); i++) {
            quantite += new Integer(strings.get(i)[1]);
            strings.remove(i);   
        }
        for (int i = 0; i < 3; i++) {
            strings.get(i)[0] = "produit_" + strings.get(i)[0];
        }
        String[] others = {"others", String.valueOf(quantite)};
        strings.add(others);
        System.out.println("service.Facture_ligneService.getBestSellers()");
        strings.forEach((string) -> {
            System.out.println(string[0] + " " + string[1]);
        });
        return strings;
    }

}
