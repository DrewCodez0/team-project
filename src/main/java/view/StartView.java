package view;

import entity.Theme;
import interface_adapter.start.StartController;
import interface_adapter.start.StartViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StartView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "start";

    private final StartViewModel startViewModel;
    private StartController startController = null;

    private final JButton play;
    private final JButton options;
    private final JButton stats;
    private final JButton exit;

    public StartView(StartViewModel startViewModel) {
        this.startViewModel = startViewModel;
        this.startViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Wordle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        play = new JButton("Play");
        buttons.add(play);
        options = new JButton("Options");
        buttons.add(options);
        stats = new JButton("Stats");
        buttons.add(stats);
        exit = new JButton("Exit");
        buttons.add(exit);

        play.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(play)) {
                            startController.switchToGameView();
                        }
                    }
                }
        );

        options.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(options)) {
                            startController.switchToOptionsView();
                        }
                    }
                }
        );

        stats.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(stats)) {
                            startController.switchToStatsView();
                        }
                    }
                }
        );

        exit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if  (evt.getSource().equals(exit)) {
                            System.exit(0);
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final Theme theme = (Theme) evt.getNewValue();
        setTheme(theme);
    }

    private void setTheme(Theme theme) {
        this.setBackground(theme.getBackgroundColor());
        this.setForeground(theme.getTextColor());
        this.setFont(theme.getFont());
    }

    public String getViewName() {return viewName;}

    public void setStartController(StartController startController) {this.startController = startController;}
}
