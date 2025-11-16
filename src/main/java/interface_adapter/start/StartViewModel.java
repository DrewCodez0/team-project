package interface_adapter.start;

import entity.Theme;
import interface_adapter.ViewModel;

/**
 * The View Model for the Start View.
 */
public class StartViewModel extends ViewModel<Theme> {
    public StartViewModel(Theme theme) {
        super("start");
        setState(theme);
    }
}
