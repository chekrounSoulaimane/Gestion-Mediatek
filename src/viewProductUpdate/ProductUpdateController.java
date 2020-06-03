/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewProductUpdate;

import bean.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ProduitService;
import viewMain.MainController;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class ProductUpdateController implements Initializable {

    private final ProduitService produitService = new ProduitService();

    @FXML
    private TextField idTextField, designationTextField, prixTextField, quantiteTextField;
    @FXML
    private Button buttonAnnuler, buttonModifier;
    @FXML
    private Label lblErrDesignation, lblErrPrix, lblErrQuantite;

    private Produit theProduit = new Produit();

    public void monConstructeur(Produit produit) {
        theProduit = produit;
        System.out.println(produit.toString());
        idTextField.setText(String.valueOf(produit.getId()));
        designationTextField.setText(produit.getDesignation());
        prixTextField.setText(String.valueOf(produit.getPrix()));
        quantiteTextField.setText(String.valueOf(produit.getQuantite_stock()));
    }

    public void updateProduct() throws Exception {
        boolean fltr1 = true, fltr2 = true, fltr3 = true;

        String prix = prixTextField.getText();
        String quantite = quantiteTextField.getText();
        String designation = designationTextField.getText();

        if (designation == null || designation.equals("")) {
            lblErrDesignation.setText("Champ obligatoire");
            lblErrDesignation.setVisible(true);
            fltr1 = false;
        }
        
        if (prix.contains(",") || prix == null || prix.equals("")) {
            if (prix == null || prix.equals("")) {
                lblErrPrix.setText("Champ obligatoire");
            } else if (prix.contains(",")) {
                lblErrPrix.setText("Prix Invalide");
            }
            lblErrPrix.setVisible(true);
            fltr2 = false;
        }
        
        if (quantite.contains(".") || quantite.contains(",") || quantite == null || quantite.equals("")) {
            if (prix == null || prix.equals("")) {
                lblErrQuantite.setText("Champ obligatoire");
            } else if (quantite.contains(".") || quantite.contains(",")) {
                lblErrQuantite.setText("Quantite Invalide");
            }
            lblErrQuantite.setVisible(true);
            fltr3 = false;
        }
        
        if (fltr1 == true && fltr2 == true && fltr3 == true) {
            theProduit.setDesignation(designation);
            theProduit.setPrix(new Double(prix));
            theProduit.setQuantite_stock(new Integer(quantite));
            produitService.update(theProduit, theProduit.getId());
            buttonModifier.getScene().getWindow().hide();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        System.out.println();
        buttonAnnuler.getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        designationTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblErrDesignation.setVisible(false);
        });
        prixTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblErrPrix.setVisible(false);
        });
        quantiteTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblErrQuantite.setVisible(false);
        });
    }

}
