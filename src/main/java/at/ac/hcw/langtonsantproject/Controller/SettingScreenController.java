package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingScreenController extends SceneControl implements Initializable {

    @FXML public Slider widthSlider, heightSlider, stepsSlider, speedSlider, startXSlider, startYSlider;
    @FXML public Label widthValueLabel, heightValueLabel, stepsValueLabel, speedValueLabel, startXValueLabel, startYValueLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Design für dieses Fenster aktivieren
        Platform.runLater(() -> {
            if (widthSlider.getScene() != null) {
                String css = Objects.requireNonNull(getClass().getResource("/at/ac/hcw/langtonsantproject/style.css")).toExternalForm();
                widthSlider.getScene().getStylesheets().add(css);
            }
        });


        // Zeigt die Zahl neben dem Slider an
        widthValueLabel.textProperty().bind(Bindings.format("%.0f", widthSlider.valueProperty()));
        heightValueLabel.textProperty().bind(Bindings.format("%.0f", heightSlider.valueProperty()));
        stepsValueLabel.textProperty().bind(Bindings.format("%.0f", stepsSlider.valueProperty()));
        speedValueLabel.textProperty().bind(Bindings.format("%.0f", speedSlider.valueProperty()));
        startXValueLabel.textProperty().bind(Bindings.format("%.0f", startXSlider.valueProperty()));
        startYValueLabel.textProperty().bind(Bindings.format("%.0f", startYSlider.valueProperty()));

        // Wenn die Breite sich ändert, springt Start X & Y automatisch in die Mitte
        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            startXSlider.setMax(newVal.doubleValue());
            startXSlider.setValue(newVal.doubleValue() / 2);
        });

        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            startYSlider.setMax(newVal.doubleValue());
            startYSlider.setValue(newVal.doubleValue() / 2);
        });

        if (AppContext.get().settings != null && !AppContext.get().isFirstStart) {
            widthSlider.setValue(AppContext.get().settings.width);
            heightSlider.setValue(AppContext.get().settings.height);
            stepsSlider.setValue(AppContext.get().settings.steps);
            speedSlider.setValue(AppContext.get().settings.speed);
            startXSlider.setValue(AppContext.get().settings.antStartPointX);
            startYSlider.setValue(AppContext.get().settings.antStartPointY);
        } else {
            setDefaultValues();
        }
    }

    private void setDefaultValues() {
        widthSlider.setValue(55);
        heightSlider.setValue(55);
        stepsSlider.setValue(500);
        speedSlider.setValue(100);
        startXSlider.setValue(28);
        startYSlider.setValue(28);
    }

    @FXML
    public void applySettingsClick(ActionEvent actionEvent) {
        // Siumaltion starten Button gedrückt
        AppContext.get().settings.width = (int) widthSlider.getValue();
        AppContext.get().settings.height = (int) heightSlider.getValue();
        AppContext.get().settings.steps = (int) stepsSlider.getValue();
        AppContext.get().settings.speed = (int) speedSlider.getValue();
        AppContext.get().settings.antStartPointX = (int) startXSlider.getValue();
        AppContext.get().settings.antStartPointY = (int) startYSlider.getValue();
        AppContext.get().setFirstStart(false);
        ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
    }

    @FXML
    public void backToMenuClick(ActionEvent actionEvent) {
        ChangeScene(actionEvent, StaticVarsHolder.StartScreen);
    }
}