package at.ac.hcw.langtonsantproject.Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Persistence layer for saving, loading, and deleting simulation data as JSON.
 */

public class SaveGameService {
    // Configures GSON for human-readable output
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // Hidden storage directory in the user's home folder
    private final Path dir = Paths.get(System.getProperty("user.home"), ".langtons-ant");

    /**
     * Returns the path for general settings files.
     */
    private Path file(String slot) {
        return dir.resolve(slot + ".json");
    }

    /**
     * Deletes a specific settings slot.
     */
    public void delete(String slot) throws IOException {
        Files.deleteIfExists(file(slot));

    }

    /**
     * Deletes a specific simulation progress slot.
     */
    public void deleteSimulation(String slot) throws IOException {
        Files.deleteIfExists(simFile(slot));
    }

    /**
     * Returns the path for simulation state files.
     */
    private Path simFile(String slot) {
        return dir.resolve(slot + "_sim.json");
    }

    /**
     * Saves the simulation state using a temporary file and atomic move
     * to ensure data integrity during the save process.
     */
    public void saveSimulation(String slot, SimulationState state) throws IOException {
        Files.createDirectories(dir);
        Path tmp = dir.resolve(slot + "_sim.tmp");
        Path target = simFile(slot);

        try (Writer w = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            gson.toJson(state, w);
        }

        Files.move(tmp, target,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * Loads a simulation state from a JSON file.
     */
    public SimulationState loadSimulation(String slot) throws IOException {
        Path target = simFile(slot);
        try (Reader r = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
            return gson.fromJson(r, SimulationState.class);
        }
    }
}
