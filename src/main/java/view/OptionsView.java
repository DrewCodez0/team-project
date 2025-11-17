package view;

import interface_adapter.options.OptionsController;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import data_access.Language;

public class OptionsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "options";

    private final OptionsViewModel optionsViewModel;
    private OptionsController optionsController = null;
    private final JTextField lenthInput = new JTextField(5);
    private final JTextField guessesInput = new JTextField(6);
    private final JComboBox<Language> languageSelect = new JComboBox<>(Language.values());
    private final JButton saveButton = new JButton("Save");

    public OptionsView(OptionsViewModel optionsViewModel) {
        this.optionsViewModel = optionsViewModel;
        setLayout(new GridLayout(2, 4));
        add(new JLabel("Word Length: "));
        add(lenthInput);
        add(new JLabel("Max Guesses: "));
        add(guessesInput);
        add(new JLabel("Language: "));
        add(languageSelect);
        saveButton.addActionListener(this);
        add(saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OptionsState newState = new OptionsState();
        newState.setLength(Integer.parseInt(lenthInput.getText()));
        newState.setMaxGuesses(Integer.parseInt(guessesInput.getText()));
        newState.setLanguage((Language)languageSelect.getSelectedItem());
        optionsController.execute(newState);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setOptionsController(OptionsController optionsController) {this.optionsController = optionsController;}
}
