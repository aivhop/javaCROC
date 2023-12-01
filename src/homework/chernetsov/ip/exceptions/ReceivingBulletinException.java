package homework.chernetsov.ip.exceptions;

/**
 * Exception thrown to indicate an issue while receiving a bulletin.
 */
public class ReceivingBulletinException extends Exception {
    /**
     * Indicates whether the elector is registered on the precinct.
     */
    private final boolean isElectorRegisteredOnPrecinct;
    /**
     * Indicates whether the bulletin has already been received by the elector.
     */
    private final boolean bulletinHasBeenReceived;

    /**
     * Constructs a ReceivingBulletinException with the specified parameters.
     *
     * @param isElectorRegisteredOnPrecinct Whether the elector is registered on the precinct.
     * @param bulletinHasBeenReceived       Whether the bulletin has already been received by the elector.
     */
    public ReceivingBulletinException(boolean isElectorRegisteredOnPrecinct, boolean bulletinHasBeenReceived) {
        this.isElectorRegisteredOnPrecinct = isElectorRegisteredOnPrecinct;
        this.bulletinHasBeenReceived = bulletinHasBeenReceived;
    }

    /**
     * Gets whether the elector is registered on the precinct.
     *
     * @return True if the elector is registered on the precinct, false otherwise.
     */
    public boolean isElectorRegisteredOnPrecinct() {
        return isElectorRegisteredOnPrecinct;
    }

    /**
     * Gets whether the bulletin has already been received by the elector.
     *
     * @return True if the bulletin has already been received, false otherwise.
     */
    public boolean isBulletinHasBeenReceived() {
        return bulletinHasBeenReceived;
    }

    /**
     * Returns a message of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String getMessage() {
        return this.toString();
    }

    /**
     * Returns a string representation of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String toString() {
        return "ReceivingBulletinException{" +
                "isElectorRegisteredOnPrecinct=" + isElectorRegisteredOnPrecinct +
                ", bulletinHasBeenReceived=" + bulletinHasBeenReceived +
                '}';
    }
}
