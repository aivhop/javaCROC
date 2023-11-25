package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectParticipantsFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Participant(String name, int id) {
    private static int nextId = 0;

    public Participant(String name) {
        this(name, nextId);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the name can't be empty");
        }
        nextId++;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
