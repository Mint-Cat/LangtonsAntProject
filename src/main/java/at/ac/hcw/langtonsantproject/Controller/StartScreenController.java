package at.ac.hcw.langtonsantproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

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
 @FXML
 public void NewAntButtonClick(ActionEvent actionEvent) {
     xzy(actionEvent,"setting-screen.fxml");
   }
   public void LoadAntButtonClick(ActionEvent actionEvent) {

   }

   public void DeleteAntButtonClick(ActionEvent actionEvent) {

   }
   @FXML
    public void xzy(ActionEvent actionEvent, String fileName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(("/at/ac/hcw/langtonsantproject/" + fileName)));

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
