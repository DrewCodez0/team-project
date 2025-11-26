package interface_adapter.stats;

import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInputData;
import use_case.stats.StatsImportInputData;
import java.io.File;

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
        statsInteractor.exportStats();
    }

    /**
     * Initiates the process of importing stats from a file.
     * @param file The file to import from.
     */
    public void importStats(File file) {
        StatsImportInputData importInputData = new StatsImportInputData(file);
        statsInteractor.importStats(importInputData);
    }
}
