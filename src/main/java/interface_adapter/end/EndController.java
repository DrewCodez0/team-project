package interface_adapter.end;

import use_case.end.EndInputBoundary;
import use_case.end.EndInputData;

public class EndController {
    private final EndInputBoundary endInteractor;

    public EndController(EndInputBoundary endInputBoundary) {
        this.endInteractor = endInputBoundary;
    }

    public void execute(String word, boolean won, int guessesUsed, int maxGuesses) {
        final EndInputData endInputData = new EndInputData(word, won, guessesUsed, maxGuesses);
        endInteractor.execute(endInputData);
    }

    public void switchToStartView() {
        endInteractor.prepareStartView();
    }

    public void replay(String word, boolean won, int guessesUsed, int maxGuesses) {
        final EndInputData inputData = new EndInputData(word, won, guessesUsed, maxGuesses);
        endInteractor.prepareNewGame(inputData);
    }
}
