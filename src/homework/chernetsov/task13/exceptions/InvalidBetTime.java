package homework.chernetsov.task13.exceptions;

import java.time.Duration;
import java.time.ZonedDateTime;

public class InvalidBetTime extends Exception {
    private final ZonedDateTime betTime;
    private final ZonedDateTime endTime;

    public InvalidBetTime(ZonedDateTime betTime, ZonedDateTime endTime) {
        this.betTime = betTime;
        this.endTime = endTime;
    }

    @Override
    public String getMessage() {
        return "Sorry, the auction for the lot is over, you are late for: " +
                Duration.between(endTime, betTime).toMinutes() + "min";
    }
}
