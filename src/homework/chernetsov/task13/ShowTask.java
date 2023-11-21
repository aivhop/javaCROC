package homework.chernetsov.task13;

import homework.chernetsov.task13.auction.Auction;
import homework.chernetsov.task13.auctionservices.Lot;
import homework.chernetsov.task13.auctionservices.LotReader;
import homework.chernetsov.task13.auctionservices.Participant;
import homework.chernetsov.task13.auctionservices.ParticipantReaderBuffer;
import homework.chernetsov.task13.exceptions.InvalidBetTime;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.Set;

public class ShowTask {
    public static void main(String[] args) throws InvalidBetTime {
        Auction auction = null;
        try {
            LotReader reader = new LotReader("src/homework/chernetsov/task13/resource/lot.txt");
            Lot lot = new Lot(reader.getLotValue(), ZonedDateTime.now().plusNanos(1_000_000_00));
            auction = new Auction(lot, reader.getLotName());
            Set<Participant> participants =
                    new ParticipantReaderBuffer("src/homework/chernetsov/task13/resource/participants.txt").getParticipants();
            Random rnd = new Random(System.currentTimeMillis());
            Thread[] threads = new Thread[participants.size()];
            int i = 0;
            for (Participant participant : participants) {
                threads[i] = new Thread(new ParticipantAuction(
                        auction, participant, Math.abs(rnd.nextInt()) % 999 + 1, 1000));
                i++;
            }
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception ex) {
            System.out.println("Sorry, the auction will not take place due to: \n" + ex.getMessage());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(auction.isOver());
        System.out.println(auction.getWinner());
        System.out.println(auction.getCurrentValue());
    }
}
