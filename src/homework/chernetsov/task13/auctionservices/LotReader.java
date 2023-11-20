package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectLotFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class LotReader {
    private final BufferedReader reader;
    private String lotName;
    private BigDecimal lotValue;

    public LotReader(String fileName) throws IncorrectLotFile {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            this.reader = reader;
            String readLine = reader.readLine();
            if (readLine == null) {
                throw new IncorrectLotFile("Sorry, the lot name is missing");
            } else if (readLine.isEmpty()) {
                throw new IncorrectLotFile("Sorry, the lot name can't be empty");
            }
            this.lotName = readLine;
            readLine = reader.readLine();
            try {
                this.lotValue = new BigDecimal(readLine);
            } catch (NumberFormatException ex) {
                throw new IncorrectLotFile("Sorry, the lot price is set incorrectly", ex);
            }
        } catch (IOException e) {
            throw new IncorrectLotFile("Sorry, " + e.getMessage(), e);
        }
    }

    public String getLotName() {
        return lotName;
    }

    public BigDecimal getLotValue() {
        return lotValue;
    }

    @Override
    public String toString() {
        return  lotName + ": " + lotValue;
    }

}
