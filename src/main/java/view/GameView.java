package view;

import entity.*;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsViewModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int SQUARE_SIZE = 80;
    private static final int MARGINS = 25;
    private static final String VIEW_NAME = "game";

    float shakeOffset = 0;

    private final GameViewModel gameViewModel;
    private final OptionsViewModel optionsViewModel;
    private GameController gameController = null;

    private final JButton menu;
    private final JButton submit;
    private final JPanel gamePanel;

    public GameView(GameViewModel gameViewModel, OptionsViewModel optionsViewModel) {
        this.gameViewModel = gameViewModel;
        this.gameViewModel.addPropertyChangeListener(this);
        this.optionsViewModel = optionsViewModel;

        this.addPropertyChangeListener(this);

        ViewHelper.setTheme(this, getTheme());

        final JLabel title = new JLabel("Wordle");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        ViewHelper.setTheme(title, getTheme(), ViewHelper.TITLE);

        // maybe this should be its own class encapsulated in a buffer
        this.gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(this.optionsViewModel.getState().getLength() * GameView.SQUARE_SIZE,
                this.optionsViewModel.getState().getMaxGuesses() * GameView.SQUARE_SIZE + 2 * MARGINS));
        ViewHelper.setTheme(gamePanel, getTheme());

        final JPanel buffer1 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50, getTheme());
        final JPanel buffer2 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50, getTheme());

        final JPanel buttons = new JPanel();
        final ArrayList<JButton> buttonList = new ArrayList<>();
        menu = new JButton("Menu");
        buttonList.add(menu);
        submit = new JButton("Submit");
        buttonList.add(submit);
        for (JButton button : buttonList) {
            ViewHelper.setTheme(button, getTheme(), ViewHelper.BUTTON);
            buttons.add(button);
        }
        ViewHelper.setTheme(buttons, getTheme());

        menu.addActionListener(this);
        submit.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(buffer1, BorderLayout.WEST);
        this.add(buffer2, BorderLayout.EAST);
        this.add(buttons, BorderLayout.SOUTH);

        final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        final KeyAction action = new KeyAction(this, GameController.LETTER);
        for (char c : chars) {
            inputMap.put(KeyStroke.getKeyStroke(c), c);
            actionMap.put(c, action);
        }

        inputMap.put(KeyStroke.getKeyStroke(GameController.ENTER), GameController.ENTER);
        actionMap.put(GameController.ENTER, new KeyAction(this, GameController.ENTER));
        inputMap.put(KeyStroke.getKeyStroke(GameController.BACKSPACE), GameController.BACKSPACE);
        actionMap.put(GameController.BACKSPACE, new KeyAction(this, GameController.BACKSPACE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menu)) {
            this.gameController.switchToStartView();
        } else if (e.getSource().equals(submit)) {
            this.gameController.executeSubmit(this.gameViewModel.getState());
        } else {
            this.gameController.execute(this.gameViewModel.getState(), e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            repaint();
        } else if (evt.getPropertyName().equals("shake")) {
            WordShake shake = new WordShake(this);
            shake.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        final GameState gameState = this.gameViewModel.getState();
        ViewHelper.drawGameState(gameState, g2d, getTheme(), GameView.SQUARE_SIZE, shakeOffset);
//        gamePanel.setSize(gamePanel.getSize().width, (2 * gameState.getMaxGuesses() + 1) * GameView.SQUARE_SIZE / 2);
    }

    public String getViewName() {return VIEW_NAME;}

    private Theme getTheme() {
        return this.optionsViewModel.getState().getTheme();
    }

    public void setGameController(GameController gameController) {this.gameController = gameController;}

    private static class KeyAction extends AbstractAction {
        private final GameView view;
        private final String type;
        public KeyAction(GameView view, String type) {
            super();
            this.view = view;
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.actionPerformed(new ActionEvent(type, ActionEvent.ACTION_PERFORMED,
                    e.getActionCommand().toUpperCase()));
//            view.firePropertyChange(e.getActionCommand().toUpperCase(), null, null);
        }
    }

    private static class WordShake extends Thread {
        private final GameView view;
        public WordShake(GameView view) {
            this.view = view;
        }
        @Override
        public void run() {
            for (int i = 0; i < 30; i++) {
                this.view.shakeOffset = shake(i / 3.3);
                this.view.repaint();
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private float shake(double time) {
            return (float)(Math.sin(time * Math.PI) * Math.exp(-Math.pow(time / 2.0 - 2, 2)) * 5);
        }
    }
}