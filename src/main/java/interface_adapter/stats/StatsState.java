package interface_adapter.stats;

public class StatsState {
    private int gamesPlayed;
    private double winPercentage;
    private int currentStreak;
    private int maxStreak;
    private String error;
    private String exportMessage;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getExportMessage() {
        return exportMessage;
    }

    public void setExportMessage(String exportMessage) {
        this.exportMessage = exportMessage;
    }
}
