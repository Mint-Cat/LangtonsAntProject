package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingScreenController extends SceneControl implements Initializable {
    @FXML public Label settingScreen;
    @FXML public Button StartButton;
    @FXML public VBox sizeVBox;
    @FXML public VBox stepsVBox;
    @FXML public VBox speedVBOX;
    @FXML public VBox startingPointVBox;
    @FXML public GridPane gridPane;
    @FXML public Label settingsLabel;
    @FXML public Label xPosLabel;
    @FXML public Label yPosLabel;
    @FXML public HBox hBoxCoo;

    // Diese Variablen speichern die aktuellen Werte der UI-Elemente
    public double currentWithSliderValue;
    public double currentHeighSliderValue;
    public double currentSpeedValue;
    public double currentStepsValue;
    public double currentPosX;
    public double currentPosY;

    @FXML
    public void StartButtonClick(ActionEvent actionEvent) {
        SettingsState settings = AppContext.get().settings;
        if (settings != null) {
            settings.height = currentHeighSliderValue;
            settings.width = currentWithSliderValue;
            settings.speed = currentSpeedValue;
            settings.steps = (int) currentStepsValue;

            // Speichere die Startpositionen der Ameise
            settings.antStartPointX = (int) currentPosX;
            settings.antStartPointY = (int) currentPosY;
        }
        ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
    }

    public Slider makeSlider(int defaultValue, double min, double max) {
        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setBlockIncrement(1);
        slide.setValue(defaultValue);
        return slide;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        responsive();

        // Standardwerte definieren
        int widthDefault = 10;
        int heighDefault = 10;
        int stepDefault = 50;
        int speedDefault = 50;
        int posXDefault = 5;
        int posYDefault = 5;

        // --- WIDTH ---
        Slider widthSlider = makeSlider(widthDefault, 1, 20);
        Label widthLabel = new Label("Width: " + widthDefault);
        currentWithSliderValue = widthDefault;
        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            widthLabel.setText("Width: " + val);
            currentWithSliderValue = val;
        });

        // --- HEIGHT ---
        Slider heightSlider = makeSlider(heighDefault, 1, 20);
        Label heightLabel = new Label("Height: " + heighDefault);
        currentHeighSliderValue = heighDefault;
        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            heightLabel.setText("Height: " + val);
            currentHeighSliderValue = val;
        });
        sizeVBox.getChildren().clear();
        sizeVBox.getChildren().addAll(widthSlider, heightSlider, widthLabel, heightLabel);

        // --- STEPS ---
        Slider stepsSlider = makeSlider(stepDefault, 1, 100);
        Label stepsLabel = new Label("Steps: " + stepDefault);
        currentStepsValue = stepDefault;
        stepsSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            stepsLabel.setText("Steps: " + val);
            currentStepsValue = val;
        });
        stepsVBox.getChildren().clear();
        stepsVBox.getChildren().addAll(stepsSlider, stepsLabel);

        // --- SPEED ---
        Slider speedSlider = makeSlider(speedDefault, 1, 100);
        Label speedLabel = new Label("Speed: " + speedDefault);
        currentSpeedValue = speedDefault;
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            speedLabel.setText("Speed: " + val);
            currentSpeedValue = val;
        });
        speedVBOX.getChildren().clear();
        speedVBOX.getChildren().addAll(speedSlider, speedLabel);

        // --- STARTING POINT X & Y ---
        TextField xPosTextField = new TextField(String.valueOf(posXDefault));
        currentPosX = posXDefault;
        xPosTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                currentPosX = Integer.parseInt(newVal);
            } catch (NumberFormatException e) { currentPosX = 0; }
        });

        TextField yPosTextfield = new TextField(String.valueOf(posYDefault));
        currentPosY = posYDefault;
        yPosTextfield.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                currentPosY = Integer.parseInt(newVal);
            } catch (NumberFormatException e) { currentPosY = 0; }
        });

        hBoxCoo.getChildren().clear();
        hBoxCoo.getChildren().addAll(xPosLabel, xPosTextField, yPosLabel, yPosTextfield);
    }

    public void responsive() {

        gridPane.getChildren().clear();
        gridPane.add(settingsLabel, 1, 0);
        gridPane.add(sizeVBox, 1, 1);
        gridPane.add(stepsVBox, 1, 2);
        gridPane.add(speedVBOX, 1, 5);
        gridPane.add(startingPointVBox, 1, 5);
        gridPane.add(hBoxCoo, 1, 5);
        gridPane.add(StartButton, 1, 6);
    }
}