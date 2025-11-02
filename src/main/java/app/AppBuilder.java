package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.end.EndViewModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.start.StartPresenter;
import interface_adapter.start.StartViewModel;
import interface_adapter.stats.StatsViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManger viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

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
        gameView = new GameView(gameViewModel);
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

    public JFrame build() {
        final JFrame application = new JFrame("Wordle");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(startView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
