package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jetbrains.annotations.NotNull;

import entity.Theme;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsViewModel;

public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int SQUARE_SIZE = 80;
    private static final int MARGINS = 25;
    private static final String VIEW_NAME = "game";

    private float shakeOffset;

    private final GameViewModel gameViewModel;
    private final OptionsViewModel optionsViewModel;
    private GameController gameController;

    private final JButton menu;
    private final JButton submit;

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
        final JPanel gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(this.optionsViewModel.getState().getLength() * GameView.SQUARE_SIZE,
                this.optionsViewModel.getState().getMaxGuesses() * GameView.SQUARE_SIZE + 2 * MARGINS));
        ViewHelper.setTheme(gamePanel, getTheme(), ViewHelper.LETTER);

        final ArrayList<JButton> buttonList = new ArrayList<>();
        menu = new JButton("Menu");
        buttonList.add(menu);
        submit = new JButton("Submit");
        buttonList.add(submit);
        final JPanel buttons = initializeButtons(buttonList);

        initializeLayout(title, gamePanel, buttons);
        initializeInputHandlers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menu)) {
            this.gameController.switchToStartView();
        }
        else if (e.getSource().equals(submit)) {
            this.gameController.executeSubmit(this.gameViewModel.getState());
        }
        else {
            this.gameController.execute(this.gameViewModel.getState(), e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            repaint();
        }
        else if (evt.getPropertyName().equals("shake")) {
            final WordShake shake = new WordShake(this);
            shake.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Graphics2D g2d = (Graphics2D) g;
        final GameState gameState = this.gameViewModel.getState();
        ViewHelper.drawGameState(gameState, g2d, getTheme(), GameView.SQUARE_SIZE, shakeOffset);
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @NotNull
    private JPanel initializeButtons(ArrayList<JButton> buttonList) {
        final JPanel buttons = new JPanel();
        for (JButton button : buttonList) {
            ViewHelper.setTheme(button, getTheme(), ViewHelper.BUTTON);
            button.addActionListener(this);
            buttons.add(button);
        }
        ViewHelper.setTheme(buttons, getTheme());
        return buttons;
    }

    private void initializeLayout(JLabel title, JPanel gamePanel, JPanel buttons) {
        final JPanel buffer1 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50, getTheme());
        final JPanel buffer2 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50, getTheme());
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(buffer1, BorderLayout.WEST);
        this.add(buffer2, BorderLayout.EAST);
        this.add(buttons, BorderLayout.SOUTH);
    }

    private void initializeInputHandlers() {
        final InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = this.getActionMap();
        final KeyAction action = new KeyAction(this, GameController.LETTER);

        final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char c : chars) {
            inputMap.put(KeyStroke.getKeyStroke(c), c);
            actionMap.put(c, action);
        }

        inputMap.put(KeyStroke.getKeyStroke(GameController.ENTER), GameController.ENTER);
        actionMap.put(GameController.ENTER, new KeyAction(this, GameController.ENTER));
        inputMap.put(KeyStroke.getKeyStroke(GameController.BACKSPACE), GameController.BACKSPACE);
        actionMap.put(GameController.BACKSPACE, new KeyAction(this, GameController.BACKSPACE));
    }

    private Theme getTheme() {
        return this.optionsViewModel.getState().getTheme();
    }

    private static class KeyAction extends AbstractAction {
        private final GameView view;
        private final String type;

        KeyAction(GameView view, String type) {
            this.view = view;
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.actionPerformed(new ActionEvent(type, ActionEvent.ACTION_PERFORMED,
                    e.getActionCommand().toUpperCase()));
        }
    }

    private static class WordShake extends Thread {
        private static final int SHAKE_MODIFIER = 5;
        private static final int SLEEP_TIME = 20;
        private static final int SHAKE_FRAMES = 30;
        private static final double SHAKE_FRAME_MODIFIER = 3.3;
        private final GameView view;

        WordShake(GameView view) {
            this.view = view;
        }

        @Override
        public void run() {
            for (int i = 0; i < SHAKE_FRAMES; i++) {
                this.view.shakeOffset = shake(i / SHAKE_FRAME_MODIFIER);
                this.view.repaint();
                try {
                    sleep(SLEEP_TIME);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private float shake(double time) {
            return (float) (Math.sin(time * Math.PI) * Math.exp(-Math.pow(time / 2.0 - 2, 2)) * SHAKE_MODIFIER);
        }
    }
}
