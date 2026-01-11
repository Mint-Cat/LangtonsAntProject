package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import at.ac.hcw.langtonsantproject.AppContext;

public class StartScreenController extends SceneControl implements Initializable {
    @FXML
    public Label startscreen;
    public Button NewAntButton;
    public Button LoadAntButton;
    public Button DeleteAntButton;
    public Label NameLabel;
    public GridPane gridPane;
    public VBox parent;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NameLabel.setStyle("-fx-font-size: 60");
        // Responsive columns
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col1.setHgrow(Priority.ALWAYS);
        col1.setMinWidth(150);
        col1.setMaxWidth(400);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setHgrow(Priority.ALWAYS);
        col2.setMinWidth(200);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        col3.setHgrow(Priority.ALWAYS);
        col3.setMinWidth(150);
        col3.setMaxWidth(400);

        // Responsive Rows
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(15);
        row1.setVgrow(Priority.ALWAYS);
        row1.setMinHeight(10);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        row2.setVgrow(Priority.ALWAYS);
        row2.setMinHeight(5);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(5);
        row3.setVgrow(Priority.ALWAYS);
        row3.setMinHeight(5);

        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(5);
        row4.setVgrow(Priority.ALWAYS);
        row4.setMinHeight(5);

        //Add VBoxes to responsive Grid
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getChildren().clear();
        gridPane.add(NameLabel, 1, 0);
        gridPane.add(NewAntButton, 1, 1);
        gridPane.add(LoadAntButton, 1, 2);
        gridPane.add(DeleteAntButton, 1, 3);

        NameLabel.setMaxWidth(Double.MAX_VALUE);
        NameLabel.setAlignment(Pos.CENTER);
        NewAntButton.setMaxWidth(Double.MAX_VALUE);
        NewAntButton.setAlignment(Pos.CENTER);
        LoadAntButton.setMaxWidth(Double.MAX_VALUE);
        LoadAntButton.setAlignment(Pos.CENTER);
        DeleteAntButton.setMaxWidth(Double.MAX_VALUE);
        DeleteAntButton.setAlignment(Pos.CENTER);

    }
    @FXML
    public void NewAntButtonClick(ActionEvent actionEvent) {
        AppContext.get().settings.width = 10;
        AppContext.get().settings.height = 10;
        AppContext.get().settings.steps = 50;
        AppContext.get().settings.speed = 50;

        ChangeScene(actionEvent, StaticVarsHolder.SettingsScreen);
    }
    @FXML
    public void LoadAntButtonClick(ActionEvent actionEvent) {
        try {
            // 1. Daten aus dem "default" Slot laden
            SettingsState loadedState = AppContext.get().saveService.load("default");

            // 2. Die geladenen Daten in den zentralen AppContext übertragen
            AppContext.get().settings = loadedState;

            // 3. Direkt zur Simulation wechseln
            ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
        } catch (IOException e) {
            //4. Fehler ausgeben, falls keine Datei existiert
            System.err.println("Load failed: " + e.getMessage());
        }
    }

    @FXML
    public void DeleteAntButtonClick(ActionEvent actionEvent) {
        try {
            // Löscht den Speicherstand im "default" Slot
            AppContext.get().saveService.delete("default");
            System.out.println("Save deleted successfully.");
        } catch (IOException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }
@FXML
public void SaveAntButtonClick(ActionEvent actionEvent) {
    saveCurrentState();
}

@FXML
public void exitAndSaveClicked(ActionEvent actionEvent) {
    // 1. Speichern
    saveCurrentState();
    // 2. Zurück zum Startbildschirm (Wichtig: actionEvent nutzen!)
    ChangeScene(actionEvent, StaticVarsHolder.StartScreen);
}

// Diese interne Methode erledigt die eigentliche Arbeit
private void saveCurrentState() {
    try {
        // Wir holen die aktuellen Einstellungen aus dem AppContext
        SettingsState currentState = AppContext.get().settings;

        // Der saveService schreibt die Daten in die Datei "default.json"
        AppContext.get().saveService.save("default", currentState);

        System.out.println("Erfolgreich gespeichert!");
    } catch (IOException e) {
        System.err.println("Fehler beim Speichern: " + e.getMessage());
    }
}}