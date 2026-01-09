package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
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
 @FXML
   public void NewAntButtonClick(ActionEvent actionEvent) {
     ChangeScene(actionEvent,"setting-screen.fxml");
   }
   public void LoadAntButtonClick(ActionEvent actionEvent) {

   }

   public void DeleteAntButtonClick(ActionEvent actionEvent) {

   }
}
