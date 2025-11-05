package interface_adapter.game;

import use_case.game.GameInputBoundary;

import javax.swing.*;

public class GameController {
    private final GameInputBoundary gameInteractor;

    public GameController(GameInputBoundary gameInputBoundary) {
        this.gameInteractor = gameInputBoundary;
    }

    public void execute(GameState gameInputData) { //, KeyStroke key
        this.gameInteractor.execute(gameInputData);
        // TODO this should take in the character input and gamestate and turn it into the next gamestate
    }

    public void execute(GameState gameInputData, char character) {

    }

    public void switchToStartView() {
        gameInteractor.prepareStartView();
    }

    public void switchToEndView(GameState gameState) {
        gameInteractor.prepareEndView(gameState);
    }
}
