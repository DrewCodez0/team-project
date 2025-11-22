package use_case.stats;

import interface_adapter.stats.StatsState;

public class StatsOutputData {
    private final StatsState statsState;
    private final boolean hasStats;

    public StatsOutputData(StatsState statsState, boolean hasStats) {
        this.statsState = statsState;
        this.hasStats = hasStats;
    }

    public StatsState getStatsState() {
        return statsState;
    }

    public boolean hasStats() {
        return hasStats;
    }
}
