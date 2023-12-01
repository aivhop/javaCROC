package homework.chernetsov.ip.exceptions;

/**
 * Exception thrown to indicate a connection-related issue.
 */
public class ConnectionException extends Exception {
    /**
     * Constructs a ConnectionException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
