package entity;

public abstract class AbstractLetter {
    public abstract char getCharacter();

    public abstract void setCharacter(char c);

    public abstract void resetCharacter();

    public abstract Status getStatus();

    public abstract void setStatus(Status status);

    public abstract boolean isNull();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractLetter)) {
            return false;
        }
        AbstractLetter letter = (AbstractLetter) obj;
        return (this.getCharacter() == letter.getCharacter()) && (this.getStatus() == letter.getStatus());
    }

    @Override
    public String toString() {
        Character character = this.getCharacter();
        return character.toString();
    }
}
