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

    public Slider makeSlider( int defaultValue, double min, double max){
        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setBlockIncrement(1);
        slide.setValue(defaultValue);
        return slide;
    }

    public void responsive(){
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

        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(20);
        row4.setVgrow(Priority.ALWAYS);
        row4.setMinHeight(10);

        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(20);
        row5.setVgrow(Priority.ALWAYS);
        row5.setMinHeight(10);

        gridPane.getRowConstraints().addAll(row1, row2);

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
        StartButton.setMaxWidth(Double.MAX_VALUE);
        StartButton.setAlignment(Pos.CENTER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Slider Start Settings
        //TODO: Make input field for x & y coordinates

        responsive();

        // default values
        int widthDefault = 10;
        int heighDefault = 10;
        int stepDefault = 50;
        int speedDefault = 50;
        int posXDefault = 5;
        int posYDefault = 5;

        // Width
        Slider widthSlider = makeSlider(widthDefault, 1, 20);
        Label widthLabel = new Label("Width: " + widthDefault);
        currentWithSliderValue = widthDefault;
        widthSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)widthSlider.getValue();
                    widthLabel.setText("Width: " + newValue);
                    currentWithSliderValue = widthSlider.getValue();
                }
        );

        // Height
        Slider heightSlider = makeSlider(heighDefault, 1,20);
        Label heightLabel = new Label("Height: " +heighDefault);
        currentHeighSliderValue= heighDefault;
        heightSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)heightSlider.getValue();
                    heightLabel.setText("Height: " + newValue);
                    currentHeighSliderValue = heightSlider.getValue();
                }
        );

        sizeVBox.getChildren().addAll(widthSlider, heightSlider, widthLabel, heightLabel);

        // Steps
        Slider stepsSlider = makeSlider(stepDefault, 1, 100);
        Label stepsLabel = new Label("Steps: 50");
        stepsSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)stepsSlider.getValue();
                    stepsLabel.setText("Steps: " + newValue);
                    currentStepsValue = stepsSlider.getValue();
                }
        );
        stepsVBox.getChildren().addAll(stepsSlider,  stepsLabel);

        // Speed
        Slider speedSlider = makeSlider(speedDefault, 1, 100);
        Label speedLabel = new Label("Speed: 50");
        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)speedSlider.getValue();
                    speedLabel.setText("Height: " + newValue);
                    currentSpeedValue = speedSlider.getValue();
                }
        );
        speedVBOX.getChildren().addAll(speedSlider, speedLabel);
    }
}