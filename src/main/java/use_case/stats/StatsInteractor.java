package use_case.stats;

import entity.Stats;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsOutputBoundary;

    public StatsInteractor(StatsDataAccessInterface statsDataAccess, StatsOutputBoundary statsOutputBoundary) {
        this.statsDataAccess = statsDataAccess;
        this.statsOutputBoundary = statsOutputBoundary;
    }

    @Override
    public void execute(StatsInputData inputData) {
        Stats stats = statsDataAccess.getStats();
        StatsOutputData outputData = new StatsOutputData(stats);
        statsOutputBoundary.prepareSuccessView(outputData);
    }

    @Override
    public void prepareStartView() {
        statsOutputBoundary.prepareStartView();
    }

    @Override
    public void exportStats() {
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "stats.csv";
        try {
            statsDataAccess.exportStats(downloadsPath);
            statsOutputBoundary.prepareExportSuccessView("Stats exported successfully to " + downloadsPath);
        } catch (IOException e) {
            statsOutputBoundary.prepareExportFailView("Error exporting stats: " + e.getMessage());
        }
    }

    @Override
    public void importStats(StatsImportInputData importInputData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(importInputData.getFile()))) {
            reader.readLine(); // Skip the header line
            String line = reader.readLine(); // Read the second line

            if (line == null) {
                throw new IOException("File is empty or has only one line.");
            }

            String[] values = line.split(",");
            if (values.length < 4) {
                throw new IOException("Invalid file format.");
            }

            int gamesPlayed = Integer.parseInt(values[0]);
            int wins = Integer.parseInt(values[1]);
            int currentStreak = Integer.parseInt(values[2]);
            int maxStreak = Integer.parseInt(values[3]);

            Stats newStats = new Stats(gamesPlayed, wins, currentStreak, maxStreak);
            statsDataAccess.saveStats(newStats);

            // Prepare success view
            StatsOutputData outputData = new StatsOutputData(newStats);
            statsOutputBoundary.prepareSuccessView(outputData);

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            statsOutputBoundary.prepareFailView("Error importing stats: " + e.getMessage());
        }
    }
}
