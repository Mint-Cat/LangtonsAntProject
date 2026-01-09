package at.ac.hcw.langtonsantproject.Inheritable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


public abstract class SceneControl {

    //Changes Scene, with scene name as input
    @FXML
    public void ChangeScene(ActionEvent actionEvent, String fileName) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(("/at/ac/hcw/langtonsantproject/" + fileName))));

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
