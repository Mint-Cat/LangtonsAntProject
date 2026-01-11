package at.ac.hcw.langtonsantproject.Inheritable;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneControl {

    // Version für den Start-Button (ActionEvent)
    public void ChangeScene(ActionEvent event, String fxmlPath) {
        Node node = (Node) event.getSource();
        loadScene(node, fxmlPath);
    }

    // Version für den MenuButton (Node)
    public void ChangeScene(Node node, String fxmlPath) {
        loadScene(node, fxmlPath);
    }

    private void loadScene(Node node, String fxmlPath) {
        try {
            Stage stage = (Stage) node.getScene().getWindow();
            // Hier nutzen wir den Pfad ausgehend vom Root-Verzeichnis
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/hcw/langtonsantproject/" + fxmlPath));

            Parent root = loader.load();
            if (root == null) throw new IOException("FXML Root ist null");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Fehler: FXML Datei " + fxmlPath + " nicht gefunden!");
            e.printStackTrace();
        }
    }
}