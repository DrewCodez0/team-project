package app;

import javax.swing.JFrame;

public class Main {
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
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
