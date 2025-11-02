package interface_adapter.stats;

import interface_adapter.ViewModel;

/**
 * The View Model for the Stats View.
 */
public class StatsViewModel extends ViewModel<StatsState> {
    public StatsViewModel() {
        super("stats");
        setState(new StatsState());
    }
}
