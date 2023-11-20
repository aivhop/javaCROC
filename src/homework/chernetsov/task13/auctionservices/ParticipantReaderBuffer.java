package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectParticipantsFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ParticipantReaderBuffer {

    private final HashSet<Participant> participants = new HashSet<>();

    public ParticipantReaderBuffer(String fileName) throws IncorrectParticipantsFile {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    continue;
                }
                Participant participant = new Participant(line);
                this.participants.add(participant);
            }
        } catch (IOException e) {
            throw new IncorrectParticipantsFile("Sorry, " + e.getMessage(), e);
        }
        if (this.participants.isEmpty()) {
            throw new IncorrectParticipantsFile("Sorry, the auction will not take place without participants");
        }
    }

    public HashSet<Participant> getParticipants() {
        return new HashSet<>(participants);
    }
}
