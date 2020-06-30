/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Utilisateur;
import java.sql.ResultSet;
import java.util.List;
/**
 *
 * @author Soulaimane
 */
public class UtilisateurDao extends AbstractDao<Utilisateur> {
    
    public UtilisateurDao() {
        super(Utilisateur.class);
    }
    
    public Utilisateur findByEmailAndPassword(String email, String password) throws Exception {
        String requette = "SELECT * FROM utilisateur WHERE email='" + email + "' AND password = '" + password + "'";    
        List<Utilisateur> list = load(requette);  
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public Utilisateur findByEmail(String email) throws Exception {
        String requette = "SELECT * FROM utilisateur WHERE email='" + email + "'";    
        List<Utilisateur> list = load(requette);  
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM utilisateur";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }
    
}
