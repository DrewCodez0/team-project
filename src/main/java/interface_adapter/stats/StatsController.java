package interface_adapter.stats;

import java.io.File;

import use_case.stats.ExportStatsInputData;
import use_case.stats.StatsImportInputData;
import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInputData;

public class StatsController {
    private final StatsInputBoundary statsInteractor;

    public StatsController(StatsInputBoundary statsInteractor) {
        this.statsInteractor = statsInteractor;
    }

    /**
     * Executes the primary action to load and display statistics.
     */
    public void execute() {
        statsInteractor.execute(new StatsInputData());
    }

    /**
     * Initiates the process of switching back to the start menu view.
     */
    public void switchToStartView() {
        statsInteractor.prepareStartView();
    }

    /**
     * Initiates the process of exporting the stats.
     */
    public void exportStats() {
        final String downloadsPath = System.getProperty("user.home")
                + File.separator + "Downloads" + File.separator + "stats.csv";
        final ExportStatsInputData exportInputData = new ExportStatsInputData(downloadsPath);
        statsInteractor.exportStats(exportInputData);
    }

    /**
     * Initiates the process of importing stats from a file.
     * @param file The file to import from.
     */
    public void importStats(File file) {
        final StatsImportInputData importInputData = new StatsImportInputData(file.getAbsolutePath());
        statsInteractor.importStats(importInputData);
    }
}
