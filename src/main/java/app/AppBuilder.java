package app;

import data_access.APIDataAccessObject;
import data_access.FileDataAccessObject;
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
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

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

    final FileDataAccessObject fileDataAccessObject = new FileDataAccessObject();
    final APIDataAccessObject apiDataAccessObject = new APIDataAccessObject();

    public AppBuilder() {cardPanel.setLayout(cardLayout);}

    public AppBuilder addStartView() {
        startViewModel = new StartViewModel();
        startView = new StartView(startViewModel);
        cardPanel.add(startView, startView.getViewName());
        return this;
    }

    public AppBuilder addEndView() {
        endViewModel = new EndViewModel();
        endView = new EndView(endViewModel);
        cardPanel.add(endView, endView.getViewName());
        return this;
    }

    public AppBuilder addGameView() {
        gameViewModel = new GameViewModel();
        gameView = new GameView(gameViewModel, optionsViewModel);
        cardPanel.add(gameView, gameView.getViewName());
        return this;
    }

    public AppBuilder addOptionsView() {
        optionsViewModel = new OptionsViewModel();
        optionsView = new OptionsView(optionsViewModel);
        cardPanel.add(optionsView, optionsView.getViewName());
        return this;
    }

    public AppBuilder addStatsView() {
        statsViewModel = new StatsViewModel();
        statsView = new StatsView(statsViewModel);
        cardPanel.add(statsView, statsView.getViewName());
        return this;
    }

    public AppBuilder addStartUseCase() {
        final StartOutputBoundary startOutputBoundary = new StartPresenter(viewManagerModel,
                startViewModel, gameViewModel, optionsViewModel, statsViewModel);
        final StartInputBoundary startInteractor = new StartInteractor(
                fileDataAccessObject, startOutputBoundary);

        StartController startController = new StartController(startInteractor);
        startView.setStartController(startController);
        return this;
    }

    public AppBuilder addEndUseCase() {
        final EndOutputBoundary endOutputBoundary = new EndPresenter(viewManagerModel,
                endViewModel, gameViewModel, startViewModel, optionsViewModel);
        final EndInputBoundary endInteractor = new EndInteractor(fileDataAccessObject, endOutputBoundary);

        EndController endController = new EndController(endInteractor);
        endView.setEndController(endController);
        return this;
    }

    public AppBuilder addGameUseCase() {
        final GameOutputBoundary gameOutputBoundary = new GamePresenter(viewManagerModel,
                gameViewModel, startViewModel, endViewModel);
        final GameInputBoundary gameInteractor = new GameInteractor(apiDataAccessObject, gameOutputBoundary);

        GameController gameController = new GameController(gameInteractor);
        gameView.setGameController(gameController);
        return this;
    }

    public AppBuilder addOptionsUseCase() {
        final OptionsOutputBoundary optionsOutputBoundary = new OptionsPresenter(viewManagerModel,
                optionsViewModel, startViewModel);
        final OptionsInputBoundary optionsInteractor = new OptionsInteractor(fileDataAccessObject, optionsOutputBoundary);

        OptionsController optionsController = new OptionsController(optionsInteractor);
        optionsView.setOptionsController(optionsController);
        return this;
    }

    public AppBuilder addStatsUseCase() {
        final StatsOutputBoundary statsOutputBoundary = new StatsPresenter(viewManagerModel,
                statsViewModel, startViewModel);
        final StatsInputBoundary statsInteractor = new StatsInteractor(fileDataAccessObject, statsOutputBoundary);

        StatsController statsController = new StatsController(statsInteractor);
        statsView.setStatsController(statsController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Wordle");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(startView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }

//    public JFrame buildWithKeys() {
//        final JFrame application = new JFrame("Wordle");
//        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        application.add(cardPanel);
//        for (Component component : cardPanel.getComponents()) {
//            if (component instanceof GameView) {
//                System.out.println("test");
//                application.addKeyListener((GameView)component);
//                break;
//            }
//        }
//
//        viewManagerModel.setState(startView.getViewName());
//        viewManagerModel.firePropertyChange();
//
//        return application;
//    }
}
