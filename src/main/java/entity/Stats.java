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

    /**
     * Returns the user's win percentage.
     * @return gets Win rate amount
     */
    public double getWinPercentage() {
        double winRate = 0.0;
        final int percentageMultiplier = 100;
        if (gamesPlayed != 0) {
            winRate = ((double) wins / gamesPlayed) * percentageMultiplier;
        }
        return winRate;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    /**
     * Increments teh amount of wins.
     */
    public void recordWin() {
        gamesPlayed++;
        wins++;
        currentStreak++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
    }

    /**
     * Increments the amount of losses.
     */
    public void recordLoss() {
        gamesPlayed++;
        currentStreak = 0;
    }
}
