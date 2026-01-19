package at.ac.hcw.langtonsantproject.Persistence;

/**
 * Data model for simulation settings.
 * This class is serialized to JSON for saving.
 */
 public class SettingsState {

    // Grid dimensions and simulation parameters
    public double width =0;
    public double height =0;
    public int steps;
    public double speed;
    // Initial starting position of the ant
    public int antStartPointX = 5;
    public int antStartPointY=5;
}
