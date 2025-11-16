package use_case.start;

import entity.Theme;
import interface_adapter.game.GameState;

/**
 * The output boundary for the Start Use Case.
 */
public interface StartOutputBoundary {
    /**
     * Prepares the start view for the Start Use Case
     * @param theme the Theme to use for the panel
     */
    void prepareStartView(Theme theme);

    /**
     * Prepares the game view for the Start Use Case.
     */
    void prepareGameView(GameState gameState);

    /**
     * Prepares the options view for the Start Use Case.
     */
    void prepareOptionsView();

    /**
     * Prepares the stats view for the Start Use Case.
     */
    void prepareStatsView();
}
