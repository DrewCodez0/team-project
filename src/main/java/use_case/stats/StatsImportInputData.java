package use_case.stats;

public class StatsImportInputData extends ImportStatsInputData {
    private final String filePath;

    public StatsImportInputData(String filePath) {
        super(filePath);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
