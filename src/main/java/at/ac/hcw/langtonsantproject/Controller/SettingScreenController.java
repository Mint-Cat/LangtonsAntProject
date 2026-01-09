package at.ac.hcw.langtonsantproject.Controller;

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
        ChangeScene(actionEvent, "simulation-screen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Slider Start Settings
        //TODO: Change default solder values
        Slider widthSlider = new Slider();
        widthSlider.setMin(1);
        widthSlider.setMax(20);
        widthSlider.setBlockIncrement(1);

        Slider heightSlider = new Slider();
        heightSlider.setMin(1);
        heightSlider.setMax(20);
        heightSlider.setBlockIncrement(1);

        Label widthLabel = new Label("Width: " + widthSlider.getValue());
        Label heightLabel = new Label("Height: " + heightSlider.getValue());
        sizeVBox.getChildren().addAll(widthSlider, heightSlider, widthLabel, heightLabel);


        Slider stepsSlider = new Slider();
        stepsSlider.setMin(1);
        stepsSlider.setMax(100);
        stepsSlider.setBlockIncrement(1);

        stepsVBox.getChildren().addAll(stepsSlider);

        Slider SpeedSlider = new Slider();
        SpeedSlider.setMin(1);
        SpeedSlider.setMax(100);
        SpeedSlider.setBlockIncrement(1);

        speedVBOX.getChildren().addAll(SpeedSlider);
    }
}
