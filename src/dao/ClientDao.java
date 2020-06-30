/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Client;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class ClientDao extends AbstractDao<Client>{
    
    public ClientDao() {
        super(Client.class);
    }
    
    public Client findByCin(String cin) throws Exception {
        String requette = "SELECT * FROM client WHERE cin = '" + cin + "'";
        List<Client> list = load(requette);  
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public long findMaxId() throws Exception {
        long id = 0;
        String requette = "SELECT MAX(id) FROM client";
        ResultSet resultSet = ConnectDB.load(requette);
        while(resultSet.next()) {
            id = resultSet.getLong("MAX(id)");
        }
        return id;
    }
}
