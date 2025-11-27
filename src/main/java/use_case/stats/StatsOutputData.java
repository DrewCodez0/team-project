package use_case.stats;

import entity.Stats;
import entity.Theme;

public class StatsOutputData {
    private final Stats stats;
    private final Theme theme;

    public StatsOutputData(Stats stats, Theme theme) {
        this.stats = stats;
        this.theme = theme;
    }

    public Stats getStats() {
        return stats;
    }

    public Theme getTheme() {
        return theme;
    }
}
