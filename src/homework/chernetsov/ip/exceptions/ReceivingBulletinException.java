package homework.chernetsov.ip.exceptions;

public class ReceivingBulletinException extends Exception {
    private final boolean isBelongToPrecinct;
    private final boolean isAlreadyReceivedBulletin;

    public ReceivingBulletinException(boolean isBelongToPrecinct, boolean isAlreadyReceivedBulletin) {
        this.isBelongToPrecinct = isBelongToPrecinct;
        this.isAlreadyReceivedBulletin = isAlreadyReceivedBulletin;
    }

    public boolean isBelongToPrecinct() {
        return isBelongToPrecinct;
    }

    public boolean isAlreadyReceivedBulletin() {
        return isAlreadyReceivedBulletin;
    }


    @Override
    public String toString() {
        return "ReceivingBulletinException{" +
                "isBelongToPrecinct=" + isBelongToPrecinct +
                ", isAlreadyReceivedBulletin=" + isAlreadyReceivedBulletin;
    }
}
