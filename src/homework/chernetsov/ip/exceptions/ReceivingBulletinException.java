package homework.chernetsov.ip.exceptions;

public class ReceivingBulletinException extends Exception {
    private final boolean isElectorRegisteredOnPrecinct;
    private final boolean bulletinHasBeenReceived;

    public ReceivingBulletinException(boolean isElectorRegisteredOnPrecinct, boolean bulletinHasBeenReceived) {
        this.isElectorRegisteredOnPrecinct = isElectorRegisteredOnPrecinct;
        this.bulletinHasBeenReceived = bulletinHasBeenReceived;
    }

    public boolean isElectorRegisteredOnPrecinct() {
        return isElectorRegisteredOnPrecinct;
    }

    public boolean isBulletinHasBeenReceived() {
        return bulletinHasBeenReceived;
    }

    @Override
    public String toString() {
        return "ReceivingBulletinException{" +
                "isElectorRegisteredOnPrecinct=" + isElectorRegisteredOnPrecinct +
                ", bulletinHasBeenReceived=" + bulletinHasBeenReceived +
                '}';
    }
}
