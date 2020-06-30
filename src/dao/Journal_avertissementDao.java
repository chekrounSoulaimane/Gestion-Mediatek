/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Journal_avertissement;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Soulaimane
 */
public class Journal_avertissementDao extends AbstractDao<Journal_avertissement> {
    
    public Journal_avertissementDao() {
        super(Journal_avertissement.class);
    }
    
    public List<Journal_avertissement> findByDateAvertissement() throws Exception {
        String requette = "SELECT * FROM Journal_avertissement WHERE date_avertissement LIKE TO_DATE(SYSDATE, 'dd/mm/yy')";
        return load(requette);
    }
}
