package interface_adapter.game;

import use_case.game.GameInputBoundary;
import java.awt.event.ActionEvent;

public class GameController {
    public static final String ENTER = "ENTER";
    public static final String LETTER = "LETTER";
    public static final String BACKSPACE = "BACK_SPACE";
    private final GameInputBoundary gameInteractor;

    public GameController(GameInputBoundary gameInputBoundary) {
        this.gameInteractor = gameInputBoundary;
    }

    public void execute(GameState gameInputData, ActionEvent e) {
        if (e.getSource() == GameController.LETTER) {
            gameInteractor.executeLetter(gameInputData, e.getActionCommand().charAt(0));
        } else if (e.getSource() == GameController.ENTER) {
            executeSubmit(gameInputData);
        } else if (e.getSource() == GameController.BACKSPACE) {
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
