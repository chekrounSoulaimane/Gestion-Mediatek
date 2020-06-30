/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Paiement;
import dao.PaiementDao;
import java.util.List;
/**
 *
 * @author Soulaimane
 */
public class PaiementService {
    
    private final PaiementDao paiementDao = new PaiementDao();
    
    public List<Paiement> findByFacture(long idFacture) throws Exception {
        return paiementDao.findByFacture(idFacture);
    }
    
    public void save(Paiement paiement) throws Exception {
        paiementDao.save(paiement);
    }
    
    public long getNextId() throws Exception {
        return paiementDao.findMaxId() + 1;
    }
}
