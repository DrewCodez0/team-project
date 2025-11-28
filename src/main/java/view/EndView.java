package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import entity.AbstractLetter;
import entity.AbstractWord;
import entity.Status;
import entity.Theme;
import interface_adapter.end.EndController;
import interface_adapter.end.EndState;
import interface_adapter.end.EndViewModel;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "end";
    private final EndViewModel endViewModel;
    private EndController endController;

    private final ArrayList<JButton> buttons;
    private final JLabel titleLabel;
    private final JLabel messageLabel;
//    private final JLabel wordLabel;
    private final JLabel statsLabel;
    private final JButton playAgain;
    private final JButton export;
    private final JButton menu;
    private final JButton exit;

    public EndView(EndViewModel endViewModel, OptionsViewModel optionsViewModel) {
        this.endViewModel = endViewModel;
        this.endViewModel.addPropertyChangeListener(this);
        optionsViewModel.addPropertyChangeListener(this);

        final ArrayList<JLabel> labels = new ArrayList<>();
        titleLabel = new JLabel("Game Over", SwingConstants.CENTER);
        labels.add(titleLabel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        labels.add(messageLabel);

//        wordLabel = new JLabel("", SwingConstants.CENTER);
//        labels.add(wordLabel);

        statsLabel = new JLabel("", SwingConstants.CENTER);
        labels.add(statsLabel);

        for (JLabel label : labels) {
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        buttons = new ArrayList<>();
        playAgain = new JButton("Play Again");
        buttons.add(playAgain);
        export = new JButton("Export");
        buttons.add(export);
        menu = new JButton("Main Menu");
        buttons.add(menu);
        exit = new JButton("Exit");
        buttons.add(exit);
        final Dimension buttonDimension = new Dimension(300, 70);
        for (JButton button : buttons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(buttonDimension);
            button.addActionListener(this);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final ArrayList<JComponent> components = new ArrayList<>();
        components.addAll(labels);
        components.addAll(buttons);
        final int[] struts = {50, 20, 20, 40, 15, 15, 15};

        for (int i = 0; i < struts.length; i++) {
            this.add(Box.createVerticalStrut(struts[i]));
            this.add(components.get(i));
        }
        this.add(Box.createVerticalGlue());

        applyTheme(optionsViewModel.getState().getTheme());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final EndState endState = endViewModel.getState();

        if (e.getSource() == playAgain) {
            endController.replay(endState.getWord(), endState.isWon(),
                    endState.getGuessesUsed(), endState.getMaxGuesses(), endState.getGuessHistory());
        }
        else if (e.getSource() == export) {
            exportScore(endState);
        }
        else if (e.getSource() == menu) {
            endController.switchToStartView();
        }
        else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    private void applyTheme(Theme theme) {
        ViewHelper.setTheme(this, theme);
        ViewHelper.setTheme(titleLabel, theme, ViewHelper.TITLE);
        ViewHelper.setTheme(messageLabel, theme, ViewHelper.BUTTON);
        // messageLabel.setFont(new Font("Verdana", Font.BOLD, 35));
//        ViewHelper.setTheme(wordLabel, theme);
//        wordLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        ViewHelper.setTheme(statsLabel, theme);
        statsLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
        for (JButton button : buttons) {
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
        }
    }

    private void exportScore(EndState endState) {
        if (endState.getGuessHistory() == null) {
            JOptionPane.showMessageDialog(this, "Export Failed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final StringBuilder export = new StringBuilder();
        final AbstractWord[] guesses = endState.getGuessHistory();

        for (int i = 0; i < endState.getGuessesUsed(); i++) {
            final AbstractWord guess = guesses[i];

            for (int j = 0; j < guess.length(); j++) {
                final AbstractLetter letter = guess.getLetter(j);
                final Status status = letter.getStatus();

                switch (status) {
                    case CORRECT:
                        export.append("🟩");
                        break;
                    case PARTIAL:
                        export.append("🟨");
                        break;
                    case WRONG:
                        export.append("🟥");
                        break;
                    default:
                        export.append("⬜");
                        break;
                }
            }

            export.append(" ").append(guess.toString()).append("\n");
        }

        if (endState.isWon()) {
            export.append("You found the word in ")
                    .append(endState.getGuessesUsed())
                    .append(" guesses.");
        }

        else {
            export.append("The word was ").append(endState.getWord()).append(".");
        }

        // This copies the results to the user's clipboard
//        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(new StringSelection(export.toString()), null);

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Score");

        final String defaultName = String.format("wordle_score_%s_%d.txt", endState.getWord().toLowerCase(),
                System.currentTimeMillis());

        fileChooser.setSelectedFile(new File(defaultName));

        final int returnVal = fileChooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.toPath().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(export.toString());

                JOptionPane.showMessageDialog(this, "Export Success!", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Export Failed!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(EndViewModel.STATE)) {
            final EndState endState = (EndState) evt.getNewValue();
            updateView(endState);
        }
        else if (evt.getPropertyName().equals(OptionsViewModel.THEME)) {
            final Theme theme = ((OptionsState) evt.getNewValue()).getTheme();
            applyTheme(theme);
        }
    }

    private void updateView(EndState endState) {
        if (endState.isWon()) {
            titleLabel.setText("You Won!");
            titleLabel.setForeground(new Color(82, 143, 79));
        }
        else {
            titleLabel.setText("You Lost!");
            titleLabel.setForeground(new Color(200, 80, 80));
        }
        messageLabel.setText(endState.getMessage());
        //wordLabel.setText("Word: " + endState.getWord());

        if (endState.isWon()) {
            statsLabel.setText("You found the word in " + endState.getGuessesUsed() + " guesses.");
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setEndController(EndController endController) {
        this.endController = endController;
    }
}
