package at.ac.hcw.langtonsantproject.Persistence;

/**
 * DTO (Data Transfer Object) für das Speichern/Laden der Einstellungen.
 * Enthält nur Daten – keine Logik.
 */
public class SettingsState {

    public boolean LoadedIn = false; //IDK, this var makes it easy to know where we came from, loaded or via settings
    /** Versionierung, falls ihr später das Format erweitert. */
    public int fileVersion = 1;

    /** Grid-/Canvas-Größe oder Settings-Werte aus dem Settings Screen. */
    public double width =0;
    public double height =0;

    /** Maximale Steps/Iterationsanzahl. */
    public int steps;

    /** Geschwindigkeit (z.B. Delay, Steps pro Sekunde, Slider-Wert). */
    public double speed;
}
