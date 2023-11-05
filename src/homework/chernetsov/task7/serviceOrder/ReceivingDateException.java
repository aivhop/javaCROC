package homework.chernetsov.task7.serviceOrder;

import java.time.Duration;
import java.time.ZonedDateTime;


public class ReceivingDateException extends Exception {
    private final ZonedDateTime storageTime;
    private final ZonedDateTime receivingTime;

    public ReceivingDateException(ZonedDateTime storageTime, ZonedDateTime receivingTime) {
        this.storageTime = storageTime;
        this.receivingTime = receivingTime;
    }

    @Override
    public String getMessage() {
        Duration duration = Duration.between(storageTime,receivingTime);
        return "The storage period has expired on " +
                duration.toHours() + "h " +
                duration.toMinutesPart() + "m " +
                duration.toSecondsPart() + "s";
    }
}
