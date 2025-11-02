package view;

import interface_adapter.options.OptionsController;
import interface_adapter.options.OptionsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OptionsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "options";

    private final OptionsViewModel optionsViewModel;
    private OptionsController optionsController = null;

    public OptionsView(OptionsViewModel optionsViewModel) {
        this.optionsViewModel = optionsViewModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setOptionsController(OptionsController optionsController) {this.optionsController = optionsController;}
}
