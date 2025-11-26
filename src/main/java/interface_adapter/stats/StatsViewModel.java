package interface_adapter.stats;

import entity.Theme;
import interface_adapter.ViewModel;

/**
 * The View Model for the Stats View.
 */
public class StatsViewModel extends ViewModel<Theme> {
    public StatsViewModel(Theme theme) {
        super("stats");
        setState(theme);
    }
}
