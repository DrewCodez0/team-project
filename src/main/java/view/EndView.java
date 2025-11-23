package view;

import interface_adapter.end.EndController;
import interface_adapter.end.EndState;
import interface_adapter.end.EndViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "end";
    //private final optionsViewModel;
    private final EndViewModel endViewModel;
    private EndController endController = null;

    private final JLabel titleLabel;
    private final JLabel messageLabel;
    private final JLabel wordLabel;
    private final JLabel statsLabel;
    private final JButton playAgain;
    private final JButton menu;
    private final JButton exit;

    public EndView(EndViewModel endViewModel) {
        this.endViewModel = endViewModel;
        this.endViewModel.addPropertyChangeListener(this);

        titleLabel = new JLabel("Game Over", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 50));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Verdana", Font.BOLD, 35));

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Tahoma", Font.BOLD, 40));

        statsLabel = new JLabel("", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Verdana", Font.PLAIN, 25));

        playAgain = new JButton("Play Again");
        playAgain.setFont(new Font("Verdana", Font.PLAIN, 30));
        playAgain.setPreferredSize(new Dimension(300, 70));

        menu = new JButton("Main Menu");
        menu.setFont(new Font("Verdana", Font.PLAIN, 30));
        menu.setPreferredSize(new Dimension(300, 70));

        exit = new JButton("Exit");
        exit.setFont(new Font("Verdana", Font.PLAIN, 30));
        exit.setPreferredSize(new Dimension(300, 70));

        playAgain.addActionListener(this);
        menu.addActionListener(this);
        exit.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalStrut(50));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(30));
        this.add(messageLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(wordLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(statsLabel);
        this.add(Box.createVerticalStrut(40));
        this.add(playAgain);
        this.add(Box.createVerticalStrut(15));
        this.add(menu);
        this.add(Box.createVerticalStrut(15));
        this.add(exit);
        this.add(Box.createVerticalGlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgain) {
            final EndState endState = endViewModel.getState();
            endController.replay(endState.getWord(), endState.isWon(),
                    endState.getGuessesUsed(), endState.getMaxGuesses());
        } else if (e.getSource() == menu) {
            endController.switchToStartView();
        } else if (e.getSource() == exit) {
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
            playAgain.setText("You Won!");
            titleLabel.setForeground(new Color(82, 143, 79));
        } else {
            playAgain.setText("You Lost!");
            titleLabel.setForeground(new Color(200, 80, 80));
        }
        messageLabel.setText(endState.getMessage());
        wordLabel.setText("Word: " + endState.getWord());

        if (endState.isWon()) {
            statsLabel.setText("You found the word in " + endState.getGuessesUsed() + " guesses.");
        }
    }

    public String getViewName() {return VIEW_NAME;}

    public void setEndController(EndController endController) {this.endController = endController;}
}
