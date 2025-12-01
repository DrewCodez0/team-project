package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Theme;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsState;
import interface_adapter.stats.StatsViewModel;

public class StatsView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "stats";
    private static final int LINE_SPACING = 40;
    private static final int VERTICAL_SPACING = 20;

    private final StatsViewModel statsViewModel;
    private StatsController statsController;

    private final JLabel gamesPlayedLabel;
    private final JLabel winRateLabel;
    private final JLabel currentStreakLabel;
    private final JLabel maxStreakLabel;
    private final JButton backButton;
    private final JButton importButton;
    private final JButton exportButton;

    public StatsView(StatsViewModel statsViewModel, OptionsViewModel optionsViewModel) {
        this.statsViewModel = statsViewModel;
        this.statsViewModel.addPropertyChangeListener(this);
        optionsViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final ArrayList<JLabel> labelList = new ArrayList<>();
        final JLabel title = new JLabel("Statistics");
        labelList.add(title);

        gamesPlayedLabel = new JLabel("Games Played: ");
        labelList.add(gamesPlayedLabel);

        winRateLabel = new JLabel("Win Rate: ");
        labelList.add(winRateLabel);

        currentStreakLabel = new JLabel("Current Streak: ");
        labelList.add(currentStreakLabel);

        maxStreakLabel = new JLabel("Max Streak: ");
        labelList.add(maxStreakLabel);

        for (JLabel label : labelList) {
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(label);
            this.add(Box.createVerticalStrut(LINE_SPACING));
        }

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(backButton);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());

        importButton = new JButton("Import");
        importButton.addActionListener(this);
        buttonPanel.add(importButton);
        buttonPanel.add(Box.createHorizontalStrut(VERTICAL_SPACING));

        exportButton = new JButton("Export");
        exportButton.addActionListener(this);
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
            final Theme theme = state.getTheme();

            if (theme != null) {
                applyTheme(theme);
            }

            gamesPlayedLabel.setText(StatsViewModel.GAMES_PLAYED_LABEL + ": " + state.getGamesPlayed());
            winRateLabel.setText(String.format(StatsViewModel.WIN_PERCENTAGE_LABEL + ": %.1f%%", state.getWinPercentage()));
            currentStreakLabel.setText(StatsViewModel.CURRENT_STREAK_LABEL + ": " + state.getCurrentStreak());
            maxStreakLabel.setText(StatsViewModel.MAX_STREAK_LABEL + ": " + state.getMaxStreak());

            final String exportMessage = state.getExportMessage();
            if (exportMessage != null) {
                JOptionPane.showMessageDialog(this, exportMessage);
            }
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
                state.setError(null);
            }
        }
        else if (evt.getPropertyName().equals(OptionsViewModel.THEME)) {
            final OptionsState optionsState = (OptionsState) evt.getNewValue();
            applyTheme(optionsState.getTheme());
            repaint();
        }
    }

    private void applyTheme(Theme theme) {
        ViewHelper.setTheme(this, theme);
        ViewHelper.setTheme((JLabel) getComponent(0), theme, ViewHelper.TITLE); // Title
        ViewHelper.setTheme(gamesPlayedLabel, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(winRateLabel, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(currentStreakLabel, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(maxStreakLabel, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(backButton, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme((JPanel) getComponent(11), theme); // buttonPanel
        ViewHelper.setTheme(importButton, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(exportButton, theme, ViewHelper.BUTTON);
    }

    public String getViewName() {
        return VIEW_NAME;
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
