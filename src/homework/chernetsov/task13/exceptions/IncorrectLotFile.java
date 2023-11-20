package homework.chernetsov.task13.exceptions;

public class IncorrectLotFile extends Exception{
    public IncorrectLotFile(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectLotFile(String message) {
        super(message);
    }
}
