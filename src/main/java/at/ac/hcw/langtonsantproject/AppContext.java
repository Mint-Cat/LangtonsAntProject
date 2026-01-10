package at.ac.hcw.langtonsantproject;

import at.ac.hcw.langtonsantproject.Controller.SettingScreenController;
import at.ac.hcw.langtonsantproject.Persistence.SettingsState;
import at.ac.hcw.langtonsantproject.Persistence.SaveGameService;

public final class AppContext {
    /// Singeton stuff (Accessible from everywhere)
    private static AppContext instance;
    public static AppContext get() {
        if (instance == null) instance = new AppContext();
        return instance;
    }
    /// All classes here that need to be shared, so scripts can communicate with each other easily
    public SettingsState settings = new SettingsState();
    public SaveGameService saveService = new SaveGameService();

}
