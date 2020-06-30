/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewAddClient;

import bean.Client;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ClientService;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class AddClientController implements Initializable {

    private final ClientService clientService = new ClientService();
    private Client newClient = new Client();

    @FXML
    private TextField textFieldCinClient, textFieldNomClient, textFieldPrenomClient;
    @FXML
    private DatePicker datePickerNaissanceClient;
    @FXML
    private Label labelNomErr, lblPrenomErr, labelCinErr, labelDateErr;
    @FXML
    private Button buttonSave;

    public void save(ActionEvent actionEvent) throws Exception {

        if (lblPrenomErr.isVisible() == false && labelNomErr.isVisible() == false) {
            String errMessage = "Champ obligatoire";
            boolean fltr1 = true, fltr2 = true, fltr3 = true, fltr4 = true;

            String cin = textFieldCinClient.getText();
            String nom = textFieldNomClient.getText();
            String prenom = textFieldPrenomClient.getText();
            Date date_naissance = Date.valueOf(datePickerNaissanceClient.getValue());

            if (cin == null || cin.isEmpty()) {
                labelCinErr.setText(errMessage);
                labelCinErr.setVisible(true);
                fltr1 = false;
            }

            if (nom == null || nom.isEmpty()) {
                labelNomErr.setText(errMessage);
                labelNomErr.setVisible(true);
                fltr1 = false;
            }

            if (prenom == null || prenom.isEmpty()) {
                lblPrenomErr.setText(errMessage);
                lblPrenomErr.setVisible(true);
                fltr1 = false;
            }

            if (date_naissance == null) {
                labelDateErr.setText(errMessage);
                labelDateErr.setVisible(true);
                fltr1 = false;
            }

            if (fltr1 == true && fltr2 == true && fltr3 == true && fltr4 == true) {
                long id = clientService.nextId();
                Client client = new Client(id, cin, nom, prenom, date_naissance);
                clientService.save(client);
                newClient = client;
                
                Stage stage = (Stage) buttonSave.getScene().getWindow();
                stage.close();
            }
        }
    }
    
    public void close(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        textFieldNomClient.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String value = textFieldNomClient.getText();
            if (value != null || !value.isEmpty()) {
                boolean found = false;
                char[] chars = value.toCharArray();
                for (char c : chars) {
                    if (Character.isDigit(c)) {
                        found = true;
                        break;
                    }
                }

                if (found == true) {
                    labelNomErr.setText("le nom ne peut pas contenir des nombres");
                    labelNomErr.setVisible(true);
                } else {
                    labelNomErr.setVisible(false);
                }
            }
        });

        textFieldPrenomClient.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String value = textFieldPrenomClient.getText();
            if (value != null || !value.isEmpty()) {
                boolean found = false;
                char[] chars = value.toCharArray();
                for (char c : chars) {
                    if (Character.isDigit(c)) {
                        found = true;
                        break;
                    }
                }

                if (found == true) {
                    lblPrenomErr.setText("le nom ne peut pas contenir des nombres");
                    lblPrenomErr.setVisible(true);
                } else {
                    lblPrenomErr.setVisible(false);
                }
            }
        });

        textFieldCinClient.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelCinErr.setVisible(false);
        });

        EventHandler<ActionEvent> eventHandler = (ActionEvent event) -> {
            labelDateErr.setVisible(false);
        };
        datePickerNaissanceClient.setOnAction(eventHandler);
    }

    public Client getNewClient() {
        return newClient;
    }
    
    

}
