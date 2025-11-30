package use_case.stats;

import java.io.IOException;

import entity.Stats;
import entity.Theme;

public interface StatsDataAccessInterface {
    /**
     * Returns the user's stats.
     * @return the Stats representing the user's stats
     */
    Stats getStats();

    /**
     * Saves the user's stats.
     * @param stats the Stats representing the stats to save
     */
    void saveStats(Stats stats);

    /**
     * Returns the default theme.
     * @return the default theme
     */
    Theme getDefaultTheme();

    /**
     * Copies the stats data file to a specified path.
     * @param destinationPath The full path (including filename) of the destination file.
     * @throws IOException if the file copy fails.
     */
    void exportStats(String destinationPath) throws IOException;

    /**
     * Imports stats from a CSV file.
     * @param filePath The path to the file to import from.
     * @return The Stats object parsed from the file.
     * @throws IOException if the file cannot be read or has invalid format.
     */
    Stats importStats(String filePath) throws IOException;
}
