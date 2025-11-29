package data_access;

import entity.WordFactory;

public class DebugWordGenerator implements WordGenerator {
    @Override
    public String getRandomWord(int length, Language language) {
        return WordFactory.DEFAULT_WORD;
    }
}
