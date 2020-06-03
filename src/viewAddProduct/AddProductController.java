/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewAddProduct;

import bean.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class AddProductController implements Initializable {

    private final ProduitService produitService = new ProduitService();

    @FXML
    private TextField idTextField, designationTextField, prixTextField, quantiteTextField;
    @FXML
    private Button buttonAnnuler, buttonAjouter;
    @FXML
    private Label lblErrDesignation, lblErrPrix, lblErrQuantite;

    private Produit theProduit = new Produit();

    public void monConstructeur(long id) {
        idTextField.setText(String.valueOf(id));
        theProduit.setId(id);
    }

    public void addProduct(ActionEvent actionEvent) throws Exception {
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
            produitService.save(theProduit);
            buttonAjouter.getScene().getWindow().hide();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        System.out.println();
        buttonAnnuler.getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
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
