package data_access;

import javax.swing.JOptionPane;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public WordNotFoundException(String message, boolean showDialog) {
        super(message);
        if (showDialog) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
