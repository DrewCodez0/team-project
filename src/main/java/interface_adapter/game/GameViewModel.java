package interface_adapter.game;

import data_access.SettingsStore;
import interface_adapter.ViewModel;
import interface_adapter.options.OptionsState;

/**
 * The View Model for the Game View.
 */
public class GameViewModel extends ViewModel<GameState> {
    public GameViewModel() {
        super("game");
        OptionsState s = SettingsStore.get();

        GameState game = new GameState();
        game.setLength(s.getLength());
        game.setMaxGuesses(s.getMaxGuesses());
        game.setLanguage(s.getLanguage());
        game.setTheme(s.getTheme());
        setState(game);
    }
}
