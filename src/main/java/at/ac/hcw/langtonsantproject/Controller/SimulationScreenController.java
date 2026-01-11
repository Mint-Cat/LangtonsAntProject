package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Misc.AntOrientation;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SimulationScreenController extends SceneControl implements Initializable {

    public Label simulationScreen;
    public MenuButton settingButtonInSimulationScreen;
    @FXML
    private GridPane gridPane;
    @FXML
    private StackPane gridHolder;
    private DoubleBinding cellSizeBinding;
    // Ganz oben bei deinen anderen @FXML Variablen
    private SettingsState currentSettings;

    /// Runtime Vars (Here for now, can be moved somewhere else) - runtime means, only relevant during game process
    /// We initalise this each time we load the simulation Scene
    boolean[][] antGrid; //False -> untouched, default false
    private Rectangle[][] cellRects;  //UI references
    private StackPane[][] cellPanes; // UI cell containers (for PNG, etc.)
    private static final double CELL_SIZE = 50;
    private int stepsRemaining;

    public int antXLocation;
    public int antYLocation;
    public AntOrientation currentAntOrientation = AntOrientation.up;


    private Timeline simLoop;
    private ImageView antView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsState settings = AppContext.get().settings;
        if (settings == null) {
            return;
        }

        runTimeInitialise(settings);

        // Timeline Stuff
        simLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> MoveAnt()));
        simLoop.setCycleCount(Timeline.INDEFINITE);

        startSimulation();
    }

    public void runTimeInitialise(SettingsState settings) {
        // HIER: Die Settings für den Restart speichern
        this.currentSettings = settings;

        int height = (int) settings.height;
        int width = (int) settings.width;


        this.stepsRemaining = (int) settings.steps;

        antGrid = new boolean[height][width];
        cellRects = new Rectangle[height][width];
        cellPanes = new StackPane[height][width];

        antXLocation = settings.antStartPointX;
        antYLocation = settings.antStartPointY;

        buildGridUI(width, height); // Erst das Gitter bauen
        redrawAll();
        spawnAntImage();
    }

    private void MoveAnt() {

        if (stepsRemaining <= 0) {
            simLoop.stop();
            return;
        }
        boolean isBlack = antGrid[antYLocation][antXLocation];

        // 2. Turn (White/False -> Right, Black/True -> Left)
        currentAntOrientation = rotate(isBlack ? -1 : 1);
        updateAntRotation();

        // 3. Flip the color of the square we are standing on
        toggleCell(antYLocation, antXLocation);

        // 4. Move forward in the NEW direction
        int dx = 0;
        int dy = 0;
        switch (currentAntOrientation) {
            case up -> dy = -1;
            case down -> dy = 1;
            case left -> dx = -1;
            case right -> dx = 1;
        }

        int nextX = antXLocation + dx;
        int nextY = antYLocation + dy;

        // 5. Bounds Check
        if (nextX < 0 || nextX >= antGrid[0].length || nextY < 0 || nextY >= antGrid.length) {
            System.out.println("Out of bounds!");
            stopSimulation();
            return;
        }
        // 6. Update Position
        antXLocation = nextX;
        antYLocation = nextY;
        moveAntImageTo(antXLocation, antYLocation);

        stepsRemaining--;
        simulationScreen.setText("Steps Remaining: " + stepsRemaining);
    }

    private AntOrientation rotate(int direction) {
        AntOrientation[] vals = AntOrientation.values();
        // Adding vals.length before modulo handles negative results (turning left)
        int nextIndex = (currentAntOrientation.ordinal() + direction + vals.length) % vals.length;
        return vals[nextIndex];
    }

    private void TeleportAntToNewPos(boolean moveRight) {
        int dx = 0;
        int dy = 0;

        // edge check: stop sim if out of bounds
        if (antXLocation < 0 || antXLocation >= antGrid[0].length
                || antYLocation < 0 || antYLocation >= antGrid.length) {

            System.out.println("[STOP ] out of bounds!");
            if (simLoop != null) simLoop.stop();
            return;
        }

        if (moveRight) {
            switch (currentAntOrientation) {
                case up -> dx = -1;
                case right -> dy = 1;
                case down -> dx = 1;
                case left -> dy = -1;
            }
        } else {
            switch (currentAntOrientation) {
                case up -> dx = 1;
                case right -> dy = -1;
                case down -> dx = -1;
                case left -> dy = 1;
            }
        }
        antXLocation += dx;
        antYLocation += dy;

        //edge check: stop sim if out of bounds
        if (antXLocation < 0 || antXLocation >= antGrid[0].length || antYLocation < 0 || antYLocation >= antGrid.length) {
            if (simLoop != null) simLoop.stop();
            return;
        }
    }

    private void moveAntImageTo(int x, int y) {
        if (antView == null) return;

        // Remove from previous cell (if attached)
        if (antView.getParent() instanceof Pane p) {
            p.getChildren().remove(antView);
        }

        StackPane newCell = cellPanes[y][x];

        // Rebind fit size to the new cell so it keeps scaling
        antView.fitWidthProperty().unbind();
        antView.fitHeightProperty().unbind();
        antView.fitWidthProperty().bind(newCell.widthProperty().multiply(0.9));
        antView.fitHeightProperty().bind(newCell.heightProperty().multiply(0.9));

        newCell.getChildren().add(antView);
    }

    private void spawnAntImage() {
        Image img = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/hcw/langtonsantproject/pictures/ANT.png")),
                10, 10, true, true
        );
        antView = new ImageView(img);
        antView.setPreserveRatio(true);


        StackPane startCell = cellPanes[antYLocation][antXLocation];
        antView.fitWidthProperty().bind(startCell.widthProperty().multiply(0.9));
        antView.fitHeightProperty().bind(startCell.heightProperty().multiply(0.9));

        startCell.getChildren().add(antView);

        updateAntRotation();

    }

    private void updateAntRotation() {
        double angle = switch (currentAntOrientation) {
            case up -> 0;
            case right -> 90;
            case down -> 180;
            case left -> 270;
        };
        antView.setRotate(angle);
    }

    //region Grid Builder
    private void buildGridUI(int width, int height) {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        double gap = 1;
        gridPane.setHgap(gap);
        gridPane.setVgap(gap);

        // Cell size = min(availableWidth/cols, availableHeight/rows)
        // subtract gaps so it doesn't overflow
        cellSizeBinding = Bindings.createDoubleBinding(() -> {
            double availableW = gridHolder.getWidth() - (width - 1) * gridPane.getHgap();
            double availableH = gridHolder.getHeight() - (height - 1) * gridPane.getVgap();
            if (availableW <= 0 || availableH <= 0) return 1.0;

            double sizeW = availableW / width;
            double sizeH = availableH / height;

            // Optional: clamp so it doesn't become ridiculous
            double cell = Math.min(sizeW, sizeH);
            cell = Math.max(2, Math.min(cell, 40)); // min 2px, max 40px (tweak/remove)
            return cell;
        }, gridHolder.widthProperty(), gridHolder.heightProperty());

        // Columns
        for (int col = 0; col < width; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.prefWidthProperty().bind(cellSizeBinding);
            cc.minWidthProperty().bind(cellSizeBinding);
            cc.maxWidthProperty().bind(cellSizeBinding);
            gridPane.getColumnConstraints().add(cc);
        }

        // Rows
        for (int row = 0; row < height; row++) {
            RowConstraints rc = new RowConstraints();
            rc.prefHeightProperty().bind(cellSizeBinding);
            rc.minHeightProperty().bind(cellSizeBinding);
            rc.maxHeightProperty().bind(cellSizeBinding);
            gridPane.getRowConstraints().add(rc);
        }

        // Create Cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                StackPane cell = new StackPane();
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                Rectangle r = new Rectangle();
                r.setStroke(Color.gray(0.7));
                r.setFill(Color.WHITE);

                // Scale rectangle to cell size
                r.widthProperty().bind(cellSizeBinding);
                r.heightProperty().bind(cellSizeBinding);

                cell.getChildren().add(r);

                cellPanes[row][col] = cell;
                cellRects[row][col] = r;

                gridPane.add(cell, col, row);
            }
        }
    }

    public void toggleCell(int row, int col) {
        antGrid[row][col] = !antGrid[row][col];
        updateCell(row, col);
    }

    private void updateCell(int row, int col) {
        cellRects[row][col].setFill(antGrid[row][col] ? Color.BLACK : Color.WHITE);
    }

    private void redrawAll() {
        for (int row = 0; row < antGrid.length; row++) {
            for (int col = 0; col < antGrid[row].length; col++) {
                updateCell(row, col);
            }
        }
    }

    //region SimulationControll
    public void startSimulation() {
        simLoop.play();
    }

    public void pauseSimulation() {
        simLoop.pause();
    }

    public void stopSimulation() {
        simLoop.stop();
    }

    @FXML
    public void pauseClicked(ActionEvent actionEvent) {
        pauseSimulation();
    }

    @FXML
    public void saveClicked(ActionEvent actionEvent) {
        try {
            // Holt die aktuellen Settings aus dem AppContext
            SettingsState stateToSave = AppContext.get().settings;

            // Speichert sie in die Datei "default.json"
            AppContext.get().saveService.save("default", stateToSave);

            System.out.println("Simulation erfolgreich gespeichert!");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void exitAndSaveClicked(ActionEvent actionEvent) {
        saveClicked(actionEvent);

        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }

    @FXML
    public void exitClicked(ActionEvent actionEvent) {
        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }


    @FXML
    public void settingsClickedInSimulation(ActionEvent actionEvent) {
        stopSimulation();
        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }

    @FXML
    public void restartClicked(ActionEvent actionEvent) {
        stopSimulation();
        try {

            String fxmlPath = "/at/ac/hcw/langtonsantproject/simulation-screen.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            SimulationScreenController controller = loader.getController();
            if (this.currentSettings != null) {
                controller.runTimeInitialise(this.currentSettings);
            }

            Stage stage = (Stage) gridPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}