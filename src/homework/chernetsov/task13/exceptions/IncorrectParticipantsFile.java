package homework.chernetsov.task13.exceptions;

public class IncorrectParticipantsFile extends Exception{
    public IncorrectParticipantsFile(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParticipantsFile(String message) {
        super(message);
    }
}