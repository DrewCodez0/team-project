package use_case.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import entity.Stats;
import entity.Theme;

public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsOutputBoundary;

    public StatsInteractor(StatsDataAccessInterface statsDataAccess, StatsOutputBoundary statsOutputBoundary) {
        this.statsDataAccess = statsDataAccess;
        this.statsOutputBoundary = statsOutputBoundary;
    }

    @Override
    public void execute(StatsInputData inputData) {
        final Stats stats = statsDataAccess.getStats();
        final Theme theme = statsDataAccess.getDefaultTheme();
        final StatsOutputData outputData = new StatsOutputData(stats, theme);
        statsOutputBoundary.prepareSuccessView(outputData);
    }

    @Override
    public void prepareStartView() {
        statsOutputBoundary.prepareStartView();
    }

    @Override
    public void exportStats() {
        final String downloadsPath = System.getProperty("user.home")
                + File.separator + "Downloads" + File.separator + "stats.csv";
        try {
            statsDataAccess.exportStats(downloadsPath);
            statsOutputBoundary.prepareExportSuccessView("Stats exported successfully to " + downloadsPath);
        }
        catch (IOException exception) {
            statsOutputBoundary.prepareExportFailView("Error exporting stats: " + exception.getMessage());
        }
    }

    @Override
    public void importStats(StatsImportInputData importInputData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(importInputData.getFile()))) {
            reader.readLine();
            final String line = reader.readLine();

            if (line == null) {
                throw new IOException("File is empty or has only one line.");
            }

            final String[] values = line.split(",");
            if (values.length < 4) {
                throw new IOException("Invalid file format.");
            }

            final int gamesPlayed = Integer.parseInt(values[0]);
            final int wins = Integer.parseInt(values[1]);
            final int currentStreak = Integer.parseInt(values[2]);
            final int maxStreak = Integer.parseInt(values[3]);

            final Stats newStats = new Stats(gamesPlayed, wins, currentStreak, maxStreak);
            statsDataAccess.saveStats(newStats);

            final Theme theme = statsDataAccess.getDefaultTheme();
            final StatsOutputData outputData = new StatsOutputData(newStats, theme);
            statsOutputBoundary.prepareSuccessView(outputData);

        }
        catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            statsOutputBoundary.prepareFailView("Error importing stats: " + exception.getMessage());
        }
    }
}
