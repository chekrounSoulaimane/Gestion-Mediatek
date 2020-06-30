/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Client;
import dao.ClientDao;

/**
 *
 * @author Soulaimane
 */
public class ClientService {
    
    private final ClientDao clientDao = new ClientDao();
    
    public Client findByCin(String cin) throws Exception {
        return clientDao.findByCin(cin);
    }
    
    public void save(Client client) throws Exception {
        clientDao.save(client);
    }
    
    public long nextId() throws Exception {
        return clientDao.findMaxId() + 1;
    }
}
