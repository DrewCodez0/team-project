package interface_adapter.options;

import interface_adapter.ViewModel;

/**
 * The View Model for the Options View.
 */
public class OptionsViewModel extends ViewModel<OptionsState> {
    public static final String STATE = "optionsstate";
    public static final String THEME = "theme";

    public OptionsViewModel() {
        super("options");
        setState(new OptionsState());
    }

    @Override
    public void firePropertyChange() {
        firePropertyChange(STATE);
    }
}
