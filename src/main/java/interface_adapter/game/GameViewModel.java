package interface_adapter.game;

import interface_adapter.ViewModel;

/**
 * The View Model for the Game View.
 */
public class GameViewModel extends ViewModel<GameState> {
    public static final String STATE = "state";
    public static final String SHAKE = "shake";
    public static final String NEW_GAME = "new";

    public GameViewModel() {
        super("game");
        setState(new GameState());
    }
}
