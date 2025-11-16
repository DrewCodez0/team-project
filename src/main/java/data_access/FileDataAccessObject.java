package data_access;

import entity.Theme;
import interface_adapter.options.OptionsState;
import interface_adapter.stats.StatsState;
import use_case.end.EndDataAccessInterface;
import use_case.options.OptionsDataAccessInterface;
import use_case.stats.StatsDataAccessInterface;
import use_case.start.StartDataAccessInterface;

public class FileDataAccessObject implements OptionsDataAccessInterface,
        StatsDataAccessInterface, StartDataAccessInterface, EndDataAccessInterface {
    @Override
    public OptionsState getOptions() {
        return new OptionsState();
    }

    @Override
    public void saveOptions(OptionsState options) {}

    @Override
    public StatsState getStats() {
        return new StatsState();
    }

    @Override
    public void saveStats(StatsState stats) {}

    @Override
    public Theme getDefaultTheme() {
        return getOptions().getTheme();
    }
}
