package homework.chernetsov.ip.exceptions;

/**
 * Exception thrown to indicate that a resource file name is invalid.
 */
public class InvalidResourceFileName extends RuntimeException {
    /**
     * The correct format expected for the file name.
     */
    private String correctFormat;
    /**
     * The input file name that caused the exception.
     */
    private String inputFileName;

    /**
     * Constructs an InvalidResourceFileName with the specified input file name and correct format.
     *
     * @param inputFileName The input file name that caused the exception.
     * @param correctFormat The correct format expected for the file name.
     */
    public InvalidResourceFileName(String inputFileName, String correctFormat) {
        this.inputFileName = inputFileName;
        this.correctFormat = correctFormat;
    }

    /**
     * Constructs an InvalidResourceFileName with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public InvalidResourceFileName(Throwable cause) {
        super(cause);
    }

    /**
     * Gets the correct format expected for the file name.
     *
     * @return The correct format for the file name.
     */
    public String getCorrectFormat() {
        return correctFormat;
    }

    /**
     * Gets the input file name that caused the exception.
     *
     * @return The input file name.
     */
    public String getInputFileName() {
        return inputFileName;
    }

    /**
     * Returns a message of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String getMessage() {
        return inputFileName == null ? super.getMessage() : this.toString();
    }

    /**
     * Returns a string representation of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String toString() {
        return "Sorry, a format file is needed: " + correctFormat +
                ", your file name: " + inputFileName;
    }
}

