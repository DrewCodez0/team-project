package view;

import interface_adapter.game.GameController;
import interface_adapter.game.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "game";

    private final GameViewModel gameViewModel;
    private GameController gameController = null;

    public GameView(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {return viewName;}

    public void setGameController(GameController gameController) {this.gameController = gameController;}
}
