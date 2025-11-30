package use_case.stats;

import java.io.IOException;

import entity.Stats;

public interface StatsImportExportDataAccessInterface {
    /**
     * Reads UserStats from a specified file path.
     * @param path The path of the file to read from.
     * @return The UserStats object read from the file.
     * @throws IOException If an error occurs during file reading.
     */
    Stats readStats(String path) throws IOException;

    /**
     * Writes UserStats to a specified file path.
     * @param path The path of the file to write to.
     * @param stats The UserStats object to write.
     * @throws IOException If an error occurs during file writing.
     */
    void writeStats(String path, Stats stats) throws IOException;
}
