package use_case.end;

public interface EndDataAccessInterface {
    /**
     * Saves a game record to persistent storage
     * @param record the game record to save
     */
    void saveGameRecord(EndGameRecord record);

    /**
     * Gets all saved game records
     * @return array of all game records
     */
    EndGameRecord[] getGameRecords();
}
