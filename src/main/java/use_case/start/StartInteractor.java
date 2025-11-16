package use_case.start;

//import data_access.Language;
import data_access.WordNotFoundException;
import entity.AbstractWord;
import entity.Theme;
import entity.Word;
import interface_adapter.game.GameState;
import interface_adapter.options.OptionsState;
import use_case.game.GameDataAccessInterface;

public class StartInteractor implements StartInputBoundary {
    private final StartDataAccessInterface startDataAccess;
    private final GameDataAccessInterface gameDataAccess;
    private final StartOutputBoundary startPresenter;

    public StartInteractor(StartDataAccessInterface startDataAccess,
                           GameDataAccessInterface gameDataAccess,
                           StartOutputBoundary startOutputBoundary) {
        this.startDataAccess = startDataAccess;
        this.gameDataAccess = gameDataAccess;
        this.startPresenter = startOutputBoundary;
    }

    @Override
    public void prepareStartView() {
        Theme theme = startDataAccess.getDefaultTheme();
        startPresenter.prepareStartView(theme);
    }

    @Override
    public void prepareGameView(OptionsState optionsState) {
            try {
                String word = gameDataAccess.getRandomWord(optionsState.getLength(), optionsState.getLanguage());
                System.out.println(word);
                AbstractWord theword = new Word(word); // TODO replace this with a wordfactory
                GameState gameState = new GameState(theword, optionsState.getMaxGuesses());
                startPresenter.prepareGameView(gameState);
            } catch (WordNotFoundException e) {
                System.out.println(e.getMessage());
            }
//        int length = optionsState.getLength();
//        Language language = optionsState.getLanguage();
//        startPresenter.prepareGameView(optionsState);
    }

    @Override
    public void prepareOptionsView() {
        startPresenter.prepareOptionsView();
    }

    @Override
    public void prepareStatsView() {
        startPresenter.prepareStatsView();
    }
}
