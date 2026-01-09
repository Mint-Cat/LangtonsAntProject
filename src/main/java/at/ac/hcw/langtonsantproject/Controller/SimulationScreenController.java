package at.ac.hcw.langtonsantproject.Controller;

import at.ac.hcw.langtonsantproject.AppContext;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import at.ac.hcw.langtonsantproject.Inheritable.SceneControl;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.GridPane;

public class SimulationScreenController extends SceneControl implements Initializable {
    public Label simulationScreen;
    public MenuButton settingButtonInSimulationScreen;
    @FXML
    private GridPane gridPane;
    private static final int CELL_SIZE = 12;

    /// Runtime Vars (Here for now, can be moved somewhere else) - runtime means, only relevant during game process
    /// We initalise this each time we load the simulation Scene
    boolean[][] antGrid; //False -> untouched, default false
    private Rectangle[][] cellRects;  //UI references

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: Add with & height from memory or settings
        SettingsState settings = AppContext.get().settings;
        if (settings == null){
            runTimeInitialise(10,10);
            return;
        }

        runTimeInitialise((int)settings.width,(int)settings.height);
    }
    public void runTimeInitialise(int width, int height) {
        antGrid = new boolean[height][width];
        cellRects = new Rectangle[height][width];

        buildGridUI(width, height);
        redrawAll();
    }

    private void buildGridUI(int width, int height) {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(5)); // padding is already on the StackPane in FXML

        //Columns = percent width
        for (int col = 0; col < width; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / width);
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            gridPane.getColumnConstraints().add(cc);
        }

        //Rows = percent height
        for (int row = 0; row < height; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / height);
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            gridPane.getRowConstraints().add(rc);
        }

        //Crate Cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                StackPane cell = new StackPane();
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(cell, Priority.ALWAYS);
                GridPane.setVgrow(cell, Priority.ALWAYS);

                Rectangle r = new Rectangle();
                r.setStroke(Color.gray(0.7));
                r.setFill(Color.WHITE);

                r.widthProperty().bind(cell.widthProperty());
                r.heightProperty().bind(cell.heightProperty());

                cell.getChildren().add(r);

                cellRects[row][col] = r;
                gridPane.add(cell, col, row);

                final int rr = row;
                final int cc = col;
                cell.setOnMouseClicked(e -> {
                    antGrid[rr][cc] = !antGrid[rr][cc];
                    cellRects[rr][cc].setFill(antGrid[rr][cc] ? Color.BLACK : Color.WHITE);
                });
            }
        }
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

    // Call this after each simulation step (update only changed cells)
    public void setCell(int row, int col, boolean value) {
        antGrid[row][col] = value;
        updateCell(row, col);
    }

    //region Button Funcs
    public void pauseClicked(ActionEvent actionEvent) {
        //TODO: Pause Simulation
    }
    public void saveClicked(ActionEvent actionEvent) {
        //TODO: Save Simulation, all relevant Vars need to be saved
    }
    //endregion

}
