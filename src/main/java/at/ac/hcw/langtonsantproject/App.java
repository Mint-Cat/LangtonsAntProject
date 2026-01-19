package at.ac.hcw.langtonsantproject;

import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


/**
 * Main entry point for the JavaFX application.
 * Responsibility: Initializes the primary stage and loads the initial scene.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {

            URL fxmlLocation = App.class.getResource(StaticVarsHolder.StartScreen);
            if (fxmlLocation == null) {
                System.err.println("ERROR: FXML file not found! Please check the path in StaticVarsHolder.");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = fxmlLoader.load();

            //  Create the scene with fixed dimensions
            Scene scene = new Scene(root, 800, 600);

            stage.setTitle("Langton's Ant Simulation");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Application started successfully!");

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to launch application:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}