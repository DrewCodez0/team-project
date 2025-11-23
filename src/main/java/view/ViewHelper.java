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

public final class ViewHelper {
    public static final String LETTER = "letter";
    public static final String TITLE = "title";
    public static final String BUTTON = "button";
    public static final int MARGINS = 25;

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
     * @param theme the theme to apply to this panel
     * @return a JPanel with the specified dimensions and theme
     */
    public static JPanel createBufferPanel(int width, int height, Theme theme) {
        final JPanel bufferPanel = new JPanel();
        bufferPanel.setPreferredSize(new Dimension(width, height));
        setTheme(bufferPanel, theme);
        return bufferPanel;
    }

    /**
     * Draws a GameState.
     * @param gameState the GameState to draw
     * @param g2d the Graphics2D used to draw the GameState
     * @param theme the Theme to apply to the GameState
     * @param size the size of the boxes
     * @param shakeOffset the offset to apply if the boxes are being shaken
     */
    public static void drawGameState(GameState gameState, Graphics2D g2d, Theme theme, int size, float shakeOffset) {
        g2d.setFont(theme.getLetterFont());
        final AbstractWord[] words = gameState.getWords();
        final int yOffset = theme.getTitleFont().getSize() + MARGINS;
        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
            final float xOffset;
            if (i == gameState.getCurrentGuess()) {
                xOffset = shakeOffset;
            } else {
                xOffset = 0;
            }
            for (int j = 0; j < gameState.getLength(); j++) {
                final float x = size * j + MARGINS + xOffset;
                final int y = size * i + yOffset;
                ViewHelper.drawLetter(g2d, words[i].getLetter(j), x, y, size, theme);
            }
        }
    }

    /**
     * Draws a letter and its box.
     * @param g      the graphics used to draw
     * @param letter the AbstractLetter to draw
     * @param x      the x location to draw the letter at
     * @param y      the y location to draw the letter at
     * @param size   the size of the letter box
     * @param theme  the theme to apply to the letter
     */
    private static void drawLetter(Graphics2D g, AbstractLetter letter, float x, int y, int size, Theme theme) {
        final AffineTransform transform = getTransform(x, y, size);
        drawBoxBackground(g, letter, transform, theme);
        drawBoxOutline(g, transform, theme);
        drawBoxLetter(g, letter, (int) x, y, size, theme);
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
     * @param g         the Graphics2D used to draw
     * @param letter    the letter to draw
     * @param transform the AffineTransform that moves the shape to the required position
     * @param theme     the theme to apply to this letter
     */
    private static void drawBoxBackground(Graphics2D g, AbstractLetter letter, AffineTransform transform, Theme theme) {
        g.setPaint(theme.getColorForStatus(letter.getStatus()));
        g.fill(transform.createTransformedShape(theme.getLetterBox()));
    }

    /**
     * Helper function to draw the outline of a letter.
     * @param g         the Graphics2D used to draw
     * @param transform the AffineTransform that moves the shape to the required position
     * @param theme     the theme to apply to this letter
     */
    private static void drawBoxOutline(Graphics2D g, AffineTransform transform, Theme theme) {
        g.setPaint(theme.getOutlineColor());
        g.draw(transform.createTransformedShape(theme.getLetterBox()));
    }

    /**
     * Helper function to draw the text of a letter.
     * @param g The Graphics2D used to draw
     * @param letter the letter to draw
     * @param x the x position of the letter
     * @param y the y position of the letter
     * @param size the size of the letter
     * @param theme the theme to apply to this letter
     */
    private static void drawBoxLetter(Graphics2D g, AbstractLetter letter, int x, int y, int size, Theme theme) {
        g.setPaint(theme.getTextColor());
        final String c = Character.toString(letter.getCharacter());
        final FontMetrics fm = g.getFontMetrics(theme.getLetterFont());
        final int letterX = x + (size - fm.stringWidth(c)) / 2;
        final int letterY = y + (size - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(c, letterX, letterY);
    }
}
