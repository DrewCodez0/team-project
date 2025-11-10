package interface_adapter.options;

import data_access.Language;
import entity.DarkTheme;
import entity.SusTheme;
import entity.Theme;

public class OptionsState {
    public Theme getTheme() {
        return new SusTheme();
    }
    public int getMaxGuesses() {
        return 6;
    }
    public int getLength() {
        return 5;
    }
    public Language getLanguage() {return Language.ENGLISH;}
}
