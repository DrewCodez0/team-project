package use_case.stats;

import entity.Theme;
import interface_adapter.stats.StatsState;

public interface StatsDataAccessInterface {
    /**
     * Returns the user's stats.
     * @return the StatsState representing the user's stats
     */
    StatsState getStats();

    /**
     * Saves the user's stats.
     * @param stats the StatsState representing the stats to save
     */
    void saveStats(StatsState stats);

    /**
     * Returns the default theme.
     * @return the default theme
     */
    Theme getDefaultTheme();
}
