package use_case.end;

import interface_adapter.game.GameState;

public class EndInteractor implements EndInputBoundary {
    public EndInteractor(EndDataAccessInterface endDataAccess, EndOutputBoundary endOutputBoundary) {}

    public void execute(GameState endInputData) {}
}
