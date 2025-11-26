package interface_adapter.stats;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StatsViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Statistics";
    public static final String GAMES_PLAYED_LABEL = "Games Played";
    public static final String WIN_PERCENTAGE_LABEL = "Win Percentage";
    public static final String CURRENT_STREAK_LABEL = "Current Streak";
    public static final String MAX_STREAK_LABEL = "Max Streak";

    private StatsState state = new StatsState();

    public StatsViewModel() {
        super("stats");
    }

    public void setState(StatsState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public StatsState getState() {
        return state;
    }
}
