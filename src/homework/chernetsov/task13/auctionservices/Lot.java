package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.InvalidBetTime;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Lot {
    private BigDecimal currentValue;
    private Participant currentParticipant;
    private final ZonedDateTime endTime;

    public Lot(BigDecimal currentValue, Participant currentParticipant, ZonedDateTime endTime) {
        if (endTime.isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Sorry, a new lot that is already closed doesn't make sense");
        }
        if (currentValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sorry, the cost must be greater than zero");
        }
        this.currentValue = currentValue;
        this.currentParticipant = currentParticipant;
        this.endTime = endTime;
    }

    public Lot(BigDecimal currentValue, ZonedDateTime endTime) {
        this(currentValue, null, endTime);
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public Participant getCurrentParticipant() {
        return currentParticipant;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Participant getWinner() {
        return ZonedDateTime.now().isBefore(endTime) ? null : currentParticipant;
    }

    public boolean placeBet(Participant participant, BigDecimal value) throws InvalidBetTime {
        ZonedDateTime betTime = ZonedDateTime.now();
        if (betTime.isAfter(endTime)) {
            throw new InvalidBetTime(betTime, endTime);
        }
        if (value.compareTo(currentValue) > 0) {
            currentValue = value;
            currentParticipant = participant;
            return true;
        }
        return false;
    }
}
