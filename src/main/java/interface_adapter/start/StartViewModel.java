package interface_adapter.start;

import entity.DarkTheme;
import entity.Theme;
import interface_adapter.ViewModel;

/**
 * The View Model for the Start View.
 */
public class StartViewModel extends ViewModel<Theme> {
    public StartViewModel() {
        super("start");
        setState(new DarkTheme());
    }
}
