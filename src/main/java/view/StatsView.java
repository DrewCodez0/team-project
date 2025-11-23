package view;

import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsState;
import interface_adapter.stats.StatsViewModel;
import entity.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.List;

public class StatsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "stats";
    private final StatsViewModel statsViewModel;
    private StatsController statsController;

    private JLabel gamesPlayedLabel;
    private JLabel winRateLabel;
    private JLabel currentStreakLabel;
    private JLabel maxStreakLabel;
    private JButton backButton;
    private JPanel distributionPanel;

    public StatsView(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
        this.statsViewModel.addPropertyChangeListener(this);

        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        StatsState currentState = this.statsViewModel.getState();
        if (currentState.getTheme() != null) {
            Theme theme = currentState.getTheme();
            ViewHelper.setTheme(this, theme);
        }

        JLabel title = new JLabel("Statistics");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));


        gamesPlayedLabel = new JLabel("Games Played: 0");
        gamesPlayedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        winRateLabel = new JLabel("Win Rate: 0%");
        winRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentStreakLabel = new JLabel("Current Streak: 0");
        currentStreakLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        maxStreakLabel = new JLabel("Max Streak: 0");
        maxStreakLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        // Initialize distributionPanel to avoid NullPointerException
        distributionPanel = new JPanel();
        distributionPanel.setLayout(new BoxLayout(distributionPanel, BoxLayout.Y_AXIS));
        distributionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(title);
        add(Box.createVerticalStrut(20));
        add(gamesPlayedLabel);
        add(winRateLabel);
        add(currentStreakLabel);
        add(maxStreakLabel);
        add(Box.createVerticalStrut(10));
        add(distributionPanel);
        add(Box.createVerticalStrut(20));
        add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            if (statsController != null) {
                statsController.switchToStartView();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            StatsState state = statsViewModel.getState();
            updateStatsDisplay(state);

            // Also update the theme if it can change
            if (state.getTheme() != null) {
                Theme theme = state.getTheme();
                ViewHelper.setTheme(this, theme);
            }
        }
    }

    private void updateStatsDisplay(StatsState state) {
        gamesPlayedLabel.setText("Games Played: " + state.getTotalGames());

        double winRate = state.getTotalGames() > 0
                ? (double) state.getGamesWon() / state.getTotalGames() * 100
                : 0;
        winRateLabel.setText(String.format("Win Rate: %.1f%%", winRate));

        currentStreakLabel.setText("Current Streak: " + state.getCurrentWinStreak());
        maxStreakLabel.setText("Max Streak: " + state.getLongestWinStreak());

    }

    public String getViewName() {
        return viewName;
    }

    public void setStatsController(StatsController statsController) {
        this.statsController = statsController;
        // Load stats when controller is set
        if (statsController != null) {
            statsController.execute();
        }
    }
}