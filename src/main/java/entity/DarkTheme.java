package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.EnumMap;
import java.util.Map;

public class DarkTheme implements Theme {
    private final Color backgroundColor;
    private final Color textColor;
    private final Color outlineColor;
    private final Font letterFont;
    private final Font titleFont;
    private final Font buttonFont;
    private final Map<Status, Color> statusColors;
    private final Shape letterBox;

    public DarkTheme() {
        this.backgroundColor = Color.black;
        this.textColor = Color.white;
        this.outlineColor = Color.gray;
        this.letterFont = new Font("Tahoma", Font.PLAIN, 50);
        this.titleFont = new Font("Verdana", Font.BOLD, 70);
        this.buttonFont = new Font("Verdana", Font.PLAIN, 30);
        this.statusColors = new EnumMap<>(Status.class);
        this.statusColors.put(Status.INITIAL, new Color(30, 30, 30));
        this.statusColors.put(Status.IN_PROGRESS, new Color(57, 57, 57));
        this.statusColors.put(Status.WRONG, new Color(60, 50, 50));
        this.statusColors.put(Status.PARTIAL, new Color(181, 158, 61));
        this.statusColors.put(Status.CORRECT, new Color(82, 143, 79));
        this.letterBox = new RoundRectangle2D.Double(0, 0, 1.0, 1.0, 0.2, 0.2);
    }

    @Override
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    @Override
    public Color getTextColor() {
        return this.textColor;
    }

    @Override
    public Color getOutlineColor() {
        return this.outlineColor;
    }

    @Override
    public Font getLetterFont() {
        return this.letterFont;
    }

    @Override
    public Font getTitleFont() {
        return this.titleFont;
    }

    @Override
    public Font getButtonFont() {
        return this.buttonFont;
    }

    @Override
    public Color getColorForStatus(Status status) {
        return this.statusColors.get(status);
    }

    @Override
    public Shape getLetterBox() {
        return this.letterBox;
    }
}
