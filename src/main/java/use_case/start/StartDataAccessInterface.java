package use_case.start;

import entity.Theme;

/**
 * DAO interface for the Start Use Case.
 */
public interface StartDataAccessInterface {
    /**
     * Returns the default theme.
     * @return the default theme
     */
    Theme getDefaultTheme();
}
