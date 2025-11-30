package use_case.stats;

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
    public void exportStats(ExportStatsInputData exportInputData) {
        try {
            statsDataAccess.exportStats(exportInputData.getPath());
            statsOutputBoundary.prepareExportSuccessView("Stats exported successfully to " + exportInputData.getPath());
        }
        catch (IOException exception) {
            statsOutputBoundary.prepareExportFailView("Error exporting stats: " + exception.getMessage());
        }
    }

    @Override
    public void importStats(StatsImportInputData importInputData) {
        try {
            final Stats newStats = statsDataAccess.importStats(importInputData.getFilePath());
            statsDataAccess.saveStats(newStats);

            final Theme theme = statsDataAccess.getDefaultTheme();
            final StatsOutputData outputData = new StatsOutputData(newStats, theme);
            statsOutputBoundary.prepareSuccessView(outputData);
        }
        catch (IOException exception) {
            statsOutputBoundary.prepareFailView("Error importing stats: " + exception.getMessage());
        }
    }
}
