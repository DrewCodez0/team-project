package use_case.stats;

import data_access.InMemoryStatsDataAccessObject;
import entity.Stats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class StatsInteractorTest {

    @Test
    void successExecuteTest() {
        StatsInputData inputData = new StatsInputData();
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // test stats
        Stats testStats = new Stats(10, 7, 3, 5);
        statsRepository.saveStats(testStats);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(10, outputData.getStats().getGamesPlayed());
                assertEquals(7, outputData.getStats().getWins());
                assertEquals(3, outputData.getStats().getCurrentStreak());
                assertEquals(5, outputData.getStats().getMaxStreak());
                assertNotNull(outputData.getTheme());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successPrepareStartViewTest() {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareStartView() {
                assertTrue(true);
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.prepareStartView();
    }

    @Test
    void successExportStatsTest() {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // test stats
        Stats testStats = new Stats(10, 7, 3, 5);
        statsRepository.saveStats(testStats);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                assertTrue(message.contains("Stats exported successfully"));
                assertTrue(message.contains("Downloads"));
                assertTrue(message.contains("stats.csv"));
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("Use case export failure is unexpected.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.exportStats();
    }

    @Test
    void failureExportStatsNoStatsTest() {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                fail("prepareFailView should not be called for export.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("Use case export success is unexpected.");
            }

            @Override
            public void prepareExportFailView(String message) {
                assertTrue(message.contains("Error exporting stats"));
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.exportStats();
    }

    @Test
    void successImportStatsTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("test_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("15,12,4,6\n");
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(15, outputData.getStats().getGamesPlayed());
                assertEquals(12, outputData.getStats().getWins());
                assertEquals(4, outputData.getStats().getCurrentStreak());
                assertEquals(6, outputData.getStats().getMaxStreak());
                assertNotNull(outputData.getTheme());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case import failure is unexpected.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void failureImportStatsEmptyFileTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("empty_stats.csv").toFile();
        importFile.createNewFile();

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case import success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertTrue(message.contains("Error importing stats"));
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void failureImportStatsInvalidFormatTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("invalid_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");

        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case import success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertTrue(message.contains("Error importing stats"));
                assertTrue(message.contains("File is empty or has only one line") || 
                          message.contains("Invalid file format"));
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void failureImportStatsInsufficientColumnsTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("insufficient_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("15,12,4\n"); // Only 3 values instead of 4
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case import success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertTrue(message.contains("Error importing stats"));
                assertTrue(message.contains("Invalid file format"));
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void failureImportStatsInvalidNumberFormatTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("invalid_number_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("not_a_number,12,4,6\n"); // Invalid number format
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case import success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertTrue(message.contains("Error importing stats"));
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.importStats(importInputData);
    }

    // Edge Case Tests

    @Test
    void successExecuteWithZeroStatsTest() {
        StatsInputData inputData = new StatsInputData();
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // Set up stats with all zeros
        Stats testStats = new Stats(0, 0, 0, 0);
        statsRepository.saveStats(testStats);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(0, outputData.getStats().getGamesPlayed());
                assertEquals(0, outputData.getStats().getWins());
                assertEquals(0, outputData.getStats().getCurrentStreak());
                assertEquals(0, outputData.getStats().getMaxStreak());
                assertNotNull(outputData.getTheme());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successExecuteWithDefaultEmptyStatsTest() {
        StatsInputData inputData = new StatsInputData();
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        // Don't save any stats - should return default empty stats

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(0, outputData.getStats().getGamesPlayed());
                assertEquals(0, outputData.getStats().getWins());
                assertEquals(0, outputData.getStats().getCurrentStreak());
                assertEquals(0, outputData.getStats().getMaxStreak());
                assertNotNull(outputData.getTheme());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureImportStatsEmptyStringValuesTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        File importFile = tempDir.resolve("empty_values_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("15,,4,6\n"); // Empty wins value
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case import success is unexpected with empty values.");
            }

            @Override
            public void prepareFailView(String message) {
                assertTrue(message.contains("Error importing stats"));
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void successImportStatsWithExtraColumnsTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // Create a file with extra columns (more than 4)
        File importFile = tempDir.resolve("extra_columns_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak,extra1,extra2\n");
            writer.write("15,12,4,6,100,200\n"); // 6 values instead of 4
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        // Note: Current implementation only checks < 4, so 5+ columns will pass
        // It only uses the first 4 values, ignoring extras
        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(15, outputData.getStats().getGamesPlayed());
                assertEquals(12, outputData.getStats().getWins());
                assertEquals(4, outputData.getStats().getCurrentStreak());
                assertEquals(6, outputData.getStats().getMaxStreak());
                // Extra columns are ignored
            }

            @Override
            public void prepareFailView(String message) {
                fail("Extra columns should be ignored, not cause failure.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void successImportStatsWithTrailingNewlinesTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // Create a file with trailing blank lines
        File importFile = tempDir.resolve("trailing_newlines_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("15,12,4,6\n");
            writer.write("\n"); // Trailing blank line
            writer.write("\n"); // Another blank line
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        // Current implementation only reads first data line, so trailing newlines are ignored
        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(15, outputData.getStats().getGamesPlayed());
                assertEquals(12, outputData.getStats().getWins());
                assertEquals(4, outputData.getStats().getCurrentStreak());
                assertEquals(6, outputData.getStats().getMaxStreak());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Trailing newlines should be ignored.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.importStats(importInputData);
    }

    @Test
    void successImportStatsWithLargeNumbersTest(@TempDir Path tempDir) throws IOException {
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();
        
        // Create a file with large but valid integer values
        File importFile = tempDir.resolve("large_numbers_stats.csv").toFile();
        try (FileWriter writer = new FileWriter(importFile)) {
            writer.write("gamesPlayed,wins,currentStreak,maxStreak\n");
            writer.write("1000000,500000,1000,2000\n"); // Large numbers
        }

        StatsImportInputData importInputData = new StatsImportInputData(importFile);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertEquals(1000000, outputData.getStats().getGamesPlayed());
                assertEquals(500000, outputData.getStats().getWins());
                assertEquals(1000, outputData.getStats().getCurrentStreak());
                assertEquals(2000, outputData.getStats().getMaxStreak());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Large valid numbers should be accepted.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareExportSuccessView(String message) {
                fail("prepareExportSuccessView should not be called.");
            }

            @Override
            public void prepareExportFailView(String message) {
                fail("prepareExportFailView should not be called.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.importStats(importInputData);
    }
}
