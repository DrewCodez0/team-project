package data_access;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import entity.Stats;
import entity.Theme;
import interface_adapter.options.OptionsState;
import use_case.end.EndDataAccessInterface;
import use_case.options.OptionsDataAccessInterface;
import use_case.start.StartDataAccessInterface;
import use_case.stats.StatsDataAccessInterface;

public class FileDataAccessObject implements OptionsDataAccessInterface,
        StatsDataAccessInterface, StartDataAccessInterface, EndDataAccessInterface {

    private final File statsFile;

    public FileDataAccessObject(String statsCsvPath) {
        this.statsFile = new File(statsCsvPath);
    }

    @Override
    public Stats getStats() {
        if (!statsFile.exists()) {
            return new Stats();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(statsFile))) {
            final String header = reader.readLine();
            if (header == null) {
                return new Stats();
            }
            final String line = reader.readLine();
            if (line == null) {
                return new Stats();
            }
            final String[] values = line.split(",");
            final int gamesPlayed = Integer.parseInt(values[0]);
            final int wins = Integer.parseInt(values[1]);
            final int currentStreak = Integer.parseInt(values[2]);
            final int maxStreak = Integer.parseInt(values[3]);

            return new Stats(gamesPlayed, wins, currentStreak, maxStreak);
        }
        catch (IOException | NumberFormatException exception) {
            return new Stats();
        }
    }

    @Override
    public void saveStats(Stats stats) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(statsFile))) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak");
            writer.newLine();
            writer.write(String.format("%d,%d,%d,%d",
                    stats.getGamesPlayed(),
                    stats.getWins(),
                    stats.getCurrentStreak(),
                    stats.getMaxStreak()));
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void exportStats(String destinationPath) throws IOException {
        if (!statsFile.exists()) {
            throw new IOException("Stats file not found.");
        }
        Files.copy(statsFile.toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public OptionsState getOptions() {
        return new OptionsState();
    }

    @Override
    public void saveOptions(OptionsState options) {

    }

    @Override
    public Theme getDefaultTheme() {
        return getOptions().getTheme();
    }
}
