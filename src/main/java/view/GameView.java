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
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jetbrains.annotations.NotNull;

import entity.DarkTheme;
import entity.LightTheme;
import entity.SusTheme;
import entity.Theme;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;

public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "game";

    private float shakeOffset;

    private final GameViewModel gameViewModel;
    private final OptionsViewModel optionsViewModel;
    private GameController gameController;

    private final ArrayList<JButton> buttonList;
    private final ArrayList<JPanel> bufferList;
    private final JLabel title;
    private final JPanel gamePanel;
    private final JPanel buttons;
    private final JButton menu;
    private final JButton submit;

    public GameView(GameViewModel gameViewModel, OptionsViewModel optionsViewModel) {
        this.gameViewModel = gameViewModel;
        this.gameViewModel.addPropertyChangeListener(this);
        this.optionsViewModel = optionsViewModel;
        this.optionsViewModel.addPropertyChangeListener(this);

        this.addPropertyChangeListener(this);
        title = new JLabel("Wordle");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(this.optionsViewModel.getState().getLength() * ViewHelper.SQUARE_SIZE,
                this.optionsViewModel.getState().getMaxGuesses() * ViewHelper.SQUARE_SIZE));

        buttonList = new ArrayList<>();
        menu = new JButton("Menu");
        menu.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonList.add(menu);
        submit = new JButton("Submit");
        buttonList.add(submit);
        buttons = initializeButtons();

        bufferList = new ArrayList<>();

        initializeLayout();
        initializeInputHandlers();
        applyTheme(getTheme());

        /*
         * This is a temporary solution to test applying theme changes.
         * Press enter in the terminal to cycle the themes.
         */
        (new Thread(() -> {
            int test = 0;
            final Scanner scanner = new Scanner(System.in);
            while (true) {
                scanner.nextLine();
                final OptionsState options = this.optionsViewModel.getState();
                if (test % 3 == 0) {
                    options.setTheme(new SusTheme());
                }
                else if (test % 3 == 1) {
                    options.setTheme(new LightTheme());
                }
                else if (test % 3 == 2) {
                    options.setTheme(new DarkTheme());
                }
                this.optionsViewModel.setState(options);
                this.optionsViewModel.firePropertyChange(OptionsViewModel.THEME);
                test++;
            }
        })).start();
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
        switch (evt.getPropertyName()) {
            case GameViewModel.STATE:
                repaint();
                break;
            case GameViewModel.SHAKE:
                final WordShake shake = new WordShake(this);
                shake.start();
                break;
            case GameViewModel.NEW_GAME:
                gameController.startNewGame();
                break;
            case OptionsViewModel.THEME:
                applyTheme(getTheme());
                repaint();
                break;
            default:
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Graphics2D g2d = (Graphics2D) g;
        final GameState gameState = this.gameViewModel.getState();
        ViewHelper.drawGameState(gameState, g2d, getTheme(), gamePanel.getSize(), shakeOffset);
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void applyTheme(Theme theme) {
        ViewHelper.setTheme(this, theme);
        ViewHelper.setTheme(title, theme, ViewHelper.TITLE);
        ViewHelper.setTheme(gamePanel, theme, ViewHelper.LETTER);
        for (JButton button : buttonList) {
            ViewHelper.setTheme(button, theme, ViewHelper.BUTTON);
        }
        ViewHelper.setTheme(buttons, theme);
        for (JPanel panel : bufferList) {
            ViewHelper.setTheme(panel, theme);
        }
    }

    @NotNull
    private JPanel initializeButtons() {
        final JPanel buttonPanel = new JPanel();
        for (JButton button : buttonList) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    private void initializeLayout() {
        final JPanel buffer1 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50);
        bufferList.add(buffer1);
        final JPanel buffer2 = ViewHelper.createBufferPanel(ViewHelper.MARGINS, 50);
        bufferList.add(buffer2);
        final JPanel buffer3 = ViewHelper.createBufferPanel(50, ViewHelper.MARGINS);
        bufferList.add(buffer3);
        final JPanel bottomPanel = new JPanel();
        bufferList.add(bottomPanel);
        bottomPanel.add(buffer3);
        bottomPanel.add(buttons);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(buffer1, BorderLayout.WEST);
        this.add(buffer2, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
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
