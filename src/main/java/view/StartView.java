package view;

import entity.Theme;
import interface_adapter.options.OptionsState;
import interface_adapter.start.StartController;
import interface_adapter.start.StartViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

public class StartView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "start";

    private final StartViewModel startViewModel;
    private StartController startController = null;

    private final JButton play;
    private final JButton options;
    private final JButton stats;
    private final JButton exit;

    public StartView(StartViewModel startViewModel) {
        this.startViewModel = startViewModel;
        this.startViewModel.addPropertyChangeListener(this);

        Theme theme = this.startViewModel.getState();
        ViewHelper.setTheme(this, theme);

        final JLabel title = new JLabel("Wordle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        ViewHelper.setTheme(title, theme, ViewHelper.TITLE);

        final JPanel buttons = new JPanel();
        final ArrayList<JButton> buttonList = new ArrayList<>();
        play = new JButton("Play");
        buttonList.add(play);
        options = new JButton("Options");
        buttonList.add(options);
        stats = new JButton("Stats");
        buttonList.add(stats);
        exit = new JButton("Exit");
        buttonList.add(exit);
        for (JButton button : buttonList) {
            button.setPreferredSize(new Dimension(300, 100));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
            buttons.add(button);
        }

//        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setLayout(new GridLayout(0, 1));
        ViewHelper.setTheme(buttons, theme);

        play.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(play)) {
                            startController.switchToGameView(new OptionsState()); //TODO
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
        ViewHelper.setTheme(this, theme);
    }


    public String getViewName() {return VIEW_NAME;}

    public void setStartController(StartController startController) {this.startController = startController;}
}
