package interface_adapter.end;

import interface_adapter.ViewModel;
import interface_adapter.game.GameState;

/**
 * The View Model for the End View.
 */
public class EndViewModel extends ViewModel<GameState> {
    public EndViewModel() {
        super("end");
        setState(new GameState());
    }
}
