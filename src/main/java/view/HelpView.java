package view;

import entity.Theme;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpView extends JDialog {
    private static final String TUTORIAL_FILE = "tutorial.txt";
    private static final int TUTORIAL_WIDTH = 400;
    private static final int TUTORIAL_HEIGHT = 500;

    public HelpView(JFrame frame, Theme theme) {
        super(frame, "How To Play", true);

        setSize(TUTORIAL_WIDTH, TUTORIAL_HEIGHT);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        String tutorialFile = loadTutorialFile();

        JTextArea textArea = new JTextArea(tutorialFile);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        ViewHelper.setTheme(textArea, theme);

        JScrollPane scrollPane = new JScrollPane(textArea);
        ViewHelper.setTheme(scrollPane, theme);

        JButton closeButton = new JButton("Close");
        ViewHelper.setTheme(closeButton, theme, ViewHelper.BUTTON);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        ViewHelper.setTheme(buttonPanel, theme);
        buttonPanel.add(closeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String loadTutorialFile() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = getClass().getClassLoader().
                    getResourceAsStream(TUTORIAL_FILE);

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
        catch (IOException e) {
            return "Error loading tutorial file!";
        }

        return stringBuilder.toString();
    }
}
