package data_access;

import interface_adapter.options.OptionsState;

public class SettingsStore {
    private static OptionsState current = new OptionsState();

    public static void save(OptionsState state) {
        current = state;
        System.out.println("[SettingsStore] SAVED → " + current);
    }

    public static OptionsState get() {
        return current;
    }
}