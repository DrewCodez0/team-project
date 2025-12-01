package view;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import entity.AbstractLetter;
import entity.AbstractWord;
import entity.Theme;
import interface_adapter.game.GameState;

// This class uses many instances of variables called "x" and "y"
@SuppressWarnings("checkstyle:ParameterName")
public final class ViewHelper {
    public static final String LETTER = "letter";
    public static final String TITLE = "title";
    public static final String BUTTON = "button";
    public static final int SQUARE_SIZE = 80;
    public static final int MARGINS = 25;
    public static final double SQUARE_SCALE = 0.95;

    private ViewHelper() {
    }

    /**
     * Applies theme to the component.
     * @param component the component to apply the theme to
     * @param theme the theme to be applied
     * @param type the type of font to set, specified by the static constants in ViewHelper
     */
    public static void setTheme(JComponent component, Theme theme, String type) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
        switch (type) {
            case LETTER:
                component.setFont(theme.getLetterFont());
                break;
            case TITLE:
                component.setFont(theme.getTitleFont());
                break;
            case BUTTON:
                component.setFont(theme.getButtonFont());
                break;
            default:
                setTheme(component, theme);
                break;
        }
    }

    /**
     * Applies theme to the component.
     * @param component the component to apply the theme to
     * @param theme the theme to be applied
     */
    public static void setTheme(JComponent component, Theme theme) {
        component.setBackground(theme.getBackgroundColor());
        component.setForeground(theme.getTextColor());
    }

    /**
     * Creates an empty JPanel to be used as a buffer.
     * @param width the width of the panel
     * @param height the height of the panel
     * @return a JPanel with the specified dimensions and theme
     */
    public static JPanel createBufferPanel(int width, int height) {
        final JPanel bufferPanel = new JPanel();
        bufferPanel.setPreferredSize(new Dimension(width, height));
        return bufferPanel;
    }

    /**
     * Draws a GameState.
     * @param gameState the GameState to draw
     * @param g2d the Graphics2D used to draw the GameState
     * @param theme the Theme to apply to the GameState
     * @param panelSize the width of the panel
     * @param shakeOffset the offset to apply if the boxes are being shaken
     */
    public static void drawGameState(GameState gameState, Graphics2D g2d, Theme theme,
                                     Dimension panelSize, float shakeOffset) {
        final int[] sizes = getSquareSize(gameState, panelSize);
        final float xOffset = (float) (panelSize.getWidth() - gameState.getLength() * sizes[0]) / 2f;
        final int yOffset = theme.getTitleFont().getSize() + MARGINS;

        g2d.setFont(theme.getLetterFont());
        final AbstractWord[] words = gameState.getWords();
        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
            final float xShakeOffset;
            if (i == gameState.getCurrentGuess()) {
                xShakeOffset = shakeOffset;
            }
            else {
                xShakeOffset = 0;
            }
            for (int j = 0; j < gameState.getLength(); j++) {
                final float x = sizes[0] * j + MARGINS + xOffset + xShakeOffset + sizes[1];
                final int y = sizes[0] * i + yOffset + sizes[1];
                ViewHelper.drawLetter(g2d, words[i].getLetter(j), x, y, sizes[2], theme);
            }
        }
    }

    /**
     * Calculates parameters related to the square size for rendering letters.
     * @param gameState the GameState used to get the length and max guesses
     * @param panelSize the size of the surrounding panel
     * @return an integer array of length 3 containing the unscaled square size, square offset, and scaled square size
     */
    private static int[] getSquareSize(GameState gameState, Dimension panelSize) {
        final int size;
        final double expectedRatio = gameState.getLength() / (gameState.getMaxGuesses() * 1.0);
        final double trueRatio = panelSize.getWidth() / panelSize.getHeight();
        if (expectedRatio > trueRatio) {
            size = (int) (panelSize.getWidth() / gameState.getLength());
        }
        else {
            size = (int) (panelSize.getHeight() / gameState.getMaxGuesses());
        }
        final int squareSize = (int) (size * SQUARE_SCALE);
        final int squareOffset = (size - squareSize) / 2;
        return new int[]{size, squareOffset, squareSize};
    }

    /**
     * Draws a letter and its box.
     * @param g2d      the graphics used to draw
     * @param letter the AbstractLetter to draw
     * @param x      the x location to draw the letter at
     * @param y      the y location to draw the letter at
     * @param size   the size of the letter box
     * @param theme  the theme to apply to the letter
     */
    private static void drawLetter(Graphics2D g2d, AbstractLetter letter, float x, int y, int size, Theme theme) {
        final AffineTransform transform = getTransform(x, y, size);
        drawBoxBackground(g2d, letter, transform, theme);
        drawBoxOutline(g2d, transform, theme);
        drawBoxLetter(g2d, letter, (int) x, y, size, theme);
    }

    /**
     * Helper function to get the AffineTransform needed to move letters to the specified spot.
     * @param x the x position of the transform
     * @param y the y position of the transform
     * @param size the size of the letter
     * @return the AffineTransform that transforms a letter according to the parameters
     */
    @NotNull
    private static AffineTransform getTransform(float x, int y, int size) {
        return new AffineTransform(size, 0, 0, size, x, y);
    }

    /**
     * Helper function to draw the background of a letter.
     * @param g2d         the Graphics2D used to draw
     * @param letter    the letter to draw
     * @param transform the AffineTransform that moves the shape to the required position
     * @param theme     the theme to apply to this letter
     */
    private static void drawBoxBackground(Graphics2D g2d, AbstractLetter letter,
                                          AffineTransform transform, Theme theme) {
        g2d.setPaint(theme.getColorForStatus(letter.getStatus()));
        g2d.fill(transform.createTransformedShape(theme.getLetterBox()));
    }

    /**
     * Helper function to draw the outline of a letter.
     * @param g2d         the Graphics2D used to draw
     * @param transform the AffineTransform that moves the shape to the required position
     * @param theme     the theme to apply to this letter
     */
    private static void drawBoxOutline(Graphics2D g2d, AffineTransform transform, Theme theme) {
        g2d.setPaint(theme.getOutlineColor());
        g2d.draw(transform.createTransformedShape(theme.getLetterBox()));
    }

    /**
     * Helper function to draw the text of a letter.
     * @param g2d The Graphics2D used to draw
     * @param letter the letter to draw
     * @param x the x position of the letter
     * @param y the y position of the letter
     * @param size the size of the letter
     * @param theme the theme to apply to this letter
     */
    private static void drawBoxLetter(Graphics2D g2d, AbstractLetter letter, int x, int y, int size, Theme theme) {
        g2d.setPaint(theme.getTextColor());
        final String c = Character.toString(letter.getCharacter());
        final FontMetrics fm = g2d.getFontMetrics(theme.getLetterFont());
        final int letterX = x + (size - fm.stringWidth(c)) / 2;
        final int letterY = y + (size - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(c, letterX, letterY);
    }
}
