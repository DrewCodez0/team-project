package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class DarkTheme implements Theme {
    private final Color backgroundColor;
    private final Color textColor;
    private final Font font;
    private final Map<Status, Color> statusColors;
    private final Shape letterBox;

    public DarkTheme() {
        this.backgroundColor = Color.black;
        this.textColor = Color.white;
        this.font = new Font("Arial", Font.PLAIN, 20);
        this.statusColors = new HashMap<>();
        this.statusColors.put(Status.INITIAL, new Color(30, 30, 30));
        this.statusColors.put(Status.IN_PROGRESS, new Color(57, 57, 57));
        this.statusColors.put(Status.WRONG, new Color(60, 50, 50));
        this.statusColors.put(Status.PARTIAL, new Color(181, 158, 61));
        this.statusColors.put(Status.CORRECT, new Color(82, 143, 79));
        this.letterBox = new RoundRectangle2D.Double(0, 0, 1.0, 1.0, 0.1, 0.1);
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
    public Font getFont() {
        return this.font;
    }

    @Override
    public Color getColorForStatus(Status status) {
        return this.statusColors.get(status);
    }

    // This will need AffineTransforms to adjust it later
    @Override
    public Shape getLetterBox() {
        return this.letterBox;
    }
}
