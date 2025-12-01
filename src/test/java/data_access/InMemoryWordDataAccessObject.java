package data_access;

import use_case.game.GameDataAccessInterface;

import java.util.regex.Pattern;

public class InMemoryWordDataAccessObject implements GameDataAccessInterface {
    public static final String CORRECT_CHAR = "A";
    public static final String WRONG_CHAR = "B";
    public static final String CRASH_TEXT = "Crash requested";

    @Override
    public boolean isValidWord(String word, Language language) {
        return Pattern.matches(".*" + CORRECT_CHAR + "+.*", word);
    }

    @Override
    public String getRandomWord(int length, Language language) {
        return InMemoryWordDataAccessObject.getRandomWordStatic(length);
    }

    private static String getRandomWordStatic(int length) {
        if (length == -1) {
            throw new WordNotFoundException(CRASH_TEXT, false);
        } else {
            return new String(new char[length]).replace("\0", CORRECT_CHAR);
        }
    }

    public static String getCorrectWord(int length) {
        return getRandomWordStatic(length);
    }

    public static String getWrongWord(int length) {
        return new String(new char[length - 1]).replace("\0", WRONG_CHAR).concat(CORRECT_CHAR);
    }

    public static String getInvalidWord(int length) {
        return new String(new char[length]).replace("\0", WRONG_CHAR);
    }
}
