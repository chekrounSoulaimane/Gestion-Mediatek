/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Journal_avertissement;
import dao.Journal_avertissementDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class Journal_avertissementService {
    
    private final Journal_avertissementDao journal_avertissementDao = new Journal_avertissementDao();
    
    public List<Journal_avertissement> findByDateAvertissement() throws Exception {
        return journal_avertissementDao.findByDateAvertissement();
    }
}
