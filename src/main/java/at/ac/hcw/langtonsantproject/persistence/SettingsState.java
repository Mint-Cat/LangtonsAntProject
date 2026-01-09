package at.ac.hcw.langtonsantproject.persistence;

/**
 * DTO (Data Transfer Object) für das Speichern/Laden der Einstellungen.
 * Enthält nur Daten – keine Logik.
 */
public class SettingsState {

    /** Versionierung, falls ihr später das Format erweitert. */
    public int fileVersion = 1;

    /** Grid-/Canvas-Größe oder Settings-Werte aus dem Settings Screen. */
    public double width;
    public double height;

    /** Maximale Steps/Iterationsanzahl. */
    public int steps;

    /** Geschwindigkeit (z.B. Delay, Steps pro Sekunde, Slider-Wert). */
    public double speed;
}
