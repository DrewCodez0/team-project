package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Theme;
import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsState;
import interface_adapter.stats.StatsViewModel;

public class StatsView extends JPanel implements ActionListener {
    private final String viewName = "stats";
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
        final int lineSpacing = 40;

        final JLabel title = getJavaLabel(theme);

        add(title);
        add(Box.createVerticalStrut(lineSpacing));
        add(gamesPlayedLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(winRateLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(currentStreakLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(maxStreakLabel);
        add(Box.createVerticalStrut(lineSpacing));

        getButtons(theme);
    }

    private void getButtons(Theme theme) {
        final int verticalSpacing = 20;

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
        buttonPanel.add(Box.createHorizontalStrut(verticalSpacing));

        exportButton = new JButton("Export");
        exportButton.addActionListener(this);
        ViewHelper.setTheme(exportButton, theme, ViewHelper.BUTTON);
        buttonPanel.add(exportButton);
        buttonPanel.add(Box.createHorizontalGlue());
        add(buttonPanel);
    }

    private JLabel getJavaLabel(Theme theme) {
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
        return title;
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

    private void updateStatsDisplay(StatsState state) {
        gamesPlayedLabel.setText("Games Played: " + state.getTotalGames());
        final int percentageMultiplier = 100;
        final double winRate;
        if (state.getTotalGames() > 0) {
            winRate = (double) state.getGamesWon() / state.getTotalGames() * percentageMultiplier;
        }
        else {
            winRate = 0;
        }
        winRateLabel.setText(String.format("Win Rate: %.1f%%", winRate));

        currentStreakLabel.setText("Current Streak: " + state.getCurrentWinStreak());
        maxStreakLabel.setText("Max Streak: " + state.getLongestWinStreak());

    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the controller for this view.
     * @param statsController The controller responsible for handling stats-related actions.
     */
    public void setStatsController(StatsController statsController) {
        this.statsController = statsController;
        if (statsController != null) {
            statsController.execute();
        }
    }
}
