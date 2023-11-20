package homework.chernetsov.task13;

import homework.chernetsov.task13.auctionservices.Lot;
import homework.chernetsov.task13.auctionservices.Participant;
import homework.chernetsov.task13.exceptions.InvalidBetTime;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Auction {
    private Lot currentLot;
    private final String lotName;

    public Auction(Lot currentLot, String lotName) {
        Objects.requireNonNull(currentLot);
        if (lotName == null || lotName.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the дot name can't be empty");
        }
        this.currentLot = currentLot;
        this.lotName = lotName;
    }

    synchronized public BigDecimal getCurrentValue() {
        return currentLot.getCurrentValue();
    }

    synchronized public Participant getWinner() {
        return currentLot.getWinner();
    }

    synchronized public boolean placeBet(Participant participant, BigDecimal value) throws InvalidBetTime {
        return currentLot.placeBet(participant, value);
    }

    synchronized public boolean isOver() {
        return currentLot.getEndTime().isBefore(ZonedDateTime.now());
    }


    public Lot getCurrentLot() {
        return currentLot;
    }

    public void setCurrentLot(Lot currentLot) {
        Objects.requireNonNull(currentLot);
        this.currentLot = currentLot;
    }
}
