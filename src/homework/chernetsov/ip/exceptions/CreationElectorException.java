package homework.chernetsov.ip.exceptions;

import homework.chernetsov.ip.dbentity.Elector;

public class CreationElectorException extends RuntimeException {
    private final Elector elector;

    public CreationElectorException(String message, Elector elector) {
        super(message);
        this.elector = elector;
    }

    public Elector getElector() {
        return elector;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": " + elector;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
