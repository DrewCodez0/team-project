package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
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
