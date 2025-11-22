package data_access;

import javax.swing.JOptionPane;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
