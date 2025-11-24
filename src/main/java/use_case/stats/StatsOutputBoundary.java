package use_case.stats;

import entity.Theme;

public interface StatsOutputBoundary {
    /**
     * Prepares the success view for the Stats Use Case.
     * This displays the user's stats
     * @param theme get stats
     */
    void prepareSuccessView(Theme theme);

    /**
     * Prepares the failure view for the Stats Use Case.
     * This executes when the DAO fails to get the stats data.
     */
    void prepareFailView();

    /**
     * Prepares the default view for the Stats Use Case.
     * This executes when the user has not yet played any games.
     */
    void prepareDefaultView();

    /**
     * Prepares the start view for the Stats Use Case.
     * This returns the user to the main menu.
     */
    void prepareStartView();
}
