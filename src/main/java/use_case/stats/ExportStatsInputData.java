package use_case.stats;

/**
 * Input data for the export stats use case.
 */
public class ExportStatsInputData {
    private final String path;

    /**
     * Constructs a new ExportStatsInputData.
     * @param path The file path to export stats to.
     */
    public ExportStatsInputData(String path) {
        this.path = path;
    }

    /**
     * Gets the file path.
     * @return The file path.
     */
    String getPath() {
        return path;
    }
}
