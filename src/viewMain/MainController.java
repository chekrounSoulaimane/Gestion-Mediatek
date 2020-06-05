/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewMain;

import bean.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.ProduitService;
import util.FxUtil;
import viewAddProduct.AddProductController;
import viewProductUpdate.ProductUpdateController;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class MainController implements Initializable {

    private final ProduitService produitService = new ProduitService();

    @FXML
    private AnchorPane anchorPaneHome, anchorPaneProduct, anchorPaneCommande, anchorPaneClient;
    @FXML
    private Button homeButton, productButton, commandeButton, clientButton;

    public void handleVboxButtonsClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == homeButton) {
            anchorPaneHome.toFront();
        } else if (actionEvent.getSource() == productButton) {
            anchorPaneProduct.toFront();
        } else if (actionEvent.getSource() == commandeButton) {
            anchorPaneCommande.toFront();
        } else if (actionEvent.getSource() == clientButton) {
            anchorPaneClient.toFront();
        }

        Button[] buttons = {homeButton, productButton, commandeButton, clientButton};
        Button button = (Button) actionEvent.getSource();
        button.setStyle("-fx-border-color : #9B59B6;");

        for (Button btn : buttons) {
            if (!(btn == actionEvent.getSource())) {
                btn.setStyle("-fx-border-color : #05071F;");
            }
        }
    }

    /*---------------------------AnchorPane Produit (@SOulaimane)-------------------------------------------*/
    @FXML
    private VBox productPageVBox;
    @FXML
    private TextField productPageSearchTextField;
    
    private final String productsImagesFolder = "file:///C:/Users/pc/Documents/NetBeansProjects/Gestion-Mediatek/src/images/products/";

    public void loadProductsPage(List<Produit> produits) throws IOException, Exception {
        for (int i = 0; i < produits.size(); i++) {
            productPageVBox.getChildren().add(customizeProductBox(produits.get(i)));
        }
    }

    public AnchorPane customizeProductBox(Produit produit) throws IOException, Exception {
        AnchorPane anchorPane = FxUtil.getAnchorPane("/viewMain/ProductBox.fxml");
        anchorPane.setId(String.valueOf(produit.getId()));
        Circle circle = (Circle) anchorPane.getChildren().get(0);
        Label designation = (Label) anchorPane.getChildren().get(1);
        Button deleteButton = (Button) anchorPane.getChildren().get(2);
        Label prix = (Label) anchorPane.getChildren().get(3);
        Label quatite = (Label) anchorPane.getChildren().get(4);
        Button updateButton = (Button) anchorPane.getChildren().get(5);
        Label demande = (Label) anchorPane.getChildren().get(6);
        
        //Image image = new Image("");
        //circle.setFill(new ImagePattern(image));
        
        designation.setText(produit.getDesignation());
        
        // boutton supprimer event handler
        EventHandler<ActionEvent> buttonHandler1 = (ActionEvent event) -> {
            try {
                deleteProduct(anchorPane);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        deleteButton.setOnAction(buttonHandler1);
        
        prix.setText(produit.getPrix() + " DH");
        
        quatite.setText(String.valueOf(produit.getQuantite_stock()));
        
        // boutton modifier event handler
        EventHandler<ActionEvent> buttonHandler2 = (ActionEvent event) -> {
            try {
                updateProduct(anchorPane);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        updateButton.setOnAction(buttonHandler2);
        
        String textFill = new String(); 
        String produitDemande = produitService.getDemande(produit.getId()); 
        if (produitDemande.equals("faible")) {
            textFill = "red";
        } else if (produitDemande.equals("moyenne")) {
            textFill = "#10165F";
        } else if (produitDemande.equals("fort")) {
            textFill = "green";
        }
        demande.setText("Demande " + produitDemande);
        demande.setStyle("-fx-text-fill: " + textFill + ";");
        
        return anchorPane;
    }

    public void searchForAProduct() throws Exception {
        String searchString = productPageSearchTextField.getText();
        System.out.println(searchString);
        if (searchString == null || searchString.equals("")) {
            productPageVBox.getChildren().clear();
            loadProductsPage(produitService.findAll());
        } else {
            productPageVBox.getChildren().clear();
            loadProductsPage(produitService.findByDesignation(searchString));
        }
    }

    public void deleteProduct(AnchorPane anchorPane) throws Exception {
        boolean confirmation = FxUtil.afficherBoiteDeDialogConfirmation("Voulez- vous vraiement supprimer ce produit!!:");
        if (confirmation == true) {
            System.out.println(anchorPane.getId());
            Produit produit = new Produit();
            produit.setId(new Long(anchorPane.getId()));
            produitService.delete(produit);
            productPageVBox.getChildren().clear();
            loadProductsPage(produitService.findAll());
        }
    }

    public void updateProduct(AnchorPane anchorPane) throws Exception {
        long id = new Long(anchorPane.getId());
        Parent root = null;
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/viewProductUpdate/ProductUpdate.fxml"));
            root = fXMLLoader.load();
            ProductUpdateController productUpdateController = fXMLLoader.getController();
            productUpdateController.monConstructeur(produitService.findById(id));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.getScene().getWindow().setOnHiding((WindowEvent event) -> {
            productPageVBox.getChildren().clear();
            try {
                loadProductsPage(produitService.findAll());
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void addProduct(ActionEvent actionEvent) throws Exception {
        Parent root = null;
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/viewAddProduct/AddProduct.fxml"));
            root = fXMLLoader.load();
            AddProductController addProductController = fXMLLoader.getController();
            addProductController.monConstructeur(produitService.getNextId());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.getScene().getWindow().setOnHiding((WindowEvent event) -> {
            productPageVBox.getChildren().clear();
            try {
                loadProductsPage(produitService.findAll());
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        anchorPaneHome.toFront();
        homeButton.setStyle("-fx-border-color : #9B59B6;");

        productPageVBox.setPadding(new Insets(20, 20, 20, 20));
        productPageVBox.setSpacing(10);

        try {
            loadProductsPage(produitService.findAll());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        productPageSearchTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                searchForAProduct();
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
