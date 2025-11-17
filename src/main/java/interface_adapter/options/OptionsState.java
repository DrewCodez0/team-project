package interface_adapter.options;

import data_access.Language;
import entity.DarkTheme;
import entity.LightTheme;
import entity.SusTheme;
import entity.Theme;

public class OptionsState {
    private Theme theme;
    private int maxGuesses;
    private int length;
    private Language language;
    public OptionsState(){
        this.theme = new SusTheme();
        this.maxGuesses = 6;
        this.length = 5;
        this.language = Language.ENGLISH;
    }
    public Theme getTheme() {
        return theme;
    }
    public void setTheme(Theme theme) {this.theme = theme;}
    public int getMaxGuesses() {
        return maxGuesses;
    }
    public void setMaxGuesses(int maxGuesses) {this.maxGuesses = maxGuesses;}
    public int getLength() {
        return length;
    }
    public void setLength(int length) {this.length = length;}
    public Language getLanguage() {return language;}
    public void setLanguage(Language language) {this.language = language;}
}
