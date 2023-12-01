package homework.chernetsov.ip.interfaces;


import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReadElectorsFromFileException;

/**
 * Interface defining methods for uploading and reading electors from CSV files.
 */
public interface FileService {

    /**
     * Uploads electors to a CSV file.
     *
     * @param fileName The name of the CSV file to upload electors to.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    void uploadElectorsToCSV(String fileName) throws ConnectionException;

    /**
     * Reads electors from a CSV file.
     *
     * @param fileName The name of the CSV file to read electors from.
     * @throws ConnectionException           If there is an issue with the database connection.
     * @throws ReadElectorsFromFileException If there is an issue reading electors from the CSV file.
     */
    void readElectorsFromCSV(String fileName) throws ConnectionException, ReadElectorsFromFileException;
}
