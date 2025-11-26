package use_case.stats;

import entity.Stats;

public class StatsOutputData {
    private final Stats stats;

    public StatsOutputData(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}
