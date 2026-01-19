package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
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
            // 1. Simulationszustand laden
            var simState = AppContext.get().saveService.loadSimulation("default");

            //2. Settings in AppContext -> initialize in SimulationScreen
            AppContext.get().settings = simState.settings;

            //3. SimulationScreen manuell laden -> Controller
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource(StaticVarsHolder.SimulationScreen)
            );
            javafx.scene.Parent root = loader.load();

            SimulationScreenController controller = loader.getController();

            //4. Simulationszustand im Controller benutzen
            controller.loadSimulationState(simState);

            //5. Scene setzen
            javafx.stage.Stage stage = (javafx.stage.Stage) gridPane.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("No save state found: " + e.getMessage());
            WarningLabel.setText("No save state found!");
        }
    }

    @FXML
    public void DeleteAntButtonClick(ActionEvent actionEvent) {
        try {
            //Settings-Save löschen
            AppContext.get().saveService.delete("default");
            //Sim.-Save löschen
            AppContext.get().saveService.deleteSimulation("default");

            System.out.println("Ant successfully deleted.");
            WarningLabel.setText("Ant successfully deleted!");
            AppContext.get().setFirstStart(true);
        }catch (IOException e) {
            System.err.println("failed delete: " + e.getMessage());
            WarningLabel.setText("failed delete!");
        }
    }
}