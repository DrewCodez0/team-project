package interface_adapter.stats;


import entity.Theme;

import java.util.ArrayList;
import java.util.List;

public class StatsState {
    private int totalGames;
    private int gamesWon;
    private int currentWinStreak;
    private int longestWinStreak;
    private Theme theme;

    public StatsState(){
        this.totalGames = 0;
        this.gamesWon = 0;
        this.currentWinStreak = 0;
        this.longestWinStreak = 0;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getCurrentWinStreak() {
        return currentWinStreak;
    }

    public void setCurrentWinStreak(int currentWinStreak) {
        this.currentWinStreak = currentWinStreak;
    }

    public int getLongestWinStreak() {
        return longestWinStreak;
    }

    public void setLongestWinStreak(int longestWinStreak) {
        this.longestWinStreak = longestWinStreak;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
