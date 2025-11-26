package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;

import entity.DarkTheme;
import entity.Theme;
import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsState;
import interface_adapter.stats.StatsViewModel;

public class StatsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "stats";
    private final StatsViewModel statsViewModel;
    private StatsController statsController;

    private final JLabel gamesPlayedLabel;
    private final JLabel winRateLabel;
    private final JLabel currentStreakLabel;
    private final JLabel maxStreakLabel;
    private final JButton backButton;
    private final JButton importButton;
    private final JButton exportButton;

    public StatsView(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
        this.statsViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final int lineSpacing = 40;

        final Theme theme = new DarkTheme();
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
        add(Box.createVerticalStrut(lineSpacing));
        add(gamesPlayedLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(winRateLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(currentStreakLabel);
        add(Box.createVerticalStrut(lineSpacing));
        add(maxStreakLabel);
        add(Box.createVerticalStrut(lineSpacing));

        final int verticalSpacing = 20;

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ViewHelper.setTheme(backButton, theme, ViewHelper.BUTTON);
        add(backButton);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        ViewHelper.setTheme(buttonPanel, theme);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            statsController.switchToStartView();
        }
        else if (e.getSource().equals(importButton)) {
            final JFileChooser fileChooser = new JFileChooser();
            final int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                final File selectedFile = fileChooser.getSelectedFile();
                statsController.importStats(selectedFile);
            }
        }
        else if (e.getSource().equals(exportButton)) {
            statsController.exportStats();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final StatsState state = (StatsState) evt.getNewValue();
            updateStatsDisplay(state);
            final String exportMessage = state.getExportMessage();
            if (exportMessage != null) {
                JOptionPane.showMessageDialog(this, exportMessage);
            }
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
                state.setError(null);
            }
        }
    }

    private void updateStatsDisplay(StatsState state) {
        gamesPlayedLabel.setText("Games Played: " + state.getGamesPlayed());
        winRateLabel.setText(String.format("Win Rate: %.1f%%", state.getWinPercentage()));
        currentStreakLabel.setText("Current Streak: " + state.getCurrentStreak());
        maxStreakLabel.setText("Max Streak: " + state.getMaxStreak());
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the controller for this view.
     *
     * @param statsController The controller responsible for handling stats-related actions.
     */
    public void setStatsController(StatsController statsController) {
        this.statsController = statsController;
        if (statsController != null) {
            statsController.execute();
        }
    }
}
