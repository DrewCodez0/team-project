package use_case.stats;

import interface_adapter.stats.StatsState;

/**
 * DAO interface for the Stats Use Case.
 */
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
}
