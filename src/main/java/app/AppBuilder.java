package app;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.*;
import entity.Theme;
import interface_adapter.ViewManagerModel;
import interface_adapter.end.EndController;
import interface_adapter.end.EndPresenter;
import interface_adapter.end.EndViewModel;
import interface_adapter.game.GameController;
import interface_adapter.game.GamePresenter;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsController;
import interface_adapter.options.OptionsPresenter;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.start.StartController;
import interface_adapter.start.StartPresenter;
import interface_adapter.start.StartViewModel;
import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsPresenter;
import interface_adapter.stats.StatsViewModel;
import use_case.end.EndInputBoundary;
import use_case.end.EndInteractor;
import use_case.end.EndOutputBoundary;
import use_case.game.GameInputBoundary;
import use_case.game.GameInteractor;
import use_case.game.GameOutputBoundary;
import use_case.options.OptionsInputBoundary;
import use_case.options.OptionsInteractor;
import use_case.options.OptionsOutputBoundary;
import use_case.start.StartInputBoundary;
import use_case.start.StartInteractor;
import use_case.start.StartOutputBoundary;
import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInteractor;
import use_case.stats.StatsOutputBoundary;
import view.EndView;
import view.GameView;
import view.OptionsView;
import view.StartView;
import view.StatsView;
import view.ViewManager;

public class AppBuilder {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(486, 713);
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private StartView startView;
    private StartViewModel startViewModel;
    private GameView gameView;
    private GameViewModel gameViewModel;
    private OptionsView optionsView;
    private OptionsViewModel optionsViewModel;
    private EndView endView;
    private EndViewModel endViewModel;
    private StatsView statsView;
    private StatsViewModel statsViewModel;

    private final FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("stats.csv");
    private final WordDataAccessObject wordDataAccessObject = new WordDataAccessObject(
            new APIWordGenerator2(), new APIWordChecker2());

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addStartView() {
        if (optionsViewModel == null) {
            this.addOptionsView();
        }
        if (startViewModel == null) {
            final Theme theme = fileDataAccessObject.getDefaultTheme();
            startViewModel = new StartViewModel(theme);
            startView = new StartView(startViewModel, optionsViewModel);
            cardPanel.add(startView, startView.getViewName());
        }
        return this;
    }

    public AppBuilder addEndView() {
        if (optionsViewModel == null) {
            this.addStartView();
        }
        endViewModel = new EndViewModel();
        endView = new EndView(endViewModel, optionsViewModel);
        cardPanel.add(endView, endView.getViewName());
        return this;
    }

    public AppBuilder addGameView() {
        if (optionsViewModel == null) {
            this.addOptionsView();
        }
        gameViewModel = new GameViewModel();
        gameView = new GameView(gameViewModel, optionsViewModel);
        cardPanel.add(gameView, gameView.getViewName());
        return this;
    }

    public AppBuilder addOptionsView() {
        if (optionsViewModel == null) {
            optionsViewModel = new OptionsViewModel();
            optionsView = new OptionsView(optionsViewModel);
            cardPanel.add(optionsView, optionsView.getViewName());
        }
        return this;
    }

    public AppBuilder addStatsView() {
        if (optionsViewModel == null) {
            this.addOptionsView();
        }
        statsViewModel = new StatsViewModel();
        statsView = new StatsView(statsViewModel, optionsViewModel);
        cardPanel.add(statsView, statsView.getViewName());
        return this;
    }

    public AppBuilder addStartUseCase() {
        final StatsOutputBoundary statsOutputBoundary = new StatsPresenter(viewManagerModel,
                statsViewModel, startViewModel);
        final StatsInputBoundary statsInteractor = new StatsInteractor(fileDataAccessObject, statsOutputBoundary);
        final StatsController statsController = new StatsController(statsInteractor);
        statsView.setStatsController(statsController);

        final StartOutputBoundary startOutputBoundary = new StartPresenter(viewManagerModel,
                startViewModel, gameViewModel, optionsViewModel, statsViewModel);
        final StartInputBoundary startInteractor = new StartInteractor(
                fileDataAccessObject, startOutputBoundary);

        final StartController startController = new StartController(startInteractor, statsController);
        startView.setStartController(startController);
        return this;
    }

    public AppBuilder addEndUseCase() {
        final EndOutputBoundary endOutputBoundary = new EndPresenter(viewManagerModel,
                endViewModel, gameViewModel, startViewModel);
        final EndInputBoundary endInteractor = new EndInteractor(fileDataAccessObject, endOutputBoundary);

        final EndController endController = new EndController(endInteractor);
        endView.setEndController(endController);
        return this;
    }

    public AppBuilder addGameUseCase() {
        final GameOutputBoundary gameOutputBoundary = new GamePresenter(viewManagerModel,
                gameViewModel, startViewModel, endViewModel);

        final EndOutputBoundary endOutputBoundary = new EndPresenter(viewManagerModel,
                endViewModel, gameViewModel, startViewModel);
        final EndInputBoundary endInteractor = new EndInteractor(fileDataAccessObject, endOutputBoundary);

        final GameInputBoundary gameInteractor = new GameInteractor(wordDataAccessObject, gameOutputBoundary,
                endInteractor, optionsViewModel);

        final GameController gameController = new GameController(gameInteractor);
        gameView.setGameController(gameController);
        return this;
    }

    public AppBuilder addOptionsUseCase() {
        final OptionsOutputBoundary optionsOutputBoundary = new OptionsPresenter(viewManagerModel,
                optionsViewModel, startViewModel);
        final OptionsInputBoundary optionsInteractor = new OptionsInteractor(fileDataAccessObject, optionsOutputBoundary);

        final OptionsController optionsController = new OptionsController(optionsInteractor);
        optionsView.setOptionsController(optionsController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Wordle");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setMinimumSize(DEFAULT_WINDOW_SIZE);
        application.setSize(DEFAULT_WINDOW_SIZE);
        // This does not work
        application.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());

        application.add(cardPanel);

        viewManagerModel.setState(startView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
