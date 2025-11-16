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

        setTheme(this, getTheme());

        final JLabel title = new JLabel("Wordle");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setTheme(title, getTheme());

        // maybe this should be its own class encapsulated in a buffer
        this.gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(this.optionsViewModel.getState().getLength() * GameView.SQUARE_SIZE,
                this.optionsViewModel.getState().getMaxGuesses() * GameView.SQUARE_SIZE));
        setTheme(gamePanel, getTheme());
//        this.gamePanel.setBackground(Color.green);

        final JPanel buffer1 = createBufferPanel();
        final JPanel buffer2 = createBufferPanel();

        final JPanel buttons = new JPanel();
        menu = new JButton("Menu");
        buttons.add(menu);
        submit = new JButton("Submit");
        buttons.add(submit);
        setTheme(buttons, getTheme());

        menu.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(menu)) {

                        }
                    }
                }
        );

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
//        repaint();
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
        Shape shape = getTheme().getLetterBox();
        AbstractWord[] words = gameState.getWords();
//        int size = gamePanel.getSize().width / (gameState.getLength() + 1);
        int size = GameView.SQUARE_SIZE;
        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
            boolean current = i == gameState.getCurrentGuess();
            for (int j = 0; j < gameState.getLength(); j++) {
                drawLetter(g2d, words[i].getLetter(j), shape,
                        size * j + size / 2,  size * i + size / 2, size, current);
            }
        }
        gamePanel.setSize(gamePanel.getSize().width, (2 * gameState.getMaxGuesses() + 1) * size / 2);
    }

    /**
     * Applies theme to the component.
     * @param component the component to apply the theme to
     * @param theme the theme to be applied
     */
    private void setTheme(JComponent component, Theme theme) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        component.setFont(theme.getFont());
    }

    /**
     * Draws a letter and its box.
     * @param g the graphics used to draw
     * @param letter the AbstractLetter to draw
     * @param shape the shape of the letter box defined by the active Theme
     * @param x the x location to draw the letter at
     * @param y the y location to draw the letter at
     * @param size the size of the letter box
     * @param current whether the current guess is being drawn
     */
    private void drawLetter(Graphics2D g, AbstractLetter letter, Shape shape, int x, int y, int size, boolean current) {
        float dx = x;
        if (current) {
            dx += shakeOffset;
        }
        AffineTransform transform = getTransform(dx, y, size);
        drawBoxBackground(g, letter, shape, transform);
        drawBoxOutline(g, shape, transform);
        drawBoxLetter(g, letter, (int)dx, y, size);
    }

    @NotNull
    private AffineTransform getTransform(float x, int y, int size) {
        return new AffineTransform(size, 0, 0, size, x, y);
    }

    private void drawBoxBackground(Graphics2D g, AbstractLetter letter, Shape shape, AffineTransform transform) {
        g.setPaint(getTheme().getColorForStatus(letter.getStatus()));
        g.fill(transform.createTransformedShape(shape));
    }

    private void drawBoxOutline(Graphics2D g, Shape shape, AffineTransform transform) {
        g.setPaint(getTheme().getOutlineColor());
        g.draw(transform.createTransformedShape(shape));
    }

    private void drawBoxLetter(Graphics2D g, AbstractLetter letter, int x, int y, int size) {
        g.setPaint(getTheme().getTextColor());
        String c = Character.toString(letter.getCharacter());
        FontMetrics fm = g.getFontMetrics(getTheme().getFont());
        int letterX = x + (size - fm.stringWidth(c)) / 2;
        int letterY = y + (size - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(c, letterX, letterY);
    }

    public String getViewName() {return VIEW_NAME;}

    private Theme getTheme() {
        return this.optionsViewModel.getState().getTheme();
    }

    private JPanel createBufferPanel() {
        final JPanel bufferPanel = new JPanel();
        bufferPanel.setPreferredSize(new Dimension(GameView.MARGINS, 50));
        setTheme(bufferPanel, getTheme());
        return bufferPanel;
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