package homework.chernetsov.task13.auctionservices;

import homework.chernetsov.task13.exceptions.IncorrectLotFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class LotReader {
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
