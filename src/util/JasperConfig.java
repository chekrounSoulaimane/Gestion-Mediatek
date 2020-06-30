/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Soulaimane
 */
public class JasperConfig {
    
    private static String cheminJasper="C:\\Users\\pc\\Documents\\NetBeansProjects\\Gestion-Mediatek\\src\\report\\facture.jasper";
    private static String cheminExport="C:\\Users\\pc\\Documents\\NetBeansProjects\\Gestion-Mediatek\\src\\facturePdf\\";

    public static String getCheminJasper() {
        return cheminJasper;
    }

    public static void setCheminJasper(String cheminJasper) {
        JasperConfig.cheminJasper = cheminJasper;
    }

    public static String getCheminExport() {
        return cheminExport;
    }

    public static void setCheminExport(String cheminExport) {
        JasperConfig.cheminExport = cheminExport;
    }
    
}
