package view;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setStartController(StartController startController) {this.startController = startController;}
}
