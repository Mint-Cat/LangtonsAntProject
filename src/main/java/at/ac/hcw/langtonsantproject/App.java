package at.ac.hcw.langtonsantproject;

import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SaveGameService;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // OPTIONAL: nur zum Testen (kannst du später wieder löschen)
        if (Boolean.getBoolean("devMode")) {
            testSaveLoad2();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(StaticVarsHolder.StartScreen));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);

        stage.setTitle("Langton's Ant Simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void testSaveLoad() {
        try {
            SaveGameService service = new SaveGameService();

            SettingsState s = new SettingsState();
            s.width = 10;
            s.height = 20;
            s.steps = 100;
            s.speed = 3;

            service.save("default", s);

            SettingsState loaded = service.load("default");
            System.out.println("Loaded: " + loaded.width + " " + loaded.height + " " + loaded.steps + " " + loaded.speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testSaveLoad2() {
        try {
            SaveGameService service = AppContext.get().saveService;
            SettingsState s = AppContext.get().settings;

            s.width = 10;
            s.height = 20;
            s.steps = 100;
            s.speed = 3;

            service.save("default", s);

            SettingsState loaded = service.load("default");
            System.out.println("Loaded: " + loaded.width + " " + loaded.height + " " + loaded.steps + " " + loaded.speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}