package at.ac.hcw.langtonsantproject.Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import at.ac.hcw.langtonsantproject.Persistence.SimulationState;


 //Persistenz-Schicht: Speichern/Laden/Löschen von Settings als JSON.

public class SaveGameService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path dir = Paths.get(System.getProperty("user.home"), ".langtons-ant");

    private Path file(String slot) {
        return dir.resolve(slot + ".json");
    }

    public boolean exists(String slot) {
        return Files.exists(file(slot));
    }

    public void save(String slot, SettingsState state) throws IOException {
        Files.createDirectories(dir);

        Path tmp = dir.resolve(slot + ".tmp");
        Path target = file(slot);

        try (Writer w = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            gson.toJson(state, w);
        }

        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public SettingsState load(String slot) throws IOException {
        Path target = file(slot);

        try (Reader r = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
            return gson.fromJson(r, SettingsState.class);
        }
    }

    public void delete(String slot) throws IOException {
        Files.deleteIfExists(file(slot));

        }
        // Sim. Save state löschen
        public void deleteSimulation(String slot) throws IOException {
        Files.deleteIfExists(simFile(slot));
        }


        private Path simFile(String slot) {
        return dir.resolve(slot + "_sim.json");
        }

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

        public SimulationState loadSimulation(String slot) throws IOException {
        Path target = simFile(slot);
        try (Reader r = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
            return gson.fromJson(r, SimulationState.class);
        }
        }
    }
