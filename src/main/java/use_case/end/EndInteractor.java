package use_case.end;

public class EndInteractor implements EndInputBoundary {
    private final EndDataAccessInterface endDataAccess;
    private final EndOutputBoundary endPresenter;

    public EndInteractor(EndDataAccessInterface endDataAccess, EndOutputBoundary endOutputBoundary) {
        this.endDataAccess = endDataAccess;
        this.endPresenter = endOutputBoundary;
    }

    @Override
    public void execute(EndInputData endInputData) {
        final EndGameRecord record = new EndGameRecord(
                endInputData.getWord(),
                endInputData.isWon(),
                endInputData.getGuessesUsed()
        );

        endDataAccess.saveGameRecord(record);

        final EndOutputData outputData = new EndOutputData(
                endInputData.getWord(),
                endInputData.isWon(),
                endInputData.getGuessesUsed(),
                endInputData.getMaxGuesses()
        );
        if (endInputData.isWon()) {
            endPresenter.prepareSuccessView(outputData);
        }
        else {
            endPresenter.prepareFailView(outputData);
        }
    }

    @Override
    public void prepareStartView() {
        endPresenter.prepareStartView();
    }

    @Override
    public void prepareNewGame(EndInputData endInputData) {
        endPresenter.prepareGameView();
    }
}



