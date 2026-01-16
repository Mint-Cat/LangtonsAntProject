package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingScreenController extends SceneControl implements Initializable {

    @FXML public Slider widthSlider, heightSlider, stepsSlider, speedSlider;
    @FXML public Label widthValueLabel, heightValueLabel, stepsValueLabel, speedValueLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. CSS DATEI LADEN (Das fehlte wahrscheinlich!)
        String css = getClass().getResource("/at/ac/hcw/langtonsantproject/style.css").toExternalForm();

        // Wir warten kurz, bis das Fenster bereit ist, und wenden dann das Rot an
        javafx.application.Platform.runLater(() -> {
            if (widthSlider.getScene() != null) {
                widthSlider.getScene().getStylesheets().clear();
                widthSlider.getScene().getStylesheets().add(css);
            }
        });

        // Deine restlichen Bindings für die Zahlen...
        widthValueLabel.textProperty().bind(Bindings.format("%.0f", widthSlider.valueProperty()));
        heightValueLabel.textProperty().bind(Bindings.format("%.0f", heightSlider.valueProperty()));
        stepsValueLabel.textProperty().bind(Bindings.format("%.0f", stepsSlider.valueProperty()));
        speedValueLabel.textProperty().bind(Bindings.format("%.0f", speedSlider.valueProperty()));
    }


    @FXML
    public void applySettingsClick(ActionEvent actionEvent) {
        // Werte direkt von den Slidern holen (als int casten)
        AppContext.get().settings.width = (int) widthSlider.getValue();
        AppContext.get().settings.height = (int) heightSlider.getValue();
        AppContext.get().settings.steps = (int) stepsSlider.getValue();
        AppContext.get().settings.speed = (int) speedSlider.getValue();

        // Startpunkt in die Mitte setzen (Standard-Verhalten)
        // Startpunkt in die Mitte setzen (Standard-Verhalten)
        AppContext.get().settings.antStartPointX = (int) widthSlider.getValue() / 2;
        AppContext.get().settings.antStartPointY = (int) heightSlider.getValue() / 2;

        ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
    }

    @FXML
    public void backToMenuClick(ActionEvent actionEvent) {
        ChangeScene(actionEvent, StaticVarsHolder.StartScreen);
    }
}