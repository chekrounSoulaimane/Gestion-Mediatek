/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Produit;
import dao.ProduitDao;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class ProduitService {
    
    private ProduitDao produitDao = new ProduitDao();
    
    public List<Produit> findAll() throws Exception {
        return produitDao.findAll();
    }
    
    public Produit findById(long id) throws Exception {
        return produitDao.findById(id);
    }
    
    public List<Produit> findByDesignation(String designation) throws Exception {
        return produitDao.findByDesignation(designation);
    }
    
    public void delete(Produit produit) throws Exception {
        produitDao.delete(produit);
    }
    
    public void update(Produit produit, long id) throws Exception {
        produitDao.update(produit, id);
    }
    
    public void save(Produit produit) throws Exception {
        produitDao.save(produit);
    }
    
    public long getNextId() throws Exception {
        return produitDao.findMaxId() + 1;
    }
}
