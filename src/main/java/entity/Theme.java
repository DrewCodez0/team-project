package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import entity.Status;

public interface Theme {

    Color getBackgroundColor();

    Color getTextColor();

    Font getFont();

    Color getColorForStatus(Status status);

    Shape getLetterBox();
}
