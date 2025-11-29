package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import entity.Theme;

/**
 * Custom JDialog to show instructions for how to play the game.
 */
public class HelpView extends JDialog {
    private static final String TUTORIAL_FILE = "tutorial.txt";
    private static final int TUTORIAL_WIDTH = 400;
    private static final int TUTORIAL_HEIGHT = 500;

    private final JTextArea textArea;
    private final JScrollPane scrollPane;
    private final JButton closeButton;
    private final JPanel buttonPanel;

    public HelpView(JFrame frame) {
        super(frame, "How To Play", true);

        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(TUTORIAL_WIDTH, TUTORIAL_HEIGHT);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        final String tutorialFile = loadTutorialFile();

        textArea = new JTextArea(tutorialFile);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        scrollPane = new JScrollPane(textArea);
        closeButton = new JButton("Close");
        closeButton.addActionListener(evt -> dispose());

        buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Apply the specified theme to this view.
     * @param theme the theme to apply
     */
    public void applyTheme(Theme theme) {
        ViewHelper.setTheme(textArea, theme);
        ViewHelper.setTheme(scrollPane, theme);
        ViewHelper.setTheme(closeButton, theme, ViewHelper.BUTTON);
        ViewHelper.setTheme(buttonPanel, theme);
    }

    private String loadTutorialFile() {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TUTORIAL_FILE);

            if (inputStream == null) {
                return "Tutorial file not found!";
            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }
        }
        catch (IOException ex) {
            return "Error loading tutorial file!";
        }

        return stringBuilder.toString();
    }
}
