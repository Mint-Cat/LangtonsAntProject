package at.ac.hcw.langtonsantproject.Persistence;


 public class SettingsState {

    public boolean LoadedIn = false; //IDK, this var makes it easy to know where we came from, loaded or via settings

    public int fileVersion = 1;

    /** Grid-/Canvas-Größe oder Settings-Werte aus dem Settings Screen. */
    public double width =0;
    public double height =0;

    /** Maximale Steps/Iterationsanzahl. */
    public int steps;

    /** (z.B. Delay, Steps pro Sekunde, Slider-Wert). */
    public double speed;

    public int antStartPointX = 5;
    public int antStartPointY=5;
}
