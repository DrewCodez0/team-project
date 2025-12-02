package entity;

public enum ThemeType {
    LIGHT(new LightTheme()),
    DARK(new DarkTheme()),
    SUS(new SusTheme());

    private final Theme theme;
    ThemeType(Theme theme) { this.theme = theme; }

    public Theme getTheme() { return theme; }
}
