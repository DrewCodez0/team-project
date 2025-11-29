package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import entity.Theme;
import interface_adapter.start.StartController;
import interface_adapter.start.StartViewModel;

public class StartView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "start";

    private StartController startController;
    private final StartViewModel startViewModel;

    private final JButton play;
    private final JButton options;
    private final JButton stats;
    private final JButton help;
    private final JButton exit;

    public StartView(StartViewModel startViewModel) {
        this.startViewModel = startViewModel;
        startViewModel.addPropertyChangeListener(this);

        final Theme theme = startViewModel.getState();
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
        help = new JButton("Help");
        buttonList.add(help);
        exit = new JButton("Exit");
        buttonList.add(exit);
        for (JButton button : buttonList) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
            buttons.add(button);
        }
        buttons.setLayout(new GridLayout(0, 1));
        ViewHelper.setTheme(buttons, theme);

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
                        startController.switchToOptionsView();
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

        help.addActionListener(
                evt -> {
                    if (evt.getSource().equals(help)) {
                        showHelpView();
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
    }

    private void showHelpView() {
        final Theme theme = startViewModel.getState();
        final HelpView helpView = new HelpView(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                theme
        );
        helpView.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // This doesnt need to do anything
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final Theme theme = (Theme) evt.getNewValue();
        ViewHelper.setTheme(this, theme);
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setStartController(StartController startController) {
        this.startController = startController;
    }
}
