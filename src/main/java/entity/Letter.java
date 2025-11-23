package entity;

public class Letter extends AbstractLetter {
    private Status status;
    private Character character;

    public Letter(char character) {
        this.character = character;
        this.status = Status.INITIAL;
    }

    public Letter() {
        this.character = ' ';
        this.status = Status.INITIAL;
    }

    @Override
    public char getCharacter() {
        return this.character;
    }

    @Override
    public void setCharacter(char c) {
        this.character = c;
        this.status = Status.IN_PROGRESS;
    }

    @Override
    public void resetCharacter() {
        this.character = ' ';
        this.status = Status.INITIAL;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean isNull() {
        return character.equals(' ');
    }
}
