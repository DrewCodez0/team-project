package view;

import interface_adapter.end.EndController;
import interface_adapter.end.EndViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "end";

    private final EndViewModel endViewModel;
    private EndController endController = null;

    public EndView(EndViewModel endViewModel) {
        this.endViewModel = endViewModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setEndController(EndController endController) {this.endController = endController;}
}
