package use_case.stats;

import java.io.File;

public class StatsImportInputData {
    private final File file;

    public StatsImportInputData(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }
}
