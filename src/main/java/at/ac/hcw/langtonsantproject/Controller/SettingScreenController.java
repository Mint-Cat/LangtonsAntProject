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
import javafx.scene.layout.*;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingScreenController extends SceneControl implements Initializable {
   @FXML
    public Label settingScreen;
   @FXML
   public Button StartButton;
    @FXML
    public VBox vBox;
    @FXML
    public VBox sizeVBox;
    @FXML
    public VBox stepsVBox;
    @FXML
    public VBox speedVBOX;
    @FXML
    public VBox startingPointVBox;
    @FXML
    public GridPane gridPane;
    @FXML
    public Label settingsLabel;

    public double currentWithSliderValue;
    public double currentHeighSliderValue;
    public double currentSpeedValue;
    public double currentStepsValue;



    public void StartButtonClick(ActionEvent actionEvent) {
        //TODO: Load all other vars
        SettingsState settings = AppContext.get().settings;
        if (settings != null){
            settings.height = currentHeighSliderValue;
            settings.width = currentWithSliderValue;
            settings.speed = currentSpeedValue;
            settings.steps = (int)currentStepsValue;
        }

        ChangeScene(actionEvent, StaticVarsHolder.SimulationScreen);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Slider Start Settings
        //TODO: Make input field for x & y coordinates
        Slider widthSlider = new Slider();
        widthSlider.setMin(1);
        widthSlider.setMax(20);
        widthSlider.setBlockIncrement(1);

        Slider heightSlider = new Slider();
        heightSlider.setMin(1);
        heightSlider.setMax(20);
        heightSlider.setBlockIncrement(1);

        widthSlider.setValue(10);
        heightSlider.setValue(10);
        Label widthLabel = new Label("Width: 10");
        Label heightLabel = new Label("Height: 10");

        /// Width
        widthSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)widthSlider.getValue();
                    widthLabel.setText("Width: " + newValue);
                    currentWithSliderValue = widthSlider.getValue();
                }
        );
        /// Height
        heightSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)heightSlider.getValue();
                    heightLabel.setText("Height: " + newValue);
                    currentHeighSliderValue = heightSlider.getValue();
                }
        );
        sizeVBox.getChildren().addAll(widthSlider, heightSlider, widthLabel, heightLabel);

        /// Steps
        Slider stepsSlider = new Slider();
        stepsSlider.setMin(1);
        stepsSlider.setMax(100);
        stepsSlider.setBlockIncrement(1);

        stepsSlider.setValue(50);
        Label stepsLabel = new Label("Steps: 50");
        stepsSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)stepsSlider.getValue();
                    stepsLabel.setText("Steps: " + newValue);
                    currentStepsValue = stepsSlider.getValue();
                }
        );
        stepsVBox.getChildren().addAll(stepsSlider,  stepsLabel);

        /// Speed
        Slider speedSlider = new Slider();
        speedSlider.setMin(1);
        speedSlider.setMax(100);
        speedSlider.setBlockIncrement(1);
        speedSlider.setValue(50);
        Label speedLabel = new Label("Speed: 50");
        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)speedSlider.getValue();
                    speedLabel.setText("Height: " + newValue);
                    currentSpeedValue = speedSlider.getValue();
                }
        );
        speedVBOX.getChildren().addAll(speedSlider, speedLabel);

        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // Responsive columns
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        col1.setMinWidth(150);
        col1.setMaxWidth(400);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        col2.setMinWidth(200);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        col3.setHgrow(Priority.ALWAYS);
        col3.setMinWidth(150);
        col3.setMaxWidth(400);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);


        // Responsive Rows
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        row1.setVgrow(Priority.ALWAYS);
        row1.setMinHeight(10);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        row2.setVgrow(Priority.ALWAYS);
        row2.setMinHeight(10);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
        row3.setVgrow(Priority.ALWAYS);
        row3.setMinHeight(10);

        gridPane.getRowConstraints().addAll(row1, row2,row3);

        // Add all elements to responsive grid
        gridPane.getChildren().clear();
        gridPane.add(settingsLabel,1,0);
        settingsLabel.setMaxWidth(Double.MAX_VALUE);
        settingsLabel.setAlignment(Pos.CENTER);
        gridPane.add(sizeVBox, 1, 1);
        gridPane.add(stepsVBox, 1, 2);
        gridPane.add(speedVBOX, 1, 3);
        gridPane.add(startingPointVBox, 1, 4);
        gridPane.add(StartButton,1,5);
        gridPane.add(vBox, 1, 0, 1, 2);
        StartButton.setMaxWidth(Double.MAX_VALUE);
        StartButton.setAlignment(Pos.CENTER);

    }
}
