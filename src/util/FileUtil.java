/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Soulaimane
 */
public class FileUtil {
    
    public static File openFileDialog(Stage stage, String fileType) {
        FileChooser.ExtensionFilter extensionFilter = null;
        switch (fileType) {
            case "Fichiers Texte":
                extensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
                break;
            case "Fichiers Image":
                extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
                break;
            case "Fichiers Audio":
                extensionFilter = new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac");
                break;
            case "Tout les Fichiers":
                extensionFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
                break;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir");
        fileChooser.setInitialDirectory(new File("C:\\Users\\pc\\Pictures"));
        fileChooser.getExtensionFilters().addAll(extensionFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            return selectedFile;
        } else {
            return null;
        }
    }
}
