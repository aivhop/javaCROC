package homework.chernetsov.task13;

import homework.chernetsov.task13.auction.Auction;
import homework.chernetsov.task13.auctionservices.Participant;

import java.io.PrintStream;
import java.math.BigDecimal;

import java.util.Objects;
import java.util.Random;

public class ParticipantAuction implements Runnable {
    private final Participant participant;
    private final int numberAttempts;
    private final Auction auction;

    private final PrintStream out;

    public ParticipantAuction(Auction auction, Participant participant, int numberAttempts, int attemptsLessThan, PrintStream out) {
        Objects.requireNonNull(participant);
        Objects.requireNonNull(auction);
        Objects.requireNonNull(out);
        if (numberAttempts <= 0 || numberAttempts >= attemptsLessThan) {
            throw new IllegalArgumentException("Sorry, the number of attempts should be positive and less " + attemptsLessThan);
        }
        this.participant = participant;
        this.numberAttempts = numberAttempts;
        this.auction = auction;
        this.out = out;
    }

    public ParticipantAuction(Auction auction, Participant participant, int numberAttempts, int attemptsLessThan) {
        this(auction, participant, numberAttempts, attemptsLessThan, System.out);
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public void run() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberAttempts; i++) {
            BigDecimal bet = new BigDecimal(Math.abs(random.nextInt()));
            StringBuffer info = new StringBuffer(participant + " try: ");
            BigDecimal currentValue = auction.getCurrentValue();
            boolean placeBet;
            try {
                placeBet = auction.placeBet(participant, bet);
            } catch (Exception ex) {
                System.out.println("end time, participant " + participant);
                break;
            }
            Participant winner = auction.getWinner();
            info.append(" Current value ")
                    .append(currentValue)
                    .append(" placeBet() ")
                    .append(placeBet)
                    .append(" try bet: ")
                    .append(bet)
                    .append(" getWinner() ") //always null because while auction in progress nobody win
                    .append(winner);
            out.println(info);
        }
    }
}
