/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewSignUp;

import bean.Utilisateur;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UtilisateurService;
import util.HashUtil;
import util.StringUtil;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class SignUpController implements Initializable {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private TextField textFieldNom, textFieldPrenom, textFieldEmail;
    @FXML
    private PasswordField textFieldPassword, textFieldPasswordConfirm;
    @FXML
    private ComboBox<String> textFieldGenre;
    @FXML
    private DatePicker textFieldDateNaissance;
    @FXML
    private Label labelNom, labelPrenom, labelEmail, labelPassword, labelPasswordConfirm, labelGenre,
            labelDateNaissance;

    public void save(ActionEvent actionEvent) throws Exception {
        boolean fltr1 = true, fltr2 = true, fltr3 = true, fltr4 = true, fltr5 = true, fltr6 = true,
                fltr7 = true;
        String nom = textFieldNom.getText();
        String prenom = textFieldPrenom.getText();
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        String passwordConfirm = textFieldPasswordConfirm.getText();
        String genre = textFieldGenre.getValue();
        Date dateNaissance = Date.valueOf(textFieldDateNaissance.getValue());

        if (nom == null || nom.isEmpty()) {
            labelNom.setText("Champ obligatoire.");
            labelNom.setVisible(true);
            fltr1 = false;
        } else if (labelNom.isVisible()) {
            fltr1 = false;
        }

        if (prenom == null || prenom.isEmpty()) {
            labelPrenom.setText("Champ obligatoire.");
            labelPrenom.setVisible(true);
            fltr2 = false;
        } else if (labelNom.isVisible()) {
            fltr2 = false;
        }

        if (email == null || email.isEmpty()) {
            labelEmail.setText("Champ obligatoire.");
            labelEmail.setVisible(true);
            fltr3 = false;
        } else if (!isValidEmail(email)) {
            labelEmail.setText("Email invalide.");
            labelEmail.setVisible(true);
            fltr3 = false;
        }

        if (password == null || password.isEmpty()) {
            labelPassword.setText("Champ obligatoire.");
            labelPassword.setVisible(true);
            fltr4 = false;
        }

        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            labelPasswordConfirm.setText("Champ obligatoire.");
            labelPasswordConfirm.setVisible(true);
            fltr5 = false;
        } else if (!password.contains(passwordConfirm) || !password.equals(passwordConfirm)) {
            labelPasswordConfirm.setText("mot de passe incompatible.");
            labelPasswordConfirm.setVisible(true);
            fltr5 = false;
        } else if (labelPasswordConfirm.isVisible()) {
            fltr5 = false;
        }

        if (genre == null || genre.isEmpty()) {
            labelGenre.setText("Champ obligatoire.");
            labelGenre.setVisible(true);
            fltr6 = false;
        }

        if (dateNaissance == null) {
            labelDateNaissance.setText("Champ obligatoire.");
            labelDateNaissance.setVisible(true);
            fltr7 = false;
        }

        if (fltr1 == true && fltr2 == true && fltr3 == true && fltr4 == true && fltr5 == true && fltr6 == true
                && fltr7 == true) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(utilisateurService.getNextId());
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setPassword(HashUtil.encryptPassword(password));
            utilisateur.setGenre(genre);
            utilisateur.setDate_naissance(dateNaissance);
            utilisateurService.save(utilisateur);
            Button button = (Button) actionEvent.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }

    }

    public boolean isValidEmail(String email) {
        final String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldNom.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (StringUtil.containsNumber(newValue)) {
                labelNom.setText("le nom ne peut pas contenir des chiffres.");
                labelNom.setVisible(true);
            } else {
                labelNom.setVisible(false);
            }
        });

        textFieldPrenom.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (StringUtil.containsNumber(newValue)) {
                labelPrenom.setText("le prenom ne peut pas contenir des chiffres.");
                labelPrenom.setVisible(true);
            } else {
                labelPrenom.setVisible(false);
            }
        });

        textFieldEmail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelEmail.setVisible(false);
        });

        textFieldPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelPassword.setVisible(false);
        });

        textFieldPasswordConfirm.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String password = textFieldPassword.getText();
            String passwordConfirm = textFieldPasswordConfirm.getText();

            if (!password.contains(passwordConfirm) || !password.equals(passwordConfirm)) {
                labelPasswordConfirm.setText("mot de passe incompatible.");
                labelPasswordConfirm.setVisible(true);
            } else {
                labelPasswordConfirm.setVisible(false);
            }
        });

        ObservableList<String> observableList = FXCollections.observableArrayList("homme", "femme");
        textFieldGenre.setItems(observableList);
    }

}
