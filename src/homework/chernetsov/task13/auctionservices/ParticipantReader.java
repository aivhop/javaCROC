package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectParticipantsFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParticipantReader {
    public static Set<Participant> readFromFile(String fileName) throws IncorrectParticipantsFile {
        Set<Participant> participants = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    continue;
                }
                participants.add(new Participant(line));
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new IncorrectParticipantsFile("Sorry, " + e.getMessage(), e);
        }
        if (participants.isEmpty()) {
            throw new IncorrectParticipantsFile("Sorry, the auction will not take place without participants");
        }
        return participants;
    }
}
