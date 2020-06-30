/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Soulaimane
 */
public class JasperUtil {
    
    public static void generatePdf(List list, String cheminJapser, String cheminExport, Map params, boolean isPdfVisible) throws FileNotFoundException, JRException, IOException {
        InputStream reportSource = null;
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
        reportSource = new FileInputStream(cheminJapser);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        //  JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\monpremierpdf.pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, cheminExport);
        if (isPdfVisible) {
            showPdf(cheminExport);
        }
          reportSource.close();
    }

      public static void generatePdf(List list, Map params, boolean isPdfVisible, long codeFacture) throws FileNotFoundException, JRException, IOException {
          generatePdf(list, JasperConfig.getCheminJasper(), JasperConfig.getCheminExport() + "facture" + codeFacture + ".pdf", params, isPdfVisible);
    }

    public static void showPdf(String chemin) throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + chemin);
    }
    
}
