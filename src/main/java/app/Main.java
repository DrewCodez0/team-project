package app;

import javax.swing.JFrame;

public class Main {
    /**
     * The main method that sets up and runs the application.
     * It uses an AppBuilder to construct the GUI and then makes the main window visible.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addStartView()
                .addEndView()
                .addOptionsView()
                .addGameView()
                .addStatsView()
                .addStartUseCase()
                .addGameUseCase()
                .addEndUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
