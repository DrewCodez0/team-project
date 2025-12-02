package use_case.end;

import entity.AbstractWord;
import entity.WordFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndDataClassesTest {

    // ==================== EndInputData Tests ====================

    @Test
    void testEndInputDataGetters() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);

        assertEquals("HELLO", inputData.getWord());
        assertTrue(inputData.isWon());
        assertEquals(3, inputData.getGuessesUsed());
        assertEquals(6, inputData.getMaxGuesses());
        assertArrayEquals(guessHistory, inputData.getGuessHistory());
    }

    @Test
    void testEndInputDataEqualsReflexive() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);

        assertEquals(inputData, inputData);
    }

    @Test
    void testEndInputDataEqualsSymmetric() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", true, 3, 6, guessHistory2);

        assertEquals(inputData1, inputData2);
        assertEquals(inputData2, inputData1);
    }

    @Test
    void testEndInputDataNotEqualsNull() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);

        assertNotEquals(inputData, null);
    }

    @Test
    void testEndInputDataNotEqualsDifferentType() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);

        assertNotEquals(inputData, "Not an EndInputData");
    }

    @Test
    void testEndInputDataNotEqualsDifferentWord() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("WORLD", true, 3, 6, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    @Test
    void testEndInputDataNotEqualsDifferentWon() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", false, 3, 6, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    @Test
    void testEndInputDataNotEqualsDifferentGuessesUsed() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", true, 4, 6, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    @Test
    void testEndInputDataNotEqualsDifferentMaxGuesses() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[8];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
        }
        for (int i = 0; i < 8; i++) {
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", true, 3, 8, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    @Test
    void testEndInputDataNotEqualsDifferentGuessHistoryLength() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[8];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
        }
        for (int i = 0; i < 8; i++) {
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", true, 3, 6, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    @Test
    void testEndInputDataNotEqualsDifferentGuessHistoryContent() {
        final AbstractWord[] guessHistory1 = new AbstractWord[6];
        final AbstractWord[] guessHistory2 = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory1[i] = WordFactory.createEmptyWord(5);
            guessHistory2[i] = WordFactory.createEmptyWord(5);
        }
        guessHistory2[0] = WordFactory.createWord("HELLO");

        final EndInputData inputData1 = new EndInputData("HELLO", true, 3, 6, guessHistory1);
        final EndInputData inputData2 = new EndInputData("HELLO", true, 3, 6, guessHistory2);

        assertNotEquals(inputData1, inputData2);
    }

    // ==================== EndOutputData Tests ====================

    @Test
    void testEndOutputDataWinFirstTry() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndOutputData outputData = new EndOutputData("HELLO", true, 1, 6, guessHistory);

        assertEquals("HELLO", outputData.getWord());
        assertTrue(outputData.isWon());
        assertEquals(1, outputData.getGuessesUsed());
        assertEquals(6, outputData.getMaxGuesses());
        assertNotNull(outputData.getMessage());
        assertTrue(outputData.getMessage().contains("First try!"));
        assertTrue(outputData.getMessage().contains("HELLO"));
        assertArrayEquals(guessHistory, outputData.getGuessHistory());
    }

    @Test
    void testEndOutputDataWinLastGuess() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndOutputData outputData = new EndOutputData("HELLO", true, 6, 6, guessHistory);

        assertEquals("HELLO", outputData.getWord());
        assertTrue(outputData.isWon());
        assertEquals(6, outputData.getGuessesUsed());
        assertEquals(6, outputData.getMaxGuesses());
        assertNotNull(outputData.getMessage());
        assertTrue(outputData.getMessage().contains("That was close!"));
        assertTrue(outputData.getMessage().contains("HELLO"));
    }

    @Test
    void testEndOutputDataWinMiddleGuess() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndOutputData outputData = new EndOutputData("HELLO", true, 3, 6, guessHistory);

        assertEquals("HELLO", outputData.getWord());
        assertTrue(outputData.isWon());
        assertEquals(3, outputData.getGuessesUsed());
        assertEquals(6, outputData.getMaxGuesses());
        assertNotNull(outputData.getMessage());
        assertFalse(outputData.getMessage().contains("First try!"));
        assertFalse(outputData.getMessage().contains("That was close!"));
        assertTrue(outputData.getMessage().contains("HELLO"));
    }

    @Test
    void testEndOutputDataLoss() {
        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndOutputData outputData = new EndOutputData("WORLD", false, 6, 6, guessHistory);

        assertEquals("WORLD", outputData.getWord());
        assertFalse(outputData.isWon());
        assertEquals(6, outputData.getGuessesUsed());
        assertEquals(6, outputData.getMaxGuesses());
        assertNotNull(outputData.getMessage());
        assertEquals("The word was: WORLD", outputData.getMessage());
        assertArrayEquals(guessHistory, outputData.getGuessHistory());
    }

    // ==================== EndGameRecord Tests ====================

    @Test
    void testEndGameRecordGetters() {
        final EndGameRecord record = new EndGameRecord("HELLO", true, 3);

        assertEquals("HELLO", record.getWord());
        assertTrue(record.isWon());
        assertEquals(3, record.getGuessesUsed());
    }

    @Test
    void testEndGameRecordToString() {
        final EndGameRecord record = new EndGameRecord("HELLO", true, 3);

        final String expected = "EndGameRecord{word=HELLO, won=true, guessesUsed=3}";
        assertEquals(expected, record.toString());
    }

    @Test
    void testEndGameRecordEqualsReflexive() {
        final EndGameRecord record = new EndGameRecord("HELLO", true, 3);

        assertEquals(record, record);
    }

    @Test
    void testEndGameRecordEqualsSymmetric() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("HELLO", true, 3);

        assertEquals(record1, record2);
        assertEquals(record2, record1);
    }

    @Test
    void testEndGameRecordNotEqualsNull() {
        final EndGameRecord record = new EndGameRecord("HELLO", true, 3);

        assertNotEquals(record, null);
    }

    @Test
    void testEndGameRecordNotEqualsDifferentType() {
        final EndGameRecord record = new EndGameRecord("HELLO", true, 3);

        assertNotEquals(record, "Not an EndGameRecord");
    }

    @Test
    void testEndGameRecordNotEqualsDifferentWord() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("WORLD", true, 3);

        assertNotEquals(record1, record2);
    }

    @Test
    void testEndGameRecordNotEqualsDifferentWon() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("HELLO", false, 3);

        assertNotEquals(record1, record2);
    }

    @Test
    void testEndGameRecordNotEqualsDifferentGuessesUsed() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("HELLO", true, 4);

        assertNotEquals(record1, record2);
    }

    @Test
    void testEndGameRecordHashCodeConsistency() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("HELLO", true, 3);

        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    void testEndGameRecordHashCodeDifferent() {
        final EndGameRecord record1 = new EndGameRecord("HELLO", true, 3);
        final EndGameRecord record2 = new EndGameRecord("WORLD", true, 3);

        assertNotEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    void testEndGameRecordLossScenario() {
        final EndGameRecord record = new EndGameRecord("TESTS", false, 6);

        assertEquals("TESTS", record.getWord());
        assertFalse(record.isWon());
        assertEquals(6, record.getGuessesUsed());
        assertEquals("EndGameRecord{word=TESTS, won=false, guessesUsed=6}", record.toString());
    }
}