package interface_adapter.stats;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

public class StatsViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Statistics";
    public static final String GAMES_PLAYED_LABEL = "Games Played";
    public static final String WIN_PERCENTAGE_LABEL = "Win Percentage";
    public static final String CURRENT_STREAK_LABEL = "Current Streak";
    public static final String MAX_STREAK_LABEL = "Max Streak";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private StatsState state = new StatsState();

    public StatsViewModel() {
        super("stats");
    }

    public void setState(StatsState state) {
        this.state = state;
    }

    /**
     * Notifies all listeners that the state property has changed.
     */
    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }
    
    /**
     * Adds a PropertyChangeListener to the listener list.
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public StatsState getState() {
        return state;
    }
}
