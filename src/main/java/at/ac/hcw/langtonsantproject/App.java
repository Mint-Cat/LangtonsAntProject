package at.ac.hcw.langtonsantproject;

import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;



public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Pfad prüfen
            URL fxmlLocation = App.class.getResource(StaticVarsHolder.StartScreen);
            if (fxmlLocation == null) {
                System.err.println("FEHLER: FXML-Datei nicht gefunden! Pfad in StaticVarsHolder prüfen.");
                return;
            }

            // 2. Loader erstellen und laden
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = fxmlLoader.load();

            // 3. Scene mit fester Größe erstellen
            Scene scene = new Scene(root, 800, 600);

            stage.setTitle("Langton's Ant Simulation");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Programm erfolgreich gestartet!");

        } catch (Exception e) {
            System.err.println("Absturz beim Starten:");
            e.printStackTrace(); // Das zeigt uns im 'Run'-Fenster genau, was schief geht
        }
    }

    public static void main(String[] args) {
        launch();
    }
}