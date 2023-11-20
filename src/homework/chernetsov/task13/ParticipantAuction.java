package homework.chernetsov.task13;

import homework.chernetsov.task13.auctionservices.Participant;

import java.math.BigDecimal;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Random;

public class ParticipantAuction implements Runnable {
    private static final Object lock = new Object();
    private final Participant participant;
    private final int numberAttempts;
    private final Auction auction;

    public ParticipantAuction(Auction auction, Participant participant, int numberAttempts, int attemptsLessThan) {
        Objects.requireNonNull(participant);
        Objects.requireNonNull(auction);
        if (numberAttempts <= 0 || numberAttempts >= attemptsLessThan) {
            throw new IllegalArgumentException("Sorry, the number of attempts should be positive and less " + attemptsLessThan);
        }
        this.participant = participant;
        this.numberAttempts = numberAttempts;
        this.auction = auction;
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public void run() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberAttempts; i++) {

            BigDecimal bet = new BigDecimal(random.nextInt());
            BigDecimal currentValue = auction.getCurrentValue();
            Participant winner = auction.getWinner();

            String info = participant + " try: ";


            try { //todo((
                System.out.println(new StringBuffer(info)
                        .append(" Current value ")
                        .append(auction.getCurrentValue())
                        .append(" getWinner() ")
                        .append(auction.getWinner())
                        .append(" isOver() ")
                        .append(auction.isOver())
                        .append(" placeBet() ")
                        .append(auction.placeBet(participant, bet)));
                /*System.out.println("Current value " + auction.getCurrentValue());
                System.out.println("getWinner() " + auction.getWinner());
                System.out.println("placeBet() " + auction.placeBet(participant, bet));
                System.out.println("isOver() " + auction.isOver());*/
            } catch (Exception ex) {
                System.out.println("end time, participant " + participant);
                break;
            }

        }
    }
}
