package entity;

public class Letter extends AbstractLetter {
    private Status status;
    private Character c;

    public Letter(char c) {
        this.c = c;
        this.status = Status.INITIAL;
    }

    public Letter() {
        this.c = ' ';
        this.status = Status.INITIAL;
    }

    @Override
    public char getCharacter() {
        return this.c;
    }

    @Override
    public void setCharacter(char c) {
        this.c = c;
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
        return c.equals(' ');
    }
}
