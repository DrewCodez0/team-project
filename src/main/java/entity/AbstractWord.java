package entity;

public abstract class AbstractWord {
    public abstract AbstractLetter getLetter(int index) throws IndexOutOfBoundsException;

    public abstract int length();

    public abstract boolean isEmpty();

    public abstract boolean isFull();

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
        AbstractWord word = (AbstractWord) obj;
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
}
