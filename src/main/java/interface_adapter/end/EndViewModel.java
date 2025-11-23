package interface_adapter.end;

import interface_adapter.ViewModel;

/**
 * The View Model for the End View.
 */
public class EndViewModel extends ViewModel<EndState> {
    public EndViewModel() {
        super("end");
        setState(new EndState());
    }
}
