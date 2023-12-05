package homework.chernetsov.task13.auction;

import homework.chernetsov.task13.auctionservices.Lot;
import homework.chernetsov.task13.auctionservices.Participant;
import homework.chernetsov.task13.exceptions.InvalidBetTime;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Auction {
    volatile private BigDecimal currentValue;
    volatile private Participant currentParticipant;
    private final Lot currentLot;
    private final ZonedDateTime endTime;

    public Auction(Lot currentLot, ZonedDateTime endTime) {
        if (endTime.isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Sorry, a new lot that is already closed doesn't make sense");
        }
        Objects.requireNonNull(currentLot);
        this.endTime = endTime;
        this.currentLot = currentLot;
        this.currentParticipant = null;
        this.currentValue = currentLot.startValue();
    }

    public Participant getWinner() {
        return ZonedDateTime.now().isBefore(getEndTime()) ? null : currentParticipant;
    }

    public boolean placeBet(Participant participant, BigDecimal value) throws InvalidBetTime {
        ZonedDateTime betTime = ZonedDateTime.now();
        if (betTime.isAfter(getEndTime())) {
            throw new InvalidBetTime(betTime, getEndTime());
        }
        if (value.compareTo(getCurrentValue()) <= 0) {
            return false;
        }
        synchronized (this) {
            if (value.compareTo(getCurrentValue()) > 0) {
                setCurrentValue(value);
                setCurrentParticipant(participant);
                return true;
            }
            return false;
        }
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public Participant getCurrentParticipant() {
        return currentParticipant;
    }

    public String getLotName() {
        return currentLot.name();
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public void setCurrentParticipant(Participant currentParticipant) {
        this.currentParticipant = currentParticipant;
    }
}
