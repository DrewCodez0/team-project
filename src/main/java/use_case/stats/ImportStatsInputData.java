package use_case.stats;

/**
 * Input data for the import stats use case.
 */
public class ImportStatsInputData {
    private final String path;

    /**
     * Constructs a new ImportStatsInputData.
     * @param path The file path to import stats from.
     */
    public ImportStatsInputData(String path) {
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
