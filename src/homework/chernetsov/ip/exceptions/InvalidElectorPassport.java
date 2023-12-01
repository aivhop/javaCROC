package homework.chernetsov.ip.exceptions;

/**
 * Exception thrown to indicate that an elector's passport series and number are invalid.
 */
public class InvalidElectorPassport extends RuntimeException {
    /**
     * The invalid passport series and number.
     */
    private final String inputPassport;
    /**
     * The message describing the reason for the exception.
     */
    private final String message;

    /**
     * Constructs an InvalidElectorPassport with the specified invalid passport series and number and a message.
     *
     * @param inputPassport The invalid passport series and number.
     * @param message       The message describing the reason for the exception.
     */
    public InvalidElectorPassport(String inputPassport, String message) {
        this.inputPassport = inputPassport;
        this.message = message;
    }

    /**
     * Gets the invalid passport series and number.
     *
     * @return The invalid passport series and number.
     */
    public String getInputPassport() {
        return inputPassport;
    }

    /**
     * Returns a string representation of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String toString() {
        return "Sorry, incorrect passport series and number:" + inputPassport + '\n' + message;
    }
}
