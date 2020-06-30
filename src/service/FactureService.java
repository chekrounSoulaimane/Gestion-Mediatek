/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Facture;
import dao.FactureDao;
import java.util.List;
import util.StringUtil;

/**
 *
 * @author Soulaimane
 */
public class FactureService {

    private final FactureDao factureDao = new FactureDao();

    public void save(Facture facture) throws Exception {
        if (facture != null) {
            facture.setId(factureDao.findMaxId() + 1);
            factureDao.save(facture);
        }
    }

    public List<Facture> findAll() throws Exception {
        return factureDao.findAll();
    }

    public List<Facture> findFacturePaye() throws Exception {
        return factureDao.findFacturePaye();
    }

    public List<Facture> findFactureNonComplet() throws Exception {
        return factureDao.findFactureNonComplet();
    }

    public List<Facture> findByIdOrLibelle(String value) throws Exception {
        long id = 0;
        if (value.length() == 1 && StringUtil.isNumeric(value)) {
            id = new Long(value);
        }
        return factureDao.findByIdOrLibelle(id, value);
    }
    
    public void update(Facture facture ) throws Exception {
        factureDao.update(facture, facture.getId());
    }

    public double[] getTotalOfLastTwelveMonths(int month, int year) throws Exception {
        double[] resultat = new double[12];
        for (int i = 0; i < 12; i++) {
            double total = factureDao.findTotalByMonth(month, year);
            resultat[i] = total;
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
        }
        return resultat;
    }
}
