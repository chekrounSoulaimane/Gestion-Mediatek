/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewMain;

import bean.Client;
import bean.Facture;
import bean.Facture_ligne;
import bean.Journal_avertissement;
import bean.Paiement;
import bean.Produit;
import bean.Utilisateur;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import service.ClientService;
import service.FactureService;
import service.Facture_ligneService;
import service.Journal_avertissementService;
import service.PaiementService;
import service.ProduitService;
import service.UtilisateurService;
import util.FileUtil;
import util.FxUtil;
import util.HashUtil;
import util.JasperUtil;
import util.StringUtil;
import viewAddClient.AddClientController;
import viewAddProduct.AddProductController;
import viewProductUpdate.ProductUpdateController;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class MainController implements Initializable {

    private final ProduitService produitService = new ProduitService();
    private final ClientService clientService = new ClientService();
    private final FactureService factureService = new FactureService();
    private final Facture_ligneService facture_ligneService = new Facture_ligneService();
    private final PaiementService paiementService = new PaiementService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final Journal_avertissementService journal_avertissementService = new Journal_avertissementService();

    @FXML
    private AnchorPane anchorPaneHome, anchorPaneProduct, anchorPaneCommande, anchorPaneClient, anchorPaneStatique;
    @FXML
    private JFXTabPane anchorConnectedUser;
    @FXML
    private Button homeButton, productButton, commandeButton, clientButton, userButton, statButton;
    @FXML
    private StackPane notificationStackPane;
    @FXML
    private Label notificationLabel;

    public void handleVboxButtonsClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == homeButton) {
            anchorPaneHome.toFront();
        } else if (actionEvent.getSource() == productButton) {
            anchorPaneProduct.toFront();
        } else if (actionEvent.getSource() == commandeButton) {
            anchorPaneCommande.toFront();
        } else if (actionEvent.getSource() == clientButton) {
            anchorPaneClient.toFront();
        } else if (actionEvent.getSource() == userButton) {
            anchorConnectedUser.toFront();
        } else if (actionEvent.getSource() == statButton) {
            anchorPaneStatique.toFront();
        }

        Button[] buttons = {homeButton, productButton, commandeButton, clientButton, statButton};
        Button button = (Button) actionEvent.getSource();
        if (!button.equals(userButton)) {
            button.setStyle("-fx-border-color : #9B59B6;");
        }

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

        Image image = new Image(productsImagesFolder + "card-img.jpg");
        circle.setFill(new ImagePattern(image));

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
        switch (produitDemande) {
            case "faible":
                textFill = "red";
                break;
            case "moyenne":
                textFill = "orange";
                break;
            case "fort":
                textFill = "green";
                break;
        }
        demande.setText("Demande " + produitDemande);
        demande.setStyle("-fx-text-fill: " + textFill + ";");

        return anchorPane;
    }

    public void searchForAProduct() throws Exception {
        String searchString = productPageSearchTextField.getText();
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

    /*---------------------------AnchorPane Acceuil (@SOulaimane)-------------------------------------------*/
    @FXML
    private AnchorPane newCommandeAnchore;

    public void openNewCommande(ActionEvent actionEvent) {
        newCommandeAnchore.toFront();
    }

    @FXML
    private TextField idTextField, quantiteTextField, textFieldTotalPaye, textFieldCinClient;
    @FXML
    private TableView<String[]> tableViewCommandeProducts;
    @FXML
    private TableColumn<String[], String> tableColumnId, tableColumnPrix;
    @FXML
    private TableColumn<String[], Integer> tableColumnQuantite;
    @FXML
    private Label lblIdErr, lblQuantiteErr, lblTotalCommande, lblTotalPayeErr, lblCinClientErr;
    @FXML
    private Hyperlink hyperlinkAjouterClient;
    @FXML
    private JFXToggleButton toggleButtonClientPassager;

    private Client createdClient = new Client();

    private double commandeTotal = 0;

    public void addProductToCommande(ActionEvent actionEvent) throws Exception {
        try {
            boolean fltr1 = true, fltr2 = true;
            Produit produit = new Produit();

            String id = idTextField.getText();
            String quantite = quantiteTextField.getText();

            if (id == null || id.isEmpty()) {
                lblIdErr.setText("Chanp Obligatoire !!!");
                lblIdErr.setVisible(true);
                fltr1 = false;
            } else {
                try {
                    Integer intId = new Integer(id);
                } catch (NumberFormatException numberFormatException) {
                    lblIdErr.setText("Nombre Invalide !!!");
                    lblIdErr.setVisible(true);
                }

                produit = produitService.findById(new Long(id));
                if (produit == null) {
                    lblIdErr.setText("Produit introuvable!!!");
                    lblIdErr.setVisible(true);
                    fltr1 = false;
                }
            }

            if (quantite == null || quantite.isEmpty()) {
                lblQuantiteErr.setText("Chanp Obligatoire !!!");
                lblQuantiteErr.setVisible(true);
                fltr2 = false;
            } else {
                try {
                    Integer intQuantite = new Integer(quantite);
                } catch (NumberFormatException numberFormatException) {
                    lblQuantiteErr.setText("Nombre Invalide !!!");
                    lblQuantiteErr.setVisible(true);
                }
            }

            if (fltr1 == true && fltr2 == true) {
                boolean exist = false;

                for (int i = 0; i < tableViewCommandeProducts.getItems().size(); i++) {
                    String[] strs = tableViewCommandeProducts.getItems().get(i);
                    if (id.equals(strs[0])) {
                        strs[2] = String.valueOf(new Integer(quantite) + new Integer(strs[2]));
                        tableViewCommandeProducts.getItems().set(i, strs);
                        exist = true;
                    }
                }

                if (exist == false) {
                    String[] strings = {id, String.valueOf(produit.getPrix()), quantite};
                    String[][] staffArray = {strings};
                    ObservableList<String[]> data = FXCollections.observableArrayList();
                    data.addAll(Arrays.asList(staffArray));

                    tableColumnId.setCellValueFactory((CellDataFeatures<String[], String> p) -> new SimpleStringProperty((p.getValue()[0])));

                    tableColumnPrix.setCellValueFactory((CellDataFeatures<String[], String> p) -> new SimpleStringProperty((p.getValue()[1])));

                    tableColumnQuantite.setCellFactory((TableColumn<String[], Integer> param) -> {
                        TableCell<String[], Integer> cell = new TableCell<String[], Integer>() {

                            @Override
                            public void updateItem(Integer item, boolean empty) {
                                item = new Integer(strings[2]);
                                if (!empty) {
                                    Spinner<Integer> spinner = new Spinner<>(0, 20, item);
                                    spinner.setId(strings[0]);
                                    spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                                        calculerTotalCommande(spinner.getId(), newValue);
                                    });
                                    setGraphic(spinner);
                                }
                            }
                        };
                        return cell;
                    });

                    tableViewCommandeProducts.getItems().add(strings);

                }

                commandeTotal += produit.getPrix() * new Integer(quantite);
                lblTotalCommande.setText(String.valueOf(commandeTotal));
                lblTotalCommande.setVisible(true);

                idTextField.setText("");
                quantiteTextField.setText("");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("lay ihdik alm3lem dkhl nombre");
        }

    }

    public void calculerTotalCommande(String id, int newValue) {
        double total = 0;
        ObservableList<String[]> observableList = tableViewCommandeProducts.getItems();

        for (int i = 0; i < observableList.size(); i++) {
            double prix = new Double(observableList.get(i)[1]);
            int quantite = new Integer(observableList.get(i)[2]);
            if (id != null && !id.isEmpty() || id.equals(observableList.get(i)[0])) {
                total += prix * newValue;
            } else {
                total += prix * quantite;
            }
        }
        lblTotalCommande.setText(String.valueOf(total));
    }

    public void saveCommande(ActionEvent actionEvent) {
        try {
            Facture facture;
            facture = constructFacture();
            List<Facture_ligne> facture_lignes = new ArrayList<>();
            int notifications = 0;
            if (facture != null) {
                for (String[] strings : tableViewCommandeProducts.getItems()) {
                    Facture_ligne facture_ligne = new Facture_ligne();
                    Produit produit = produitService.findById(new Long(strings[0]));
                    facture_ligne.setFacture(facture);
                    facture_ligne.setProduit(produit);
                    facture_ligne.setQuantite(new Integer(strings[2]));
                    facture_lignes.add(facture_ligne);

                    for (Node node : productPageVBox.getChildren()) {
                        AnchorPane anchorPane = (AnchorPane) node;
                        Label quatite = (Label) anchorPane.getChildren().get(4);
                        int nouvelQuantite = new Integer(quatite.getText()) - new Integer(strings[2]);
                        if (String.valueOf(produit.getId()).equals(anchorPane.getId())) {
                            quatite.setText(String.valueOf(nouvelQuantite));
                            if (nouvelQuantite < 5) {
                                notifications++;
                            }
                        }
                    }
                }
                factureService.save(facture);
                facture_ligneService.saveList(facture_lignes);

                reloadCommandePage();

                tableViewFactures.getItems().add(facture);

                if (notifications > 0) {
                    FxUtil.afficherBoiteDeDialogWarning("Vous avez " + notifications + " nouvelle notification");
                    notificationLabel.setText(String.valueOf(notifications));
                    notificationStackPane.setVisible(true);
                    addNotifications();
                }

                generateFacturePdf(facture, facture_lignes);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void generateFacturePdf(Facture facture, List<Facture_ligne> facture_lignes) throws FileNotFoundException, JRException, IOException {
        Map params = new HashMap();
        params.put("nomClient", facture.getClient().getNom() + " " + facture.getClient().getPrenom());
        params.put("dateEtablissement", String.valueOf(facture.getDate_etablissement()));
        params.put("total", String.valueOf(facture.getTotal()));
        params.put("totalPaye", String.valueOf(facture.getTotal_paye()));
        params.put("signe", textFieldNomUtilisateur.getText() + " " + textFieldPrenomUtilisateur.getText());
        JasperUtil.generatePdf(facture_lignes, params, true, facture.getId());
    }

    public void addNotifications() throws Exception {
        List<Journal_avertissement> list = journal_avertissementService.findByDateAvertissement();
        list.forEach((element) -> {
            AnchorPane anchorPane;
            try {
                anchorPane = FxUtil.getAnchorPane("/viewMain/NotificationBox.fxml");
                anchorPane.setId(String.valueOf(element.getId()));
                Label dateEtalissement = (Label) anchorPane.getChildren().get(1);
                Label codeProduit = (Label) anchorPane.getChildren().get(3);
                Label Contenu = (Label) anchorPane.getChildren().get(5);

                dateEtalissement.setText(String.valueOf(element.getDate_avertissement()));
                codeProduit.setText(String.valueOf(element.getProduit().getId()));
                Contenu.setText("la quantite restante en stock est inferiuer a 5.");

                vBoxNotification.getChildren().add(anchorPane);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void reloadCommandePage() {

        tableViewCommandeProducts.getItems().clear();
        lblTotalCommande.setVisible(false);
        textFieldTotalPaye.setText("");
        toggleButtonClientPassager.setSelected(false);
        textFieldCinClient.setText("");
        hyperlinkAjouterClient.setVisible(false);

    }

    public Facture constructFacture() throws Exception {
        Facture facture = new Facture();

        if (toggleButtonClientPassager.isSelected() && validerCommandePartiePaiement() == 1) {
            facture.setClient(null);
            lblCinClientErr.setVisible(false);
            facture.setTotal(new Double(lblTotalCommande.getText()));
            facture.setTotal_paye(new Double(textFieldTotalPaye.getText()));
            facture.setDate_etablissement(new Date(new java.util.Date().getTime()));
            facture.setLibelle("");
            return facture;
        } else {
            Client client = validerCommandePartieClient();
            if (client != null) {
                facture.setClient(client);
                facture.setTotal(new Double(lblTotalCommande.getText()));
                facture.setTotal_paye(new Double(textFieldTotalPaye.getText()));
                facture.setDate_etablissement(new Date(new java.util.Date().getTime()));
                facture.setLibelle("");
                return facture;
            }
        }

        return null;
    }

    public Client validerCommandePartieClient() throws Exception {
        int res = validerCommandePartiePaiement();
        String cinClient = textFieldCinClient.getText();

        Client client = new Client();

        if (res == 1) {
            if (!toggleButtonClientPassager.isSelected()) {
                if (cinClient == null || cinClient.isEmpty()) {
                    lblCinClientErr.setText("Champ obligatoire !!!");
                    lblCinClientErr.setVisible(true);
                } else {
                    client = checkClientExistance(cinClient);
                    if (client != null) {
                        return client;
                    }
                }
            }
        } else if (res == 0) {
            if (cinClient == null || cinClient.isEmpty()) {
                lblCinClientErr.setText("Champ obligatoire !!!");
                lblCinClientErr.setVisible(true);
            } else {
                client = clientService.findByCin(cinClient);
                if (client == null) {
                    lblCinClientErr.setText("client introuvable");
                    lblCinClientErr.setVisible(true);
                    hyperlinkAjouterClient.setVisible(true);
                } else {
                    return client;
                }
                hyperlinkAjouterClient.setVisible(true);
                return client;
            }
        }
        return null;
    }

    public Client checkClientExistance(String cinClient) throws Exception {
        Client client = clientService.findByCin(cinClient);
        if (client == null) {
            lblCinClientErr.setText("client introuvable");
            lblCinClientErr.setVisible(true);
            hyperlinkAjouterClient.setVisible(true);
            return null;
        } else {
            return client;
        }
    }

    public int validerCommandePartiePaiement() {
        Double total = new Double(lblTotalCommande.getText());
        Double totalPaye;

        if (textFieldTotalPaye.getText() == null || textFieldTotalPaye.getText().isEmpty()) {
            lblTotalPayeErr.setText("Champ obligatoire !!!");
            lblTotalPayeErr.setVisible(true);
        } else {
            totalPaye = new Double(textFieldTotalPaye.getText());
            if (totalPaye.equals(total)) {
                return 1;
            } else if (totalPaye < total) {
                return 0;
            } else {
                return -1;
            }
        }
        return -2;
    }

    /*---------------------------AnchorPane Facture (@SOulaimane)-------------------------------------------*/
    @FXML
    private ComboBox<String> comboBoxFacturesType;
    @FXML
    private TableView<Facture> tableViewFactures;
    @FXML
    private TableColumn<Facture, Long> tableColumnCodeFacture;
    @FXML
    private TableColumn<Facture, String> tableColumnLibelle;
    @FXML
    private TableColumn<Facture, Date> tableColumnDateEtablissement;
    @FXML
    private TableColumn<Facture, Double> tableColumnTotal, tableColumnTotalPaye;
    @FXML
    private TableColumn<Facture, String> tableColumnCodeClient;
    @FXML
    private TextField textFieldSearchForFacture;
    @FXML
    private AnchorPane anchorPanePaiement;
    @FXML
    private Label labelCodeFactureSelectionne;
    @FXML
    private TextField textFieldMontantPaiement;
    @FXML
    private TableView<Paiement> tableViewPaiements;
    @FXML
    private TableColumn<Paiement, Long> tableColumnCodePaiement;
    @FXML
    private TableColumn<Paiement, Date> tableColumnDatePaiement;
    @FXML
    private TableColumn<Paiement, Double> tableColumnMontantPaiement;
    @FXML
    private Label labelMontantPaiementErr, labelRestePaiement;

    public void handleComboBoxFacturesType(ActionEvent actionEvent) throws Exception {
        String value = comboBoxFacturesType.getValue();
        tableViewFactures.getItems().clear();
        if (value.equalsIgnoreCase("tous")) {
            customizeTableViewFactures(factureService.findAll());
        } else if (value.equalsIgnoreCase("Commande payé")) {
            customizeTableViewFactures(factureService.findFacturePaye());
        } else if (value.equalsIgnoreCase("Commande non-complet")) {
            customizeTableViewFactures(factureService.findFactureNonComplet());
        }
    }

    public void customizeTableViewFactures(List<Facture> factures) {
        final ObservableList<Facture> OLFactures = FXCollections.observableArrayList(factures);
        tableViewFactures.setItems(OLFactures);
        tableColumnCodeFacture.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tableColumnDateEtablissement.setCellValueFactory(new PropertyValueFactory<>("date_etablissement"));
        tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableColumnCodeClient.setCellValueFactory((CellDataFeatures<Facture, String> param) -> {
            ObservableValue<String> libelle = new SimpleStringProperty(String.valueOf(param.getValue().getClient().getId()));
            if (libelle.getValue().equals("0")) {
                return new SimpleStringProperty("client passager");
            }
            return libelle;
        });
        tableColumnTotalPaye.setCellValueFactory(new PropertyValueFactory<>("total_paye"));
    }

    public void searchForFacture(String oldValue, String newValue) throws Exception {
        if (newValue.isEmpty()) {
            customizeTableViewFactures(factureService.findAll());
        } else if (oldValue.length() < newValue.length()) {
            for (int i = 0; i < tableViewFactures.getItems().size(); i++) {
                Facture facture = tableViewFactures.getItems().get(i);
                boolean exist = false;

                if (newValue.length() == 1 && StringUtil.isNumeric(newValue)) {
                    long value = new Long(newValue);
                    if (value == facture.getId()) {
                        exist = true;
                    }
                }

                if (facture.getLibelle().contains(newValue)) {
                    exist = true;
                }

                if (exist == false) {
                    tableViewFactures.getItems().remove(i);
                    i--;
                }
            }
        } else {
            customizeTableViewFactures(factureService.findByIdOrLibelle(newValue));
        }
    }

    public void viewFacturePaiements(ActionEvent actionEvent) throws Exception {
        Facture facture = tableViewFactures.getSelectionModel().getSelectedItem();
        if (facture == null) {
            FxUtil.afficherBoiteDeDialogWarning("Aucune Facture n'est selectionnée !!!");
        } else {
            labelCodeFactureSelectionne.setText(String.valueOf(facture.getId()));
            List<Paiement> paiements = paiementService.findByFacture(facture.getId());
            final ObservableList<Paiement> OLPaiements = FXCollections.observableArrayList(paiements);
            tableViewPaiements.setItems(OLPaiements);
            tableColumnCodePaiement.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableColumnDatePaiement.setCellValueFactory(new PropertyValueFactory<>("date_paiement"));
            tableColumnMontantPaiement.setCellValueFactory(new PropertyValueFactory<>("montant"));
            double reste = facture.getTotal() - facture.getTotal_paye();
            labelRestePaiement.setText(String.valueOf(reste));
            anchorPanePaiement.toFront();
        }
    }

    public void addPaiement(ActionEvent actionEvent) throws Exception {
        String value = textFieldMontantPaiement.getText();
        Facture facture = tableViewFactures.getSelectionModel().getSelectedItem();
        if (value == null || value.isEmpty()) {
            labelMontantPaiementErr.setText("Champ obligatoire.");
            labelMontantPaiementErr.setVisible(true);
        } else if (!labelMontantPaiementErr.isVisible()) {
            double montant = new Double(value);
            double reste = new Double(labelRestePaiement.getText());
            if (reste == 0) {
                labelMontantPaiementErr.setText("Facture completement payé.");
                labelMontantPaiementErr.setVisible(true);
            } else if (montant > reste) {
                labelMontantPaiementErr.setText("Valeur superieure au reste.");
                labelMontantPaiementErr.setVisible(true);
            } else {
                Paiement paiement = new Paiement();
                paiement.setId(paiementService.getNextId());
                paiement.setDate_paiement(new Date(new java.util.Date().getTime()));
                paiement.setFacture(facture);
                paiement.setMontant(montant);
                tableViewPaiements.getItems().add(paiement);
                paiementService.save(paiement);
                facture.setTotal_paye(facture.getTotal_paye() + montant);
                factureService.update(facture);
                labelRestePaiement.setText(String.valueOf(reste - montant));
                tableViewFactures.getItems().set((int) facture.getId() - 1, facture);
            }
        }
    }

    public void returnToFactureAnchor(ActionEvent actionEvent) {
        anchorPaneCommande.toFront();
    }

    /*---------------------------AnchorPane User Info (@SOulaimane)-------------------------------------------*/
    @FXML
    private TextField textFieldIdUtilisateur, textFieldNomUtilisateur, textFieldPrenomUtilisateur,
            textFieldEmailUtilisateur, textFieldPasswordUtilisateur, textFieldGenreUtilisateur,
            textFieldDateNaissanceUtilisateur;
    @FXML
    private Label vBoxUsername;
    @FXML
    private TextField textFieldOldPassword, textFieldNewPassword, textFieldNewPasswordConfirmation;
    @FXML
    private Label labelOldPasswordErr, labelNewPasswordErr, labelNewPasswordConfirmationErr;
    @FXML
    private VBox vBoxNotification;

    public void initialiseUserAnchor(Utilisateur utilisateur) {
        textFieldIdUtilisateur.setText(String.valueOf(utilisateur.getId()));
        textFieldNomUtilisateur.setText(utilisateur.getNom());
        textFieldPrenomUtilisateur.setText(utilisateur.getPrenom());
        textFieldEmailUtilisateur.setText(utilisateur.getEmail());
        textFieldPasswordUtilisateur.setText(utilisateur.getPassword());
        textFieldGenreUtilisateur.setText(utilisateur.getGenre());
        textFieldDateNaissanceUtilisateur.setText(String.valueOf(utilisateur.getDate_naissance()));
        vBoxUsername.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
    }

    public void addUserPhoto(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        File file = FileUtil.openFileDialog(stage, "Image Files");
        //System.out.println(file.getAbsolutePath());
    }

    public void updateUserInformation(ActionEvent actionEvent) throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(new Long(textFieldIdUtilisateur.getText()));
        utilisateur.setNom(textFieldNomUtilisateur.getText());
        utilisateur.setPrenom(textFieldPrenomUtilisateur.getText());
        utilisateur.setEmail(textFieldEmailUtilisateur.getText());
        utilisateur.setGenre(textFieldGenreUtilisateur.getText());
        utilisateur.setDate_naissance(Date.valueOf(textFieldDateNaissanceUtilisateur.getText()));
        utilisateurService.update(utilisateur);
    }

    public void updatePassword(ActionEvent actionEvent) throws Exception {
        boolean fltr1 = true, fltr2 = true, fltr3 = true;
        String oldPassword = HashUtil.encryptPassword(textFieldOldPassword.getText());
        String newPassword = textFieldNewPassword.getText();
        String newPasswordConfirmation = textFieldNewPasswordConfirmation.getText();

        if (oldPassword == null || oldPassword.isEmpty()) {
            labelOldPasswordErr.setText("Champ obligatoire.");
            labelOldPasswordErr.setVisible(true);
            fltr1 = false;
        } else if (!oldPassword.equals(textFieldPasswordUtilisateur.getText())) {
            labelOldPasswordErr.setText("Mot de passe incorrect.");
            labelOldPasswordErr.setVisible(true);
            fltr1 = false;
        }

        if (newPassword == null || newPassword.isEmpty()) {
            labelNewPasswordErr.setText("Champ obligatoire.");
            labelNewPasswordErr.setVisible(true);
            fltr2 = false;
        }

        if (newPasswordConfirmation == null || newPasswordConfirmation.isEmpty()) {
            labelNewPasswordConfirmationErr.setText("Champ obligatoire.");
            labelNewPasswordConfirmationErr.setVisible(true);
            fltr3 = false;
        } else if (!newPassword.equals(newPasswordConfirmation)) {
            textFieldNewPassword.setText("");
            textFieldNewPasswordConfirmation.setText("");
            labelNewPasswordConfirmationErr.setText("Mot de passe non identique.");
            labelNewPasswordConfirmationErr.setVisible(true);
            fltr3 = false;
        }

        if (fltr1 == true && fltr2 == true && fltr3 == true) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(new Long(textFieldIdUtilisateur.getText()));
            utilisateur.setNom(textFieldNomUtilisateur.getText());
            utilisateur.setPrenom(textFieldPrenomUtilisateur.getText());
            utilisateur.setEmail(textFieldEmailUtilisateur.getText());
            utilisateur.setPassword(HashUtil.encryptPassword(newPassword));
            utilisateur.setGenre(textFieldGenreUtilisateur.getText());
            utilisateur.setDate_naissance(Date.valueOf(textFieldDateNaissanceUtilisateur.getText()));
            utilisateurService.update(utilisateur);

            FxUtil.afficherBoiteDeDialogWarning("Votre mot de passe est modifié.");
            textFieldOldPassword.setText("");
            textFieldNewPassword.setText("");
            textFieldNewPasswordConfirmation.setText("");
        }
    }

    /*---------------------------AnchorPane Statistique(@SOulaimane)-------------------------------------------*/
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart peiChart;

    public void removeNotification(ActionEvent actionEvent) {
        notificationStackPane.setVisible(false);
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

        vBoxNotification.setSpacing(10);

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

        idTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblIdErr.setVisible(false);
        });

        quantiteTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblQuantiteErr.setVisible(false);
        });

        // page commande total paye
        textFieldTotalPaye.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (tableViewCommandeProducts.getItems() != null && !tableViewCommandeProducts.getItems().isEmpty()) {
                if (textFieldTotalPaye.getText() == null || textFieldTotalPaye.getText().isEmpty()) {
                    lblTotalPayeErr.setVisible(false);
                } else {
                    try {
                        Double total = new Double(lblTotalCommande.getText());
                        Double totalPaye = new Double(textFieldTotalPaye.getText());
                        if (totalPaye > total) {
                            lblTotalPayeErr.setText("Total paye > Total !!!");
                            lblTotalPayeErr.setVisible(true);
                        } else {
                            lblTotalPayeErr.setVisible(false);
                        }
                    } catch (NumberFormatException numberFormatException) {
                        lblTotalPayeErr.setText("Veuillez entrer un nombre.");
                        lblTotalPayeErr.setVisible(true);
                    }
                }
            }
        });

        EventHandler<ActionEvent> eventHandler = (ActionEvent event) -> {
            Parent root = null;
            try {
                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/viewAddClient/AddClient.fxml"));
                root = fXMLLoader.load();
                AddClientController addClientController = fXMLLoader.getController();

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                stage.getScene().getWindow().setOnHiding((WindowEvent windowEvent) -> {
                    textFieldCinClient.setText(addClientController.getNewClient().getCin());
                    createdClient = addClientController.getNewClient();
                });
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        hyperlinkAjouterClient.setOnAction(eventHandler);

        // page commande cin client
        textFieldCinClient.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblCinClientErr.setVisible(false);
        });

        // anchore Facture
        ObservableList<String> observableList = FXCollections.observableArrayList("Tous", "Commande payé", "Commande non-complet");
        comboBoxFacturesType.setItems(observableList);
        comboBoxFacturesType.setValue(comboBoxFacturesType.getItems().get(0));

        try {
            customizeTableViewFactures(factureService.findAll());
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        textFieldSearchForFacture.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                searchForFacture(oldValue, newValue);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //anchor paiement
        textFieldMontantPaiement.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                Double value = new Double(newValue);
                labelMontantPaiementErr.setVisible(false);
            } catch (NumberFormatException numberFormatException) {
                labelMontantPaiementErr.setText("Veuillez entrer un nombre.");
                labelMontantPaiementErr.setVisible(true);
            }
        });

        //tabPane utilisateur
        textFieldOldPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelOldPasswordErr.setVisible(false);
        });
        textFieldNewPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelNewPasswordErr.setVisible(false);
        });
        textFieldNewPasswordConfirmation.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelNewPasswordConfirmationErr.setVisible(false);
        });

        //anchorPane statistique
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart.setTitle("Graphe des ventes des derniers 12 mois");
        String[] mois = {"jan", "fev", "mar", "avr", "mai", "juin", "jull", "aout", "sep", "oct", "nov", "dec"};
        double[] sommesVentes = {};
        java.util.Date date = new java.util.Date();
        int month = date.getMonth();
        try {
            sommesVentes = factureService.getTotalOfLastTwelveMonths(month, 2020);

            for (int i = 0; i < 12; i++) {
                series.getData().add(new XYChart.Data<>(mois[month], sommesVentes[i]));
                month++;
                if (month >= 12) {
                    month = 0;
                }
            }
            series.setName("montant en DH");
            barChart.getData().add(series);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List<String[]> resultat = facture_ligneService.getBestSellers();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (String[] element : resultat) {
                pieChartData.add(new PieChart.Data(element[0], new Double(element[1])));
            }

            peiChart.getData().addAll(pieChartData);
            peiChart.setTitle("Best sellers");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        anchorConnectedUser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if (newValue.intValue() == 2) {
                    notificationStackPane.setVisible(false);
                }
            }
        });
    }

}
