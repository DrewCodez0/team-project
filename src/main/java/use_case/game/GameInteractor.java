package use_case.game;

import interface_adapter.game.GameState;

public class GameInteractor implements GameInputBoundary {
    private final GameDataAccessInterface gameDataAccess;
    private final GameOutputBoundary gamePresenter;

    public GameInteractor(GameDataAccessInterface gameDataAccess, GameOutputBoundary gameOutputBoundary) {
        this.gameDataAccess = gameDataAccess;
        this.gamePresenter = gameOutputBoundary;
    }

    @Override
    public void execute(GameState gameInputData) {
//        System.out.println(gameDataAccess.getRandomWord(5, "en"));
    }

    @Override
    public void prepareStartView() {
        gamePresenter.prepareStartView();
    }
    @Override
    public void prepareEndView(GameState gameState) {
        gamePresenter.prepareEndView(gameState);
    }
}
