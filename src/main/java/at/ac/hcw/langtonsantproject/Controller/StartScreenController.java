package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartScreenController extends SceneControl {
   @FXML
   public Label startscreen;
   public VBox VBox1;
   public VBox VBox2;
   public Button NewAntButton;
   public Button LoadAntButton;
   public Button DeleteAntButton;
   public Label NameLabel;


 @FXML
 private void initialize() {
  NameLabel.setStyle("-fx-font-size: 60" );
 }

 @FXML //These are func for its respective buttons
   public void NewAntButtonClick(ActionEvent actionEvent) {
     //Change scene to Settings Scene to make new ant
     ChangeScene(actionEvent, StaticVarsHolder.SettingsScreen);
   }
   public void LoadAntButtonClick(ActionEvent actionEvent) {
       //TODO: Load data on existing ant to varaible class
   }

   public void DeleteAntButtonClick(ActionEvent actionEvent) {
     //TODO: Delete all data on existing ant
   }
}
