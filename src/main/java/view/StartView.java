package view;

import data_access.SettingsStore;
import entity.Theme;
import interface_adapter.game.GameState;
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
        setTheme(this, theme);

        final JLabel title = new JLabel("Wordle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTitleTheme(title, theme);

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
            setButtonTheme(button, theme);
            buttons.add(button);
        }

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        setTheme(buttons, theme);

        play.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (startController == null) return;
                        startController.startNewGame();
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
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final Theme theme = (Theme) evt.getNewValue();
        setTheme(this, theme);
    }

    private void setTheme(JComponent component, Theme theme) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        component.setFont(theme.getLetterFont());
    }

    private void setTitleTheme(JComponent component, Theme theme) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        component.setFont(theme.getTitleFont());
    }

    private void setButtonTheme(JComponent component, Theme theme) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        component.setFont(theme.getButtonFont()); // TODO make these all one function
    }

    public String getViewName() {return VIEW_NAME;}

    public void setStartController(StartController startController) {this.startController = startController;}
}
