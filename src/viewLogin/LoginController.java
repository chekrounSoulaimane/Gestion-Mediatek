/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewLogin;

import bean.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UtilisateurService;
import util.EmailUtil;
import util.FxUtil;
import util.HashUtil;
import viewMain.MainController;

/**
 * FXML Controller class
 *
 * @author Soulaimane
 */
public class LoginController implements Initializable {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Label labelEmailErr, labelConnectionErr, labelPasswordErr;
    @FXML
    private Button signIn;

    public void login(ActionEvent actionEvent) throws Exception {
        boolean fltr1 = true, fltr2 = true;
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();

        if (email == null || email.isEmpty()) {
            labelEmailErr.setText("Champ obligatoire.");
            labelEmailErr.setVisible(true);
            fltr1 = false;
        } else if (!isValidEmail(email)) {
            labelEmailErr.setText("Email Invalide.");
            labelEmailErr.setVisible(true);
            fltr1 = false;
        } else {
            Utilisateur utilisateur = utilisateurService.findByEmail(email);
            if (utilisateur == null) {
                labelEmailErr.setText("Veuillez verifier votre adresse email.");
                labelEmailErr.setVisible(true);
                fltr1 = false;
            }
        }

        if (password == null || password.isEmpty()) {
            labelPasswordErr.setText("Champ obligatoire.");
            labelPasswordErr.setVisible(true);
            fltr2 = false;
        }

        if (fltr1 == true && fltr2 == true) {
            Utilisateur utilisateur = utilisateurService.seConnecter(email, HashUtil.encryptPassword(password));
            if (utilisateur == null) {
                labelConnectionErr.setText("email ou mot de passe est incorrect.");
                labelConnectionErr.setVisible(true);
            } else {
                labelConnectionErr.setVisible(false);
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Parent root = null;
                try {
                    FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/viewMain/Main.fxml"));
                    root = fXMLLoader.load();
                    MainController mainController = fXMLLoader.getController();
                    mainController.initialiseUserAnchor(utilisateur);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Stage stage1 = new Stage();
                Scene scene = new Scene(root);
                stage1.setScene(scene);
                stage1.show();
            }
        }
    }

    public boolean isValidEmail(String email) {
        final String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/viewSignUp/SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void sendEmail(ActionEvent actionEvent) throws Exception {
        boolean fltr1 = true;
        String email = textFieldEmail.getText();
        Utilisateur utilisateur = new Utilisateur();

        if (email == null || email.isEmpty()) {
            labelEmailErr.setText("Champ obligatoire.");
            labelEmailErr.setVisible(true);
            fltr1 = false;
        } else if (!isValidEmail(email)) {
            labelEmailErr.setText("Email Invalide.");
            labelEmailErr.setVisible(true);
            fltr1 = false;
        } else {
            utilisateur = utilisateurService.findByEmail(email);
            if (utilisateur == null) {
                labelEmailErr.setText("Veuillez verifier votre adresse email.");
                labelEmailErr.setVisible(true);
                fltr1 = false;
            }
        }

        if (fltr1 == true) {
            boolean value = FxUtil.afficherBoiteDeDialogConfirmation("On va vous envoyer un mot de passe temporaire.");
            if (value == true) {
                int sendPasswordMail = EmailUtil.sendPasswordMail(email);
                if (sendPasswordMail > 0) {
                    utilisateur.setPassword(HashUtil.encryptPassword(String.valueOf(sendPasswordMail)));
                    utilisateurService.update(utilisateur);
                }
            }
        }
    }

    public void signInWithFacebook() {

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldEmail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelEmailErr.setVisible(false);
        });

        textFieldPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            labelPasswordErr.setVisible(false);
        });
    }

}
