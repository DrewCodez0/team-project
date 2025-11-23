package entity;

public abstract class AbstractLetter {
    /**
     * Returns the character associated with this AbstractLetter.
     * @return the character associated with this AbstractLetter
     */
    public abstract char getCharacter();

    /**
     * Sets the character associated with this AbstractLetter.
     * @param c the character to set
     */
    public abstract void setCharacter(char c);

    /**
     * Resets the character associated with this AbstractLetter to a null state.
     */
    public abstract void resetCharacter();

    /**
     * Returns the status of this AbstractLetter.
     * @return the status of this AbstractLetter
     */
    public abstract Status getStatus();

    /**
     * Sets the status of this AbstractLetter.
     * @param status the status to set
     */
    public abstract void setStatus(Status status);

    /**
     * Checks if this AbstractLetter is in a null state.
     * @return true if this AbstractLetter is in a null state, false otherwise
     */
    public abstract boolean isNull();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractLetter)) {
            return false;
        }
        final AbstractLetter letter = (AbstractLetter) obj;
        return this.getCharacter() == letter.getCharacter() && this.getStatus() == letter.getStatus();
    }

    @Override
    public int hashCode() {
        return Character.hashCode(this.getCharacter());
    }

    @Override
    public String toString() {
        return Character.toString(this.getCharacter());
    }
}
