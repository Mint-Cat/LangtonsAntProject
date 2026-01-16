package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Misc.AntOrientation;
import at.ac.hcw.langtonsantproject.Misc.StaticVarsHolder;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SimulationScreenController extends SceneControl implements Initializable {

    @FXML public Label simulationScreen;
    @FXML public MenuButton settingButtonInSimulationScreen;
    @FXML private GridPane gridPane;
    @FXML private StackPane gridHolder;

    private SettingsState currentSettings;
    private double fixedCellSize;

    private boolean[][] antGrid;
    private Rectangle[][] cellRects;
    private StackPane[][] cellPanes;
    private int stepsRemaining;
    private int antXLocation;
    private int antYLocation;
    private AntOrientation currentAntOrientation = AntOrientation.up;

    private Timeline simLoop;
    private ImageView antView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsState settings = AppContext.get().settings;
        if (settings == null) return;

        String css = getClass().getResource("/at/ac/hcw/langtonsantproject/style.css").toExternalForm();
        Platform.runLater(() -> {
            if (gridPane.getScene() != null) {
                gridPane.getScene().getStylesheets().add(css);
            }
        });

        runTimeInitialise(settings);

        double speedValue = settings.speed <= 0 ? 50 : settings.speed;
        simLoop = new Timeline(new KeyFrame(Duration.millis(1000.0 / speedValue), e -> MoveAnt()));
        simLoop.setCycleCount(Timeline.INDEFINITE);

        startSimulation();
    }

    public void runTimeInitialise(SettingsState settings) {
        this.currentSettings = settings;
        int height = (int) settings.height;
        int width = (int) settings.width;
        this.stepsRemaining = (int) settings.steps;

        antGrid = new boolean[height][width];
        cellRects = new Rectangle[height][width];
        cellPanes = new StackPane[height][width];

        antXLocation = settings.antStartPointX;
        antYLocation = settings.antStartPointY;

        // BERECHNUNG: Gittergröße einmal festlegen
        double availW = 740.0 / width;
        double availH = 480.0 / height;
        this.fixedCellSize = Math.min(availW, availH);
        if (this.fixedCellSize > 40) this.fixedCellSize = 40;

        buildGridUI(width, height);
        redrawAll();
        spawnAntImage();
        simulationScreen.setText("Steps Remaining: " + stepsRemaining);
    }

    private void buildGridUI(int width, int height) {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < width; i++) gridPane.getColumnConstraints().add(new ColumnConstraints(fixedCellSize));
        for (int i = 0; i < height; i++) gridPane.getRowConstraints().add(new RowConstraints(fixedCellSize));

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                StackPane cell = new StackPane();
                Rectangle r = new Rectangle(fixedCellSize, fixedCellSize);
                r.setStroke(Color.LIGHTGRAY);
                r.setFill(Color.WHITE);
                cell.getChildren().add(r);
                cellPanes[row][col] = cell;
                cellRects[row][col] = r;
                gridPane.add(cell, col, row);
            }
        }
    }

    private void MoveAnt() {
        if (stepsRemaining <= 0) { stopSimulation(); return; }

        boolean isBlack = antGrid[antYLocation][antXLocation];
        currentAntOrientation = rotate(isBlack ? -1 : 1);
        updateAntRotation();
        toggleCell(antYLocation, antXLocation);

        int dx = 0, dy = 0;
        switch (currentAntOrientation) {
            case up -> dy = -1;
            case down -> dy = 1;
            case left -> dx = -1;
            case right -> dx = 1;
        }

        int nextX = antXLocation + dx;
        int nextY = antYLocation + dy;

        if (nextX < 0 || nextX >= antGrid[0].length || nextY < 0 || nextY >= antGrid.length) {
            stopSimulation();
            return;
        }

        antXLocation = nextX;
        antYLocation = nextY;
        moveAntImageTo(antXLocation, antYLocation);
        stepsRemaining--;
        simulationScreen.setText("Steps Remaining: " + stepsRemaining);
    }

    private void moveAntImageTo(int x, int y) {
        if (antView == null) return;
        if (antView.getParent() instanceof Pane p) {
            p.getChildren().remove(antView);
        }
        cellPanes[y][x].getChildren().add(antView);
    }

    private void spawnAntImage() {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/hcw/langtonsantproject/Pictures/ANT.png")));
        antView = new ImageView(img);
        antView.setFitWidth(fixedCellSize * 0.8);
        antView.setFitHeight(fixedCellSize * 0.8);
        antView.setPreserveRatio(true);
        cellPanes[antYLocation][antXLocation].getChildren().add(antView);
        updateAntRotation();
    }

    private void updateCell(int row, int col) {
        // ZURÜCK ZU SCHWARZ-WEISS
        cellRects[row][col].setFill(antGrid[row][col] ? Color.BLACK : Color.WHITE);
    }

    private AntOrientation rotate(int direction) {
        AntOrientation[] vals = AntOrientation.values();
        return vals[(currentAntOrientation.ordinal() + direction + vals.length) % vals.length];
    }

    private void updateAntRotation() {
        antView.setRotate(switch (currentAntOrientation) {
            case up -> 0; case right -> 90; case down -> 180; case left -> 270;
        });
    }

    public void toggleCell(int row, int col) {
        antGrid[row][col] = !antGrid[row][col];
        updateCell(row, col);
    }

    private void redrawAll() {
        for (int r = 0; r < antGrid.length; r++)
            for (int c = 0; c < antGrid[r].length; c++) updateCell(r, c);
    }

    public void startSimulation() { simLoop.play(); }
    public void pauseSimulation() { simLoop.pause(); }
    public void stopSimulation() { simLoop.stop(); }

    @FXML public void pauseClicked(ActionEvent e) {
        if (simLoop.getStatus() == Timeline.Status.RUNNING) pauseSimulation(); else startSimulation();
    }

    @FXML public void saveClicked(ActionEvent e) {
        try { AppContext.get().saveService.save("default", AppContext.get().settings); } catch (IOException ex) { ex.printStackTrace(); }
    }

    @FXML public void exitAndSaveClicked(ActionEvent actionEvent) {
        saveClicked(actionEvent);
        stopSimulation();
        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }

    @FXML public void exitClicked(ActionEvent e) {
        stopSimulation();
        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }

    @FXML public void settingsClickedInSimulation(ActionEvent e) {
        stopSimulation();
        ChangeScene(gridPane, StaticVarsHolder.StartScreen);
    }

    @FXML public void restartClicked(ActionEvent e) {
        stopSimulation();
        // RESTART-FIX: Nutzt gridPane statt ActionEvent
        ChangeScene(gridPane, StaticVarsHolder.SimulationScreen);
    }
}