package view;

import entity.*;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int SQUARE_SIZE = 80;
    private static final int MARGINS = 25;
    private static final String ENTER = "ENTER";
    private static final String LETTER = "LETTER";
    private static final String BACKSPACE = "BACK_SPACE";

    private final String viewName = "game";

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

        setTheme(getTheme(), this);

        final JLabel title = new JLabel("Wordle");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setTheme(getTheme(), title);

        // maybe this should be its own class encapsulated in a buffer
        this.gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(this.optionsViewModel.getState().getLength() * GameView.SQUARE_SIZE,
                this.optionsViewModel.getState().getMaxGuesses() * GameView.SQUARE_SIZE));
        setTheme(getTheme(), gamePanel);
//        this.gamePanel.setBackground(Color.green);

        final JPanel buffer1 = createBufferPanel();
        final JPanel buffer2 = createBufferPanel();

        final JPanel buttons = new JPanel();
        menu = new JButton("Menu");
        buttons.add(menu);
        submit = new JButton("Submit");
        buttons.add(submit);
        setTheme(getTheme(), buttons);

        menu.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(menu)) {
                            gameController.switchToStartView();
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
        final KeyAction action = new KeyAction(this, LETTER);
        for (char c : chars) {
            inputMap.put(KeyStroke.getKeyStroke(c), c);
            actionMap.put(c, action);
        }

        inputMap.put(KeyStroke.getKeyStroke(ENTER), ENTER);
        actionMap.put(ENTER, new KeyAction(this, ENTER));
        inputMap.put(KeyStroke.getKeyStroke(BACKSPACE), BACKSPACE);
        actionMap.put(BACKSPACE, new KeyAction(this, BACKSPACE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == LETTER) {
//            this.gameController.execute(this.gameViewModel.getState());
            final GameState state = this.gameViewModel.getState();
            state.nextLetter(e.getActionCommand().charAt(0));
//            state.getCurrentLetter().setCharacter(e.getActionCommand().charAt(0));
//            this.gameViewModel.setState(state);
//            this.gameViewModel.firePropertyChange();
            repaint();
//            for (int i = 0; i < state.getLength(); i++) {
//                AbstractLetter letter = words[guess].getLetter(i);
//                if (letter.isNull()) {
//                    letter.setCharacter(e.getActionCommand().charAt(0));
////                    letter.setStatus(Status.IN_PROGRESS);
//                    state.nextLetter();
//                    this.gameViewModel.setState(state);
//                    this.gameViewModel.firePropertyChange();
//                    repaint();
//                    break;
//                }
//            }
        } else if (e.getSource() == ENTER || e.getSource() == submit) {
            final GameState state = this.gameViewModel.getState();
            int guess = state.getCurrentGuess();
            if (state.getWords()[guess].isFull()) {
                // check the word first before the next part

                // update the gamestate
                state.submit();
//                this.gameViewModel.setState(new GameState(state.getWordToGuess(), state.getWords()););
//                this.gameViewModel.firePropertyChange();
                repaint(); // TODO fix this whole thing and put it in controller
            }
        } else if (e.getSource() == BACKSPACE) {
            final GameState state = this.gameViewModel.getState();
//            if (state.getCurrentLetterIndex() )
//            AbstractLetter letter = state.getCurrentLetter();
//            letter.resetCharacter(); // TODO fix all this
            state.previousLetter();
//            this.gameViewModel.setState(state);
//            this.gameViewModel.firePropertyChange();
            repaint();
        }
    } // game logic

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        System.out.println(evt);
//        final GameState gameState = ((GameViewModel) evt.getSource()).getState();
//        Shape shape = getLetterBox();
//        Graphics2D g = (Graphics2D) this.getGraphics();
//        AbstractWord[] words = gameState.getWords();
//        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
//            for (int j = 0; j < gameState.getLength(); j++) {
//                drawLetter(g, words[i].getLetter(j), GameView.SQUARE_SIZE * j,  GameView.SQUARE_SIZE * i, shape);
//            }
//        }
//        super.repaint();
    } // more game logic

//    @Override
//    public void keyTyped(KeyEvent e) {
//        System.out.println(e.getKeyChar());
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyChar());
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO put everything below this into the controller?
        Graphics2D g2d = (Graphics2D) g;
        final GameState gameState = this.gameViewModel.getState();
        Shape shape = getTheme().getLetterBox();
        AbstractWord[] words = gameState.getWords();
//        int size = gamePanel.getSize().width / (gameState.getLength() + 1);
        int size = GameView.SQUARE_SIZE;
        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
            for (int j = 0; j < gameState.getLength(); j++) {
                drawLetter(g2d, words[i].getLetter(j), shape,
                        size * j + size / 2,  size * i + size / 2, size);
            }
        }
        gamePanel.setSize(gamePanel.getSize().width, (2 * gameState.getMaxGuesses() + 1) * size / 2);
    }

    private void setTheme(Theme theme, JComponent component) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        component.setFont(theme.getFont());
    }

    private void drawLetter(Graphics2D g, AbstractLetter letter, Shape shape, int x, int y, int size) {
        AffineTransform transform = new AffineTransform(
                size, 0, 0, size, x, y);
        g.setPaint(getTheme().getColorForStatus(letter.getStatus()));
        g.fill(transform.createTransformedShape(shape));
        g.setPaint(getTheme().getOutlineColor());
        g.draw(transform.createTransformedShape(shape));
        g.setPaint(getTheme().getTextColor());
        String c = Character.toString(letter.getCharacter());
        FontMetrics fm = g.getFontMetrics(getTheme().getFont());
        int letterX = x + (size - fm.stringWidth(c)) / 2;
        int letterY = y + (size - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(c, letterX, letterY);
    }

    public String getViewName() {return viewName;}

    private Theme getTheme() {
        return this.optionsViewModel.getState().getTheme();
    }

    private JPanel createBufferPanel() {
        final JPanel bufferPanel = new JPanel();
        bufferPanel.setPreferredSize(new Dimension(GameView.SQUARE_SIZE / 2, 50));
        setTheme(getTheme(), bufferPanel);
        return  bufferPanel;
    }

    public void setGameController(GameController gameController) {this.gameController = gameController;}

    private class KeyAction extends AbstractAction {
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
}