package interface_adapter.game;

import java.awt.event.ActionEvent;

import use_case.game.GameInputBoundary;

public class GameController {
    public static final String ENTER = "ENTER";
    public static final String LETTER = "LETTER";
    public static final String BACKSPACE = "BACK_SPACE";
    private final GameInputBoundary gameInteractor;

    public GameController(GameInputBoundary gameInputBoundary) {
        this.gameInteractor = gameInputBoundary;
    }

    public void startNewGame() {
        gameInteractor.prepareNewGame();
    }

    public void execute(GameState gameInputData, ActionEvent exp) {
        if (exp.getSource() == GameController.LETTER) {
            gameInteractor.executeLetter(gameInputData, exp.getActionCommand().charAt(0));
        }
        else if (exp.getSource() == GameController.ENTER) {
            executeSubmit(gameInputData);
        }
        else if (exp.getSource() == GameController.BACKSPACE) {
            gameInteractor.executeBackspace(gameInputData);
        }
    }

    public void executeSubmit(GameState gameInputData) {
        gameInteractor.executeSubmit(gameInputData);
    }

    public void switchToStartView() {
        gameInteractor.prepareStartView();
    }

    public void switchToEndView(GameState gameState) {
        gameInteractor.prepareEndView(gameState);
    }
}
