package view;

import entity.AbstractLetter;
import entity.AbstractWord;
import entity.Theme;
import interface_adapter.game.GameState;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ViewHelper {
    public static final String LETTER = "letter";
    public static final String TITLE = "title";
    public static final String BUTTON = "button";
    public static final int MARGINS = 25;

    /**
     * Applies theme to the component.
     * @param component the component to apply the theme to
     * @param theme the theme to be applied
     * @param type the type of font to set
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

    public static JPanel createBufferPanel(int x, int y, Theme theme) {
        final JPanel bufferPanel = new JPanel();
        bufferPanel.setPreferredSize(new Dimension(x, y));
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
        final Shape shape = theme.getLetterBox();
        final AbstractWord[] words = gameState.getWords();
        final int yOffset = theme.getTitleFont().getSize() + MARGINS;
        for (int i = 0; i < gameState.getMaxGuesses(); i++) {
            boolean current = i == gameState.getCurrentGuess();
            for (int j = 0; j < gameState.getLength(); j++) {
                ViewHelper.drawLetter(g2d, words[i].getLetter(j), shape,
                        size * j + MARGINS,  size * i + yOffset, size, current, theme, shakeOffset);
            }
        }
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
     * @param theme the theme to apply to the letter
     * @param shakeOffset the offset to draw the letter at
     */
    private static void drawLetter(Graphics2D g, AbstractLetter letter, Shape shape, int x, int y, int size,
                           boolean current, Theme theme, float shakeOffset) {
        float dx = x;
        if (current) {
            dx += shakeOffset;
        }
        AffineTransform transform = getTransform(dx, y, size);
        drawBoxBackground(g, letter, shape, transform, theme);
        drawBoxOutline(g, shape, transform, theme);
        drawBoxLetter(g, letter, (int)dx, y, size, theme);
    }

    @NotNull
    private static AffineTransform getTransform(float x, int y, int size) {
        return new AffineTransform(size, 0, 0, size, x, y);
    }

    private static void drawBoxBackground(Graphics2D g, AbstractLetter letter, Shape shape, AffineTransform transform, Theme theme) {
        g.setPaint(theme.getColorForStatus(letter.getStatus()));
        g.fill(transform.createTransformedShape(shape));
    }

    private static void drawBoxOutline(Graphics2D g, Shape shape, AffineTransform transform, Theme theme) {
        g.setPaint(theme.getOutlineColor());
        g.draw(transform.createTransformedShape(shape));
    }

    private static void drawBoxLetter(Graphics2D g, AbstractLetter letter, int x, int y, int size, Theme theme) {
        g.setPaint(theme.getTextColor());
        String c = Character.toString(letter.getCharacter());
        FontMetrics fm = g.getFontMetrics(theme.getLetterFont());
        int letterX = x + (size - fm.stringWidth(c)) / 2;
        int letterY = y + (size - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(c, letterX, letterY);
    }
}
