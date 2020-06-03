/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import viewMain.MainController;

/**
 *
 * @author Soulaimane
 */
public class FxUtil {
    
    public static AnchorPane getAnchorPane(String path) throws IOException {
        AnchorPane panierElem = null;
        try {
            URL fileUrl = MainController.class.getResource(path);
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("File not found");
            }
            panierElem = new FXMLLoader().load(fileUrl);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return panierElem;
    }
    
    public static void afficherBoiteDeDialogWarning(String errMssg) {
        Alert dialogW = new Alert(Alert.AlertType.WARNING);
        dialogW.setTitle("Message d'erreure");
        dialogW.setHeaderText(null); // No header
        dialogW.setContentText("Caution : " + errMssg);
        dialogW.showAndWait();
    }
    
    public static boolean afficherBoiteDeDialogConfirmation(String errMssg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(errMssg);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            } else {
                return false;
            }
    }
}
