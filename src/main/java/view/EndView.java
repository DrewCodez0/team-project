package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.Theme;
import interface_adapter.end.EndController;
import interface_adapter.end.EndState;
import interface_adapter.end.EndViewModel;
import interface_adapter.start.StartViewModel;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "end";
    //private final optionsViewModel;
    private final EndViewModel endViewModel;
    private EndController endController;

    private final JLabel titleLabel;
    private final JLabel messageLabel;
    private final JLabel wordLabel;
    private final JLabel statsLabel;
    private final JButton playAgain;
    private final JButton menu;
    private final JButton exit;

    public EndView(EndViewModel endViewModel, StartViewModel startViewModel) {
        this.endViewModel = endViewModel;
        this.endViewModel.addPropertyChangeListener(this);

        final Theme theme = startViewModel.getState();
        ViewHelper.setTheme(this, theme);

        final ArrayList<JLabel> labels = new ArrayList<>();
        titleLabel = new JLabel("Game Over", SwingConstants.CENTER);
        ViewHelper.setTheme(titleLabel, theme, ViewHelper.TITLE);
        labels.add(titleLabel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
//        messageLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        ViewHelper.setTheme(messageLabel, theme, ViewHelper.BUTTON);
        labels.add(messageLabel);

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        labels.add(wordLabel);

        statsLabel = new JLabel("", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
        labels.add(statsLabel);

        for (JLabel label : labels) {
            ViewHelper.setTheme(label, theme);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        final ArrayList<JButton> buttonList = new ArrayList<>();
        playAgain = new JButton("Play Again");
        buttonList.add(playAgain);
        menu = new JButton("Main Menu");
        buttonList.add(menu);
        exit = new JButton("Exit");
        buttonList.add(exit);
        final Dimension buttonDimension = new Dimension(300, 70);
        for (JButton button : buttonList) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
            button.setPreferredSize(buttonDimension);
            button.addActionListener(this);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final ArrayList<JComponent> components = new ArrayList<>();
        components.addAll(labels);
        components.addAll(buttonList);
        final int[] struts = {50, 30, 20, 20, 40, 15, 15};

        for (int i = 0; i < struts.length; i++) {
            this.add(Box.createVerticalStrut(struts[i]));
            this.add(components.get(i));
        }
        this.add(Box.createVerticalGlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgain) {
            final EndState endState = endViewModel.getState();
            endController.replay(endState.getWord(), endState.isWon(),
                    endState.getGuessesUsed(), endState.getMaxGuesses());
        }
        else if (e.getSource() == menu) {
            endController.switchToStartView();
        }
        else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final EndState endState = (EndState) evt.getNewValue();
            updateView(endState);
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
