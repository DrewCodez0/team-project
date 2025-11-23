package entity;

public class Word extends AbstractWord {
    private final AbstractLetter[] letters;

    public Word(String word) {
        letters = new AbstractLetter[word.length()];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new Letter(word.charAt(i)); // TODO replace this dependence with the wordfactory
        }
    }

    public Word(int length) {
        letters = new AbstractLetter[length];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new Letter();
        }
    }

    @Override
    public AbstractLetter getLetter(int index) {
        return letters[index];
    }

    @Override
    public int length() {
        return letters.length;
    }

    @Override
    public boolean isEmpty() {
        for (AbstractLetter letter : letters) {
            if (!letter.isNull()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFull() {
        for (AbstractLetter letter : letters) {
            if (letter.isNull()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCorrect() {
        for (AbstractLetter letter : letters) {
            if (letter.getStatus() != Status.CORRECT) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (AbstractLetter letter : letters) {
            result.append(letter.toString());
        }
        return result.toString();
    }
}
