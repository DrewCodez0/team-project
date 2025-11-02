package view;

import interface_adapter.stats.StatsController;
import interface_adapter.stats.StatsViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StatsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "stats";

    private final StatsViewModel statsViewModel;
    private StatsController statsController = null;

    public StatsView(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setStatsController(StatsController statsController) {this.statsController = statsController;}
}
