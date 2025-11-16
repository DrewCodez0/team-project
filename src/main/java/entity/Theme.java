package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;

public interface Theme {

    Color getBackgroundColor();

    Color getTextColor();

    Color getOutlineColor();

    Font getFont();

    Color getColorForStatus(Status status);

    Shape getLetterBox();
}
