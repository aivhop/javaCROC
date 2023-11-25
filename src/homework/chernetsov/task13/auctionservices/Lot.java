package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectLotFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Lot {
    volatile private BigDecimal currentValue;
    private Participant currentParticipant;
    private final String name;

    public Lot(BigDecimal currentValue, Participant currentParticipant, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the lot name can't be empty");
        }
        this.name = name;
        setCurrentValue(currentValue);
        this.currentParticipant = currentParticipant;
    }


    public Lot(BigDecimal currentValue, String name) {
        this(currentValue, null, name);
    }
    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public Participant getCurrentParticipant() {
        return currentParticipant;
    }

    public String getName() {
        return name;
    }

    public void setCurrentParticipant(Participant currentParticipant) {
        this.currentParticipant = currentParticipant;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        if (currentValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sorry, the cost must be greater than zero");
        }
        this.currentValue = currentValue;
    }

    public static Lot readFromFile(String fileName) throws IncorrectLotFile {
        String lotName;
        BigDecimal lotValue;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String readLine = reader.readLine();
            if (readLine == null || readLine.isEmpty()) {
                throw new IncorrectLotFile("Sorry, the lot name is missing");
            }
            lotName = readLine;
            readLine = reader.readLine();
            try {
                lotValue = new BigDecimal(readLine);
            } catch (NumberFormatException ex) {
                throw new IncorrectLotFile("Sorry, the lot price is set incorrectly", ex);
            }
        } catch (IOException e) {
            throw new IncorrectLotFile("Sorry, " + e.getMessage(), e);
        }
        return new Lot(lotValue, lotName);
    }
}
