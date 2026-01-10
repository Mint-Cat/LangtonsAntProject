package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController extends SceneControl implements Initializable {
   @FXML
   public Label startscreen;
   public Button NewAntButton;
   public Button LoadAntButton;
   public Button DeleteAntButton;
   public Label NameLabel;
   public GridPane gridPane;
   public VBox parent;


    @FXML
 public void initialize(URL url, ResourceBundle resourceBundle) {
  
     NameLabel.setStyle("-fx-font-size: 60" );
        // Responsive columns
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col1.setHgrow(Priority.ALWAYS);
        col1.setMinWidth(150);
        col1.setMaxWidth(400);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setHgrow(Priority.ALWAYS);
        col2.setMinWidth(200);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        col3.setHgrow(Priority.ALWAYS);
        col3.setMinWidth(150);
        col3.setMaxWidth(400);

        // Responsive Rows
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(15);
        row1.setVgrow(Priority.ALWAYS);
        row1.setMinHeight(10);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        row2.setVgrow(Priority.ALWAYS);
        row2.setMinHeight(5);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(5);
        row3.setVgrow(Priority.ALWAYS);
        row3.setMinHeight(5);

        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(5);
        row4.setVgrow(Priority.ALWAYS);
        row4.setMinHeight(5);

        //Add VBoxes to responsive Grid
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getChildren().clear();
        gridPane.add(NameLabel,1,0);
        gridPane.add(NewAntButton, 1, 1);
        gridPane.add(LoadAntButton, 1, 2);
        gridPane.add(DeleteAntButton, 1, 3);

        NameLabel.setMaxWidth(Double.MAX_VALUE);
        NameLabel.setAlignment(Pos.CENTER);
        NewAntButton.setMaxWidth(Double.MAX_VALUE);
        NewAntButton.setAlignment(Pos.CENTER);
        LoadAntButton.setMaxWidth(Double.MAX_VALUE);
        LoadAntButton.setAlignment(Pos.CENTER);
        DeleteAntButton.setMaxWidth(Double.MAX_VALUE);
        DeleteAntButton.setAlignment(Pos.CENTER);

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
