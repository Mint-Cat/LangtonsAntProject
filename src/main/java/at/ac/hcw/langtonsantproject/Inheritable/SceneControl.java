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
     * Wechselt die Szene basierend auf einem ActionEvent (z.B. Button-Klick)
     */
    public void ChangeScene(ActionEvent event, String fxmlPath) {
        Node node = (Node) event.getSource();
        loadScene(node, fxmlPath);
    }

    /**
     * Wechselt die Szene basierend auf einer Node (z.B. MenuButton oder Label)
     */
    public void ChangeScene(Node node, String fxmlPath) {
        loadScene(node, fxmlPath);
    }

    /**
     * Zentrale Methode zum Laden und Anzeigen einer neuen FXML-Datei
     */
    private void loadScene(Node node, String fxmlPath) {
        try {
            // 1. URL der FXML prüfen
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("FXML file not found at: " + fxmlPath);
            }

            // 2. FXML neu laden (erzeugt ein frisches Objekt für den Scene-Graph)
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            // 3. Das aktuelle Fenster (Stage) finden
            Stage stage = (Stage) node.getScene().getWindow();

            // 4. Eine BRANDNEUE Scene erstellen (800x600)
            // Das löst den "already set as root" Fehler endgültig
            Scene newScene = new Scene(root, 800, 600);

            // 5. Fenster aktualisieren
            stage.setScene(newScene);
            stage.setResizable(false); // Design bleibt stabil bei 800x600
            stage.centerOnScreen();    // Sorgt für eine saubere Anzeige
            stage.show();

        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load scene " + fxmlPath);
            e.printStackTrace();
        }
    }
}