package at.ac.hcw.langtonsantproject.Persistence;
import at.ac.hcw.langtonsantproject.Misc.AntOrientation;
/**
 * Captures the current state of a running simulation for persistence.
 * This class allows users to resume the ant's progress exactly where they left off.
 */
public class SimulationState {
    public SettingsState settings;

    public boolean [][] grid;

    public int antX;
    public int antY;

    public AntOrientation orientation;

    public int stepsRemaining;
}
