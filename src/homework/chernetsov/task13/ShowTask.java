package homework.chernetsov.task13;

import homework.chernetsov.task13.auctionservices.Lot;
import homework.chernetsov.task13.auctionservices.LotReader;
import homework.chernetsov.task13.auctionservices.Participant;
import homework.chernetsov.task13.auctionservices.ParticipantReaderBuffer;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class ShowTask {
    public static void main(String[] args) {
        try {

            LotReader reader = new LotReader("src/homework/chernetsov/task13/resource/lot.txt");
            Lot lot = new Lot(reader.getLotValue(), ZonedDateTime.now().plusSeconds(1));
            Auction auction = new Auction(lot, reader.getLotName());
            Set<Participant> participants =
                    new ParticipantReaderBuffer("src/homework/chernetsov/task13/resource/participants.txt").getParticipants();
            Random rnd = new Random(System.currentTimeMillis());
            Thread[] threads = new Thread[participants.size()];
            int i = 0;
            for (Participant participant : participants) {
                threads[i] = new Thread(new ParticipantAuction(
                        auction, participant, Math.abs(rnd.nextInt()) % 99 + 1, 100));
                i++;
            }
            for(int j = 0; j < threads.length; j++){
                threads[j].join();
                threads[j].start();
            }
        } catch (Exception ex) {
            System.out.println("Sorry, the auction will not take place due to: \n" + ex.getMessage());
        }
    }
}
