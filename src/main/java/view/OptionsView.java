package view;

import data_access.SettingsStore;
import entity.Theme;
import entity.ThemeType;
import interface_adapter.ViewManagerModel;
import interface_adapter.options.OptionsController;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import data_access.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OptionsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "options";

    private final OptionsViewModel optionsViewModel;
    private final ViewManagerModel viewManagerModel;
    private OptionsController optionsController = null;

    private final JTextField lengthInput = new JTextField(5);
    private final JTextField guessesInput = new JTextField(6);
    private final JComboBox<Language> languageSelect = new JComboBox<>(Language.values());
    private final JComboBox<ThemeType> themeSelect = new JComboBox<>(ThemeType.values());
    private final JButton saveButton = new JButton("Save");

    public OptionsView(OptionsViewModel optionsViewModel, ViewManagerModel viewManagerModel) {
        this.optionsViewModel = optionsViewModel;
        this.viewManagerModel = viewManagerModel;

        this.optionsViewModel.addPropertyChangeListener(this);

        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Word Length: "));
        add(lengthInput);
        add(new JLabel("Max Guesses: "));
        add(guessesInput);
        add(new JLabel("Language: "));
        add(languageSelect);
        add(new JLabel("Theme: "));
        add(themeSelect);

        OptionsState loaded = SettingsStore.get();
        lengthInput.setText(String.valueOf(loaded.getLength()));
        guessesInput.setText(String.valueOf(loaded.getMaxGuesses()));
        languageSelect.setSelectedItem(loaded.getLanguage());
        ThemeType selectedType = ThemeType.LIGHT;
        for (ThemeType t : ThemeType.values()) {
            if (t.getTheme().getClass().equals(loaded.getTheme().getClass())) {
                selectedType = t;
                break;
            }
        }
        themeSelect.setSelectedItem(selectedType);

        saveButton.addActionListener(this);
        add(saveButton);
    }


    public void applySelectedTheme() {
        Object sel = themeSelect.getSelectedItem();
        ThemeType themeType = (sel instanceof ThemeType) ? (ThemeType) sel : ThemeType.LIGHT;
        Theme theme = themeType.getTheme();

        ViewHelper.setTheme(this, theme);
        for (Component c : getComponents()) {
            if (c instanceof JComponent jc) {
                if (jc instanceof JButton) {
                    ViewHelper.setTheme(jc, theme, ViewHelper.BUTTON);
                } else {
                    ViewHelper.setTheme(jc, theme);
                }
            }
        }

        SwingUtilities.updateComponentTreeUI(this);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != saveButton) return;

        int length;
        int maxGuesses;
        try {
            length = Integer.parseInt(lengthInput.getText().trim());
            maxGuesses = Integer.parseInt(guessesInput.getText().trim());
            if (length <= 0 || maxGuesses <= 0) {
                return;
            }
        } catch (NumberFormatException ex) {
            return;
        }

        Language language = (Language) languageSelect.getSelectedItem();
        Object sel = themeSelect.getSelectedItem();
        ThemeType themeType = (sel instanceof ThemeType) ? (ThemeType) sel : ThemeType.LIGHT;
        Theme theme = themeType.getTheme();

        OptionsState newState = new OptionsState();
        newState.setLength(length);
        newState.setMaxGuesses(maxGuesses);
        newState.setLanguage(language);
        newState.setTheme(theme);

        if (optionsController != null) {
                optionsController.execute(newState);
        }

        SettingsStore.save(newState);

        applySelectedTheme();

        viewManagerModel.setState("start");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("theme".equals(evt.getPropertyName()) || evt.getNewValue() instanceof Theme) {
            Theme t = (Theme) evt.getNewValue();
            if (t != null) {
                ViewHelper.setTheme(this, t);
                SwingUtilities.updateComponentTreeUI(this);
                repaint();
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setOptionsController(OptionsController controller) {
        this.optionsController = controller;
    }
}
