package use_case.game;

import interface_adapter.game.GameState;

public class GameInteractor implements GameInputBoundary {

    public GameInteractor(GameDataAccessInterface gameDataAccess, GameOutputBoundary gameOutputBoundary) {

    }

    @Override
    public void execute(GameState gameInputData) {}
}
