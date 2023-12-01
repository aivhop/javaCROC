package homework.chernetsov.ip.exceptions;

/**
 * Exception thrown to indicate an error while reading electors from a file.
 */
public class ReadElectorsFromFileException extends Exception {
    /**
     * Constructs a ReadElectorsFromFileException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public ReadElectorsFromFileException(Throwable cause) {
        super(cause);
    }
}
