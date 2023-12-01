package homework.chernetsov.ip.dbservice;

import homework.chernetsov.ip.exceptions.InvalidResourceFileName;

import java.io.*;
/**
 * Utility class for processing files.
 */
public class FileProcessing {
    /**
     * The default file name pattern.
     */
    private static final String DEFAULT_FILE_NAME = ".+?\\.";
    /**
     * The default CSV file format.
     */
    private static final String DEFAULT_FORMAT_CSV = "csv";
    /**
     * Private constructor to prevent instantiation.
     */
    private FileProcessing() {}
    /**
     * Checks and retrieves a BufferedReader for the specified input file.
     *
     * @param inputFileName The name of the input file.
     * @param correctFormat The correct format for the file.
     * @return A BufferedReader for the input file.
     * @throws InvalidResourceFileName If the file name does not match the correct format or if the file is not found.
     */
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
    /**
     * Checks and retrieves a BufferedReader for a CSV file.
     *
     * @param inputFileName The name of the input CSV file.
     * @return A BufferedReader for the input CSV file.
     * @throws InvalidResourceFileName If the file name does not match the correct format or if the file is not found.
     */
    public static BufferedReader checkAndGetReaderFileCSV(String inputFileName) {
        return checkAndGetReaderFile(inputFileName, DEFAULT_FORMAT_CSV);
    }
    /**
     * Checks and retrieves a PrintWriter for the specified output file.
     *
     * @param inputFileName The name of the output file.
     * @param correctFormat The correct format for the file.
     * @return A PrintWriter for the output file.
     * @throws InvalidResourceFileName If the file name does not match the correct format or if an I/O error occurs.
     */
    public static PrintWriter checkAndGetWriterToFile(String inputFileName, String correctFormat) {
        if (!inputFileName.matches(DEFAULT_FILE_NAME + correctFormat)) {
            throw new InvalidResourceFileName(inputFileName, correctFormat);
        }
        try {
            return new PrintWriter(new FileWriter(inputFileName),true);
        } catch (IOException e) {
            throw new InvalidResourceFileName(e);
        }
    }
    /**
     * Checks and retrieves a PrintWriter for a CSV output file.
     *
     * @param inputFileName The name of the output CSV file.
     * @return A PrintWriter for the output CSV file.
     * @throws InvalidResourceFileName If the file name does not match the correct format or if an I/O error occurs.
     */
    public static PrintWriter checkAndGetWriterFileCSV(String inputFileName) {
        return checkAndGetWriterToFile(inputFileName, DEFAULT_FORMAT_CSV);
    }
}
