package interface_adapter.start;

import entity.Theme;
import interface_adapter.ViewModel;

/**
 * The View Model for the Start View.
 */
public class StartViewModel extends ViewModel<Theme> {
    public static final String STATE = "startstate";

    public StartViewModel(Theme theme) {
        super("start");
        setState(theme);
    }

    @Override
    public void firePropertyChange() {
        firePropertyChange(STATE);
    }
}
