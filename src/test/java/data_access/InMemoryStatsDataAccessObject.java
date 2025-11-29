package data_access;

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
}

