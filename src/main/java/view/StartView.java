package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import data_access.Language;
import entity.DarkTheme;
import entity.LightTheme;
import entity.SusTheme;
import entity.Theme;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.start.StartController;
import interface_adapter.start.StartViewModel;

public class StartView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "start";

    private StartViewModel startViewModel;
    private OptionsViewModel optionsViewModel;
    private StartController startController;

    private final JLabel title;
    private final JPanel buttons;
    private final ArrayList<JButton> buttonList;
    private final JButton play;
    private final JButton options;
    private final JButton stats;
    private final JButton exit;

    public StartView(StartViewModel startViewModel, OptionsViewModel optionsViewModel) {
        this.startViewModel = startViewModel;
        this.startViewModel.addPropertyChangeListener(this);
        this.optionsViewModel = optionsViewModel;
        this.optionsViewModel.addPropertyChangeListener(this);

        title = new JLabel("Wordle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons = new JPanel();
        buttonList = new ArrayList<>();
        play = new JButton("Play");
        buttonList.add(play);
        options = new JButton("Options");
        buttonList.add(options);
        stats = new JButton("Stats");
        buttonList.add(stats);
        exit = new JButton("Exit");
        buttonList.add(exit);
        for (JButton button : buttonList) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttons.add(button);
        }
        buttons.setLayout(new GridLayout(0, 1));

        play.addActionListener(
                evt -> {
                    if (evt.getSource().equals(play)) {
                        startController.switchToGameView();
                    }
                }
        );

        options.addActionListener(
                evt -> {
                    if (evt.getSource().equals(options)) {
                        // This is a temporary solution for the options use case not being implemented
                        final OptionsState optionsState = getOptions();
                        this.optionsViewModel.setState(optionsState);
                        this.optionsViewModel.firePropertyChange(OptionsViewModel.THEME);
                        // startController.switchToOptionsView();
                    }
                }
        );

        stats.addActionListener(
                evt -> {
                    if (evt.getSource().equals(stats)) {
                        startController.switchToStatsView();
                    }
                }
        );

        exit.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exit)) {
                        System.exit(0);
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(buttons);

        applyTheme(getTheme());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // This doesnt need to do anything
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OptionsViewModel.THEME)) {
            applyTheme(getTheme());
        }
        else if (evt.getPropertyName().equals(StartViewModel.STATE)) {
            final Theme theme = (Theme) evt.getNewValue();
            applyTheme(theme);
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setStartController(StartController startController) {
        this.startController = startController;
    }

    private Theme getTheme() {
        return this.optionsViewModel.getState().getTheme();
    }

    private void applyTheme(Theme theme) {
        ViewHelper.setTheme(this, theme);
        ViewHelper.setTheme(title, theme, ViewHelper.TITLE);
        for (JButton button : buttonList) {
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
        }
        ViewHelper.setTheme(buttons, theme);
    }

    /**
     * This is a temporary solution for the options use case not being implemented. This function shows a
     * JOptionPane with the relevant options and creates an OptionsState from the results.
     * @return the OptionsState corresponding to the selected values.
     */
    private OptionsState getOptions() {
        final OptionsState initialState = this.optionsViewModel.getState();
        final SpinnerNumberModel maxGuessesModel = new SpinnerNumberModel(initialState.getMaxGuesses(), 1, 20, 1);
        final JSpinner maxGuessesSpinner = new JSpinner(maxGuessesModel);
        final SpinnerNumberModel wordLengthModel = new SpinnerNumberModel(initialState.getLength(), 3, 15, 1);
        final JSpinner wordLengthSpinner = new JSpinner(wordLengthModel);

        final String[] themes = {"Dark", "Light", "Sus"};
        final JComboBox<String> themeComboBox = new JComboBox<>(themes);

        // ComboBox for Language options
        final String[] languages = {"English", "Spanish", "French", "German", "Italian"};
        final JComboBox<String> languageComboBox = new JComboBox<>(languages);

        // Create a panel to organize the components
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Add components to the panel
        panel.add(new JLabel("Max Guesses:"));
        panel.add(maxGuessesSpinner);
        panel.add(new JLabel("Word Length:"));
        panel.add(wordLengthSpinner);
        panel.add(new JLabel("Theme:"));
        panel.add(themeComboBox);
        panel.add(new JLabel("Language:"));
        panel.add(languageComboBox);

        // Show the JOptionPane dialog with the panel
        final int option = JOptionPane.showConfirmDialog(null, panel, "Game Settings",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Handle the user input
        if (option == JOptionPane.OK_OPTION) {
            final int maxGuesses = (Integer) maxGuessesSpinner.getValue();
            final int wordLength = (Integer) wordLengthSpinner.getValue();
            final String selectedTheme = (String) themeComboBox.getSelectedItem();
            final String selectedLanguage = (String) languageComboBox.getSelectedItem();

            final Theme theme;
            switch (selectedTheme) {
                case "Light":
                    theme = new LightTheme();
                    break;
                case "Sus":
                    theme = new SusTheme();
                    break;
                default:
                    theme = new DarkTheme();
                    break;
            }
            final Language language;
            switch (selectedLanguage) {
                case "Spanish":
                    language = Language.SPANISH;
                    break;
                case "French":
                    language = Language.FRENCH;
                    break;
                case "Italian":
                    language = Language.ITALIAN;
                    break;
                case "German":
                    language = Language.GERMAN;
                    break;
                default:
                    language = Language.ENGLISH;
                    break;
            }

            final OptionsState optionsState = new OptionsState();
            optionsState.setMaxGuesses(maxGuesses);
            optionsState.setLength(wordLength);
            optionsState.setTheme(theme);
            optionsState.setLanguage(language);
            return optionsState;
        }
        else {
            return optionsViewModel.getState();
        }
    }
}
