package at.ac.hcw.langtonsantproject.Inheritable;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneControl {
    /**
     * Base class for handling scene transitions within the application.
     * All controllers inherit from this class to access navigation methods.
     */
    public void ChangeScene(ActionEvent event, String fxmlPath) {
        Node node = (Node) event.getSource();
        loadScene(node, fxmlPath);
    }
    /**
     * Changes the scene based on an ActionEvent (e.g., from a standard Button).
     * This is an example of method overloading.
     */
    public void ChangeScene(Node node, String fxmlPath) {
        loadScene(node, fxmlPath);
    }

    /**
     * Changes the scene using a specific Node as an anchor (e.g., for MenuItems).
     */
    private void loadScene(Node node, String fxmlPath) {
        try {

            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("FXML file not found at: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Stage stage = (Stage) node.getScene().getWindow();

            Scene newScene = new Scene(root, 800, 600);

            stage.setScene(newScene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load scene " + fxmlPath);
            e.printStackTrace();
        }
    }
}