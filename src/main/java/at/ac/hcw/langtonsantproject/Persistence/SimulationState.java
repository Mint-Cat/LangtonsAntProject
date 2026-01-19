package at.ac.hcw.langtonsantproject.Persistence;
import at.ac.hcw.langtonsantproject.Misc.AntOrientation;
/**
 * Represents the live state of an ongoing simulation.
 * Used for saving and resuming the ant's progress.
 */
public class SimulationState {
    public SettingsState settings;

    public boolean [][] grid;

    public int antX;
    public int antY;

    public AntOrientation orientation;

    public int stepsRemaining;
}
