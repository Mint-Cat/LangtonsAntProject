package at.ac.hcw.langtonsantproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartScreenController {
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

 public void NewAntButtonClick(ActionEvent actionEvent) {
   }
   public void LoadAntButtonClick(ActionEvent actionEvent) {
   }
   public void DeleteAntButtonClick(ActionEvent actionEvent) {
   }
}
