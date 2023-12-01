package homework.chernetsov.ip.dbservice;

import homework.chernetsov.ip.exceptions.InvalidResourceFileName;

import java.io.*;

public class FileProcessing {
    private static final String DEFAULT_FILE_NAME = ".+?\\.";
    private static final String DEFAULT_FORMAT_CSV = "csv";

    private FileProcessing() {
    }

    public static BufferedReader checkAndGetReaderFile(String inputFileName, String correctFormat) {
        if (!inputFileName.matches(DEFAULT_FILE_NAME + correctFormat)) {
            throw new InvalidResourceFileName(inputFileName, correctFormat);
        }
        try {
            return new BufferedReader(new FileReader(inputFileName));
        } catch (FileNotFoundException e) {
            throw new InvalidResourceFileName(e);
        }
    }

    public static BufferedReader checkAndGetReaderFileCSV(String inputFileName) {
        return checkAndGetReaderFile(inputFileName, DEFAULT_FORMAT_CSV);
    }

    public static PrintWriter checkAndGetWriterToFile(String inputFileName, String correctFormat) {
        if (!inputFileName.matches(DEFAULT_FILE_NAME + correctFormat)) {
            throw new InvalidResourceFileName(inputFileName, correctFormat);
        }
        try {
            return new PrintWriter(new FileWriter(inputFileName));
        } catch (IOException e) {
            throw new InvalidResourceFileName(e);
        }
    }

    public static PrintWriter checkAndGetWriterFileCSV(String inputFileName) {
        return checkAndGetWriterToFile(inputFileName, DEFAULT_FORMAT_CSV);
    }
}
