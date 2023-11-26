package homework.chernetsov.task15;

import homework.chernetsov.task15.exceptions.InvalidResourceFileName;

import java.io.*;

public class FileProcessing {
    private static final String DEFAULT_FILE_NAME = ".+?\\.";
    private static final String DEFAULT_FORMAT_CSV = "csv";

    private FileProcessing() {
    }

    public static BufferedReader checkAndReadFile(String inputFileName, String correctFormat) {
        if (!inputFileName.matches(DEFAULT_FILE_NAME + correctFormat)) {
            throw new InvalidResourceFileName(inputFileName, correctFormat);
        }
        try {
            return new BufferedReader(new FileReader(inputFileName));
        } catch (FileNotFoundException e) {
            throw new InvalidResourceFileName(e);
        }
    }
    public static BufferedReader checkAndReadFileCSV(String inputFileName) {
        return checkAndReadFile(inputFileName, DEFAULT_FORMAT_CSV);
    }
}
