package homework.chernetsov.task13.auction;

import homework.chernetsov.task13.auctionservices.Lot;
import homework.chernetsov.task13.auctionservices.Participant;
import homework.chernetsov.task13.exceptions.InvalidBetTime;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Auction {
    private Lot currentLot;
    private ZonedDateTime endTime;

    public Auction(Lot currentLot, ZonedDateTime endTime) {
        setCurrentLot(currentLot);
        setEndTime(endTime);
    }

    public Participant getWinner() {
        return ZonedDateTime.now().isBefore(getEndTime()) ? null : getCurrentParticipant();
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
                currentLot.setCurrentValue(value);
                currentLot.setCurrentParticipant(participant);
                return true;
            }
            return false;
        }
    }

    public BigDecimal getCurrentValue() {
        return currentLot.getCurrentValue();
    }

    public Participant getCurrentParticipant() {
        return currentLot.getCurrentParticipant();
    }

    public String getLotName() {
        return currentLot.getName();
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }


    public void setEndTime(ZonedDateTime endTime) {
        if (endTime.isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Sorry, a new lot that is already closed doesn't make sense");
        }
        this.endTime = endTime;
    }

    public void setCurrentLot(Lot currentLot) {
        Objects.requireNonNull(currentLot);
        this.currentLot = currentLot;
    }
}
