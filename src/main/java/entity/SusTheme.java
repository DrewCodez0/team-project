package entity;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.EnumMap;
import java.util.Map;

public class SusTheme implements Theme { // TODO make this extend darktheme to simplify things
    private final Color backgroundColor;
    private final Color textColor;
    private final Color outlineColor;
    private final Font font;
    private final Map<Status, Color> statusColors;
    private final Shape letterBox;

    public SusTheme() {
        this.backgroundColor = Color.black;
        this.textColor = Color.white;
        this.outlineColor = Color.gray;
        this.font = new Font("Arial", Font.PLAIN, 30);
        this.statusColors = new EnumMap<>(Status.class);
        this.statusColors.put(Status.INITIAL, new Color(30, 30, 30));
        this.statusColors.put(Status.IN_PROGRESS, new Color(57, 57, 57));
        this.statusColors.put(Status.WRONG, new Color(60, 50, 50));
        this.statusColors.put(Status.PARTIAL, new Color(181, 158, 61));
        this.statusColors.put(Status.CORRECT, new Color(82, 143, 79));
        Path2D path = new Path2D.Float();
        path.moveTo(0.298, 0.742);
        path.curveTo(0.283, 0.595, 0.28, 0.437, 0.297, 0.284);
        path.curveTo(0.309, 0.119, 0.367, 0.03, 0.509, 0.033);
        path.curveTo(0.656, 0.026, 0.748, 0.089, 0.756, 0.174);
        path.curveTo(0.647, 0.142, 0.507, 0.155, 0.481, 0.197);
        path.curveTo(0.444, 0.239, 0.448, 0.32, 0.478, 0.366);
        path.curveTo(0.572, 0.481, 0.749, 0.418, 0.799, 0.389);
        path.curveTo(0.865, 0.365, 0.891, 0.218, 0.756, 0.174);
        path.curveTo(0.876, 0.218, 0.864, 0.351, 0.799, 0.389);
        path.curveTo(0.816, 0.499, 0.807, 0.615, 0.802, 0.732);
        path.curveTo(0.797, 0.781, 0.79, 0.819, 0.774, 0.87);
        path.curveTo(0.755, 0.923, 0.632, 0.916, 0.624, 0.888);
        path.curveTo(0.599, 0.842, 0.631, 0.755, 0.564, 0.756);
        path.curveTo(0.49, 0.745, 0.525, 0.852, 0.496, 0.9);
        path.curveTo(0.442, 0.958, 0.333, 0.948, 0.32, 0.9);
        path.curveTo(0.304, 0.851, 0.301, 0.792, 0.298, 0.742);
        path.curveTo(0.121, 0.768, 0.156, 0.606, 0.152, 0.521);
        path.curveTo(0.158, 0.265, 0.194, 0.282, 0.297, 0.284);
        path.curveTo(0.28, 0.437, 0.283, 0.595, 0.298, 0.742);
        this.letterBox = path;
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
