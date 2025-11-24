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

public class StatsView extends JPanel implements ActionListener {
    private final String viewName = "stats";
    private final StatsViewModel statsViewModel = null;
    private StatsController statsController;

    private JLabel gamesPlayedLabel;
    private JLabel winRateLabel;
    private JLabel currentStreakLabel;
    private JLabel maxStreakLabel;
    private JButton backButton;
    private JButton importButton;
    private JButton exportButton;

    public StatsView(StatsViewModel statsViewModel) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final Theme theme = statsViewModel.getState();
        ViewHelper.setTheme(this, theme);

        final JLabel title = new JLabel("Statistics");
        title.setForeground(Color.WHITE);
        ViewHelper.setTheme(title, theme, ViewHelper.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        gamesPlayedLabel = new JLabel("Games Played: ");
        gamesPlayedLabel.setForeground(Color.WHITE);
        ViewHelper.setTheme(gamesPlayedLabel, theme, ViewHelper.BUTTON);
        gamesPlayedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        winRateLabel = new JLabel("Win Rate: ");
        winRateLabel.setForeground(Color.WHITE);
        ViewHelper.setTheme(winRateLabel, theme, ViewHelper.BUTTON);
        winRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentStreakLabel = new JLabel("Current Streak: ");
        currentStreakLabel.setForeground(Color.WHITE);
        ViewHelper.setTheme(currentStreakLabel, theme, ViewHelper.BUTTON);
        currentStreakLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        maxStreakLabel = new JLabel("Max Streak: ");
        maxStreakLabel.setForeground(Color.WHITE);
        ViewHelper.setTheme(maxStreakLabel, theme, ViewHelper.BUTTON);
        maxStreakLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(40));
        add(gamesPlayedLabel);
        add(Box.createVerticalStrut(40));
        add(winRateLabel);
        add(Box.createVerticalStrut(40));
        add(currentStreakLabel);
        add(Box.createVerticalStrut(40));
        add(maxStreakLabel);
        add(Box.createVerticalStrut(40));

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ViewHelper.setTheme(backButton, theme, ViewHelper.BUTTON);
        add(backButton);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        ViewHelper.setTheme(buttonPanel, theme, ViewHelper.BUTTON);
        buttonPanel.add(Box.createHorizontalGlue());

        importButton = new JButton("Import");
        importButton.addActionListener(this);
        ViewHelper.setTheme(importButton, theme, ViewHelper.BUTTON);
        buttonPanel.add(importButton);
        buttonPanel.add(Box.createHorizontalStrut(20));

        exportButton = new JButton("Export");
        exportButton.addActionListener(this);
        ViewHelper.setTheme(exportButton, theme, ViewHelper.BUTTON);
        buttonPanel.add(exportButton);
        buttonPanel.add(Box.createHorizontalGlue());
        add(buttonPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            statsController.switchToStartView();
        }
        else if (e.getSource().equals(importButton)) {

        }
        else if (e.getSource().equals(exportButton)){

        }
    }

//    public void propertyChange(PropertyChangeEvent evt) {
//        if ("state".equals(evt.getPropertyName())) {
//            StatsState state = statsViewModel.getState();
//            updateStatsDisplay(state);
//
//            // Also update the theme if it can change
//            if (state.getTheme() != null) {
//                Theme theme = state.getTheme();
//                ViewHelper.setTheme(this, theme);
//            }
//        }
//    }

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