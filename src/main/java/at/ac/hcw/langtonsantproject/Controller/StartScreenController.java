package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartScreenController extends SceneControl implements Initializable {

    // Diese Variablen sind mit deiner .fxml Datei verknüpft
    @FXML public GridPane gridPane;
    @FXML public Label WarningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* WICHTIG: Wir löschen hier NICHTS mehr mit gridPane.getChildren().clear().
           Das Layout kommt jetzt sauber aus der FXML-Datei.
        */

        // Das dunkelrote Design (CSS) laden
        String css = Objects.requireNonNull(getClass().getResource("/at/ac/hcw/langtonsantproject/style.css")).toExternalForm();

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
        // Zum Einstellungs-Bildschirm wechseln
        ChangeScene(actionEvent, StaticVarsHolder.SettingsScreen);
    }

    @FXML
    public void LoadAntButtonClick(ActionEvent actionEvent) {
        try {
            // Speicherstand laden
            AppContext.get().settings = AppContext.get().saveService.load("default");

            // Direkt zur Simulation
            ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
        } catch (IOException e) {
            System.err.println("Kein Speicherstand gefunden: " + e.getMessage());
            WarningLabel.setText("Kein Speicherstand gefunden!");
        }
    }

    @FXML
    public void DeleteAntButtonClick(ActionEvent actionEvent) {
        try {
            // Speicherstand löschen
            AppContext.get().saveService.delete("default");
            System.out.println("Speicherstand erfolgreich gelöscht.");
            WarningLabel.setText("Speicherstand erfolgreich gelöscht!");
            AppContext.get().setFirstStart(true);
        } catch (IOException e) {
            System.err.println("Löschen fehlgeschlagen: " + e.getMessage());
            WarningLabel.setText("Löschen fehlgeschlagen!");
        }
    }
}