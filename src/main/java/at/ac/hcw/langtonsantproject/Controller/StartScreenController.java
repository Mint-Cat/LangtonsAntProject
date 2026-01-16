package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController extends SceneControl implements Initializable {

    // Diese Variablen sind mit deiner .fxml Datei verknüpft
    @FXML
    public GridPane gridPane;
    @FXML
    public Label NameLabel;
    @FXML
    public Button NewAntButton;
    @FXML
    public Button LoadAntButton;
    @FXML
    public Button DeleteAntButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* WICHTIG: Wir löschen hier NICHTS mehr mit gridPane.getChildren().clear().
           Das Layout kommt jetzt sauber aus der FXML-Datei.
        */

        // Das dunkelrote Design (CSS) laden
        String css = getClass().getResource("/at/ac/hcw/langtonsantproject/style.css").toExternalForm();

        // Wir warten kurz, bis das Fenster offen ist, und wenden dann das Design an
        Platform.runLater(() -> {
            if (gridPane.getScene() != null) {
                gridPane.getScene().getStylesheets().clear();
                gridPane.getScene().getStylesheets().add(css);
            }
        });
    }

    @FXML
    public void NewAntButtonClick(ActionEvent actionEvent) {
        // Standardwerte setzen
        AppContext.get().settings.width = 10;
        AppContext.get().settings.height = 10;
        AppContext.get().settings.steps = 50;
        AppContext.get().settings.speed = 50;

        // Zum Einstellungs-Bildschirm wechseln
        ChangeScene(actionEvent, StaticVarsHolder.SettingsScreen);
    }

    @FXML
    public void LoadAntButtonClick(ActionEvent actionEvent) {
        try {
            // Speicherstand laden
            SettingsState loadedState = AppContext.get().saveService.load("default");
            AppContext.get().settings = loadedState;

            // Direkt zur Simulation flitzen
            ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
        } catch (IOException e) {
            System.err.println("Kein Speicherstand gefunden: " + e.getMessage());
        }
    }

    @FXML
    public void DeleteAntButtonClick(ActionEvent actionEvent) {
        try {
            // Speicherstand löschen
            AppContext.get().saveService.delete("default");
            System.out.println("Speicherstand erfolgreich gelöscht.");
        } catch (IOException e) {
            System.err.println("Löschen fehlgeschlagen: " + e.getMessage());
        }
    }
}