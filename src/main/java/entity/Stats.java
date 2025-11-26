package entity;

public class Stats {

    private int gamesPlayed;
    private int wins;
    private int currentStreak;
    private int maxStreak;

    public Stats() {
        this.gamesPlayed = 0;
        this.wins = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
    }

    public Stats(int gamesPlayed, int wins, int currentStreak, int maxStreak) {
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public double getWinPercentage() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return ((double) wins / gamesPlayed) * 100;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void recordWin() {
        gamesPlayed++;
        wins++;
        currentStreak++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
    }

    public void recordLoss() {
        gamesPlayed++;
        currentStreak = 0;
    }
}
