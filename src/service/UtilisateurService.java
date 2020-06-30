/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Utilisateur;
import dao.UtilisateurDao;

/**
 *
 * @author Soulaimane
 */
public class UtilisateurService {

    private final UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Utilisateur seConnecter(String email, String password) throws Exception {
        return utilisateurDao.findByEmailAndPassword(email, password);
    }

    public Utilisateur findByEmail(String email) throws Exception {
        return utilisateurDao.findByEmail(email);
    }

    public long getNextId() throws Exception {
        return utilisateurDao.findMaxId() + 1;
    }

    public void save(Utilisateur utilisateur) throws Exception {
        utilisateurDao.save(utilisateur);
    }
    
    public void update(Utilisateur utilisateur) throws Exception {
        utilisateurDao.update(utilisateur, utilisateur.getId());
    }
}
