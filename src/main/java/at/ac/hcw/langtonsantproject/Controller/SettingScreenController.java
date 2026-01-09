package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
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
    public VBox sizeVBox;
    public VBox stepsVBox;
    public VBox speedVBOX;
    public VBox startingPointVBox;


    public void StartButtonClick(ActionEvent actionEvent) {
        //TODO: Start Game: Load all vars, initialise ant array with set settings
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

        widthSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)widthSlider.getValue();
                    widthLabel.setText("Width: " + newValue);
                }
        );
        heightSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    newValue = (int)heightSlider.getValue();
                    heightLabel.setText("Height: " + newValue);
                }
        );
        sizeVBox.getChildren().addAll(widthSlider, heightSlider, widthLabel, heightLabel);


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
                }
        );

        stepsVBox.getChildren().addAll(stepsSlider,  stepsLabel);



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
                }
        );

        speedVBOX.getChildren().addAll(speedSlider, speedLabel);
    }
}
