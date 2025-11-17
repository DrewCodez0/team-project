package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;

public interface Theme {

    /**
     * Returns this theme's color used for drawing backgrounds.
     * @return the background color associated with this Theme
     */
    Color getBackgroundColor();

    /**
     * Returns this theme's color used for drawing text.
     * @return the text color associated with this Theme
     */
    Color getTextColor();

    /**
     * Returns this theme's color used for drawing outlines.
     * @return the outline color associated with this Theme
     */
    Color getOutlineColor();

    /**
     * Returns this theme's font used for letters during gameplay.
     * @return the letter font associated with this Theme
     */
    Font getLetterFont();

    /**
     * Returns this theme's font used for titles.
     * @return the title font associated with this Theme
     */
    Font getTitleFont();

    /**
     * Returns this theme's font used for buttons.
     * @return the button font associated with this Theme
     */
    Font getButtonFont();

    /**
     * Returns this theme's color for the specified status.
     * @param status the status of the letter
     * @return the color corresponding to the specified status
     */
    Color getColorForStatus(Status status);

    /**
     * Returns this theme's shape for drawing letters.
     * @return the shape associated with this Theme
     */
    Shape getLetterBox();
}
