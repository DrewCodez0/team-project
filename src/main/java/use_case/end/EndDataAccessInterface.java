package use_case.end;

import entity.Stats;

public interface EndDataAccessInterface {
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
}
