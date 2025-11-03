package interface_adapter.game;

import use_case.game.GameInputBoundary;

public class GameController {
    private final GameInputBoundary gameInputBoundary;

    public GameController(GameInputBoundary gameInputBoundary) {
        this.gameInputBoundary = gameInputBoundary;
    }

    public void execute(GameState gameInputData) {
        this.gameInputBoundary.execute(gameInputData);
        // this should take in the character input and gamestate and turn it into the next gamestate
    }
}
