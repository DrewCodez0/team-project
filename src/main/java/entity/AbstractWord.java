package entity;

public abstract class AbstractWord {
    /**
     * Returns the letter at the specified index of the word.
     * @param index the index to retrieve the letter from
     * @return the AbstractLetter corresponding to the letter at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds of the word
     */
    public abstract AbstractLetter getLetter(int index) throws IndexOutOfBoundsException;

    /**
     * Returns the length of this word.
     * @return the length of this word
     */
    public abstract int length();

    /**
     * Check if this word only contains null letters.
     * @return true if this word only contains null letters, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Check if this word only contains non-null letters.
     * @return true if this word only contains non-null letters, false otherwise
     */
    public abstract boolean isFull();

    /**
     * Check if all the letters in this word are correct.
     * @return true if all the letters in this word are in the correct state, false otherwise
     */
    public abstract boolean isCorrect();

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractWord)) {
            return false;
        }
        final AbstractWord word = (AbstractWord) obj;
        if (word.length() != this.length()) {
            return false;
        }
        for (int i = 0; i < this.length(); i++) {
            if (!this.getLetter(i).equals(word.getLetter(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
