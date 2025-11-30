package data_access;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import entity.LightTheme;
import entity.Stats;
import entity.Theme;
import use_case.stats.StatsDataAccessInterface;
/**
 * An in-memory implementation of StatsDataAccessInterface for testing purposes.
 * This class stores statistics data in memory rather than persisting to a file,
 * making it good for unit tests.
 */
public class InMemoryStatsDataAccessObject implements StatsDataAccessInterface {
    private Stats stats;
    private boolean hasStats;

    public InMemoryStatsDataAccessObject() {
        this.stats = new Stats();
        this.hasStats = false;
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    @Override
    public void saveStats(Stats stats) {
        this.stats = stats;
        this.hasStats = true;
    }

    @Override
    public Theme getDefaultTheme() {
        return new LightTheme();
    }

    @Override
    public void exportStats(String destinationPath) throws IOException {
        if (!hasStats) {
            throw new IOException("Stats file not found.");
        }
    }

    @Override
    public Stats importStats(String filePath) throws IOException {
        final File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            final String line = reader.readLine();

            if (line == null) {
                throw new IOException("File is empty or has only one line.");
            }

            final String[] values = line.split(",");
            if (values.length < 4) {
                throw new IOException("Invalid file format.");
            }

            final int gamesPlayed = Integer.parseInt(values[0]);
            final int wins = Integer.parseInt(values[1]);
            final int currentStreak = Integer.parseInt(values[2]);
            final int maxStreak = Integer.parseInt(values[3]);

            return new Stats(gamesPlayed, wins, currentStreak, maxStreak);
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            throw new IOException("Invalid file format: " + exception.getMessage());
        }
    }
}

