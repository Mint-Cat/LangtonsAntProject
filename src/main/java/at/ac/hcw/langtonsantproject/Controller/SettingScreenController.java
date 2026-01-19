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

/**
 * Controller for the settings screen.
 * Handles user input via sliders and updates the global application context.
 */
public class SettingScreenController extends SceneControl implements Initializable {

    @FXML
    public Slider widthSlider, heightSlider, stepsSlider, speedSlider, startXSlider, startYSlider;
    @FXML
    public Label widthValueLabel, heightValueLabel, stepsValueLabel, speedValueLabel, startXValueLabel, startYValueLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Apply global stylesheet once the scene is active
        Platform.runLater(() -> {
            if (widthSlider.getScene() != null) {
                String css = Objects.requireNonNull(getClass().getResource("/at/ac/hcw/langtonsantproject/style.css")).toExternalForm();
                widthSlider.getScene().getStylesheets().add(css);
            }
        });

        // Bind slider values to labels for real-time visual feedback
        widthValueLabel.textProperty().bind(Bindings.format("%.0f", widthSlider.valueProperty()));
        heightValueLabel.textProperty().bind(Bindings.format("%.0f", heightSlider.valueProperty()));
        stepsValueLabel.textProperty().bind(Bindings.format("%.0f", stepsSlider.valueProperty()));
        speedValueLabel.textProperty().bind(Bindings.format("%.0f", speedSlider.valueProperty()));
        startXValueLabel.textProperty().bind(Bindings.format("%.0f", startXSlider.valueProperty()));
        startYValueLabel.textProperty().bind(Bindings.format("%.0f", startYSlider.valueProperty()));

        // Add listeners to keep the ant's start position within grid bounds
        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            startXSlider.setMax(newVal.doubleValue());
            startXSlider.setValue(newVal.doubleValue() / 2);
        });

        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            startYSlider.setMax(newVal.doubleValue());
            startYSlider.setValue(newVal.doubleValue() / 2);
        });
        // Load existing settings or apply defaults
        if (AppContext.get().settings != null && !AppContext.get().useDefaultSettingValues) {
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

    /**
     * Sets sliders to predefined default values from StaticVarsHolder.
     */
    private void setDefaultValues() {
        widthSlider.setValue(StaticVarsHolder.defaultWidthSetting);
        heightSlider.setValue(StaticVarsHolder.defaultHeightSetting);
        stepsSlider.setValue(StaticVarsHolder.defaultStepsSetting);
        speedSlider.setValue(StaticVarsHolder.defaultSpeedSetting);
        startXSlider.setValue(StaticVarsHolder.defaultStartXSliderSetting);
        startYSlider.setValue(StaticVarsHolder.defaultStartYSliderSetting);
    }

    /**
     * Saves settings to AppContext and transitions to the simulation.
     */
    @FXML
    public void applySettingsClick(ActionEvent actionEvent) {
        AppContext.get().settings.width = (int) widthSlider.getValue();
        AppContext.get().settings.height = (int) heightSlider.getValue();
        AppContext.get().settings.steps = (int) stepsSlider.getValue();
        AppContext.get().settings.speed = (int) speedSlider.getValue();
        AppContext.get().settings.antStartPointX = (int) startXSlider.getValue();
        AppContext.get().settings.antStartPointY = (int) startYSlider.getValue();
        AppContext.get().setUseDefaultSettingValues(false);
        ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
    }

    /**
     * Navigates back to the main menu.
     */
    @FXML
    public void backToMenuClick(ActionEvent actionEvent) {
        ChangeScene(actionEvent, StaticVarsHolder.StartScreen);
    }
}