package homework.chernetsov.ip.dbservice;

import homework.chernetsov.ip.DataBaseElector;
import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReadElectorsFromFileException;
import homework.chernetsov.ip.interfaces.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service for processing files related to the Elector database.
 */
public class DBFileProcessingService implements FileService, AutoCloseable {
    /**
     * The instance to interact with the Elector database.
     */
    private final DataBaseElector dataBaseElector;
    /**
     * The PrintWriter used for logging.
     */
    private final PrintWriter log;

    /**
     * Constructs a DBFileProcessingService with the specified DataBaseElector and log information.
     *
     * @param dataBaseElector The DataBaseElector instance to interact with the Elector database.
     * @param logFileName     The name of the log file.
     * @param logFileFormat   The format of the log file.
     */
    public DBFileProcessingService(DataBaseElector dataBaseElector, String logFileName, String logFileFormat) {
        this.dataBaseElector = dataBaseElector;
        this.log = FileProcessing.checkAndGetWriterToFile(logFileName, logFileFormat);
    }
    /**
     * Uploads electors from the database to a CSV file.
     *
     * @param fileName The name of the CSV file.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public void uploadElectorsToCSV(String fileName) throws ConnectionException {
        try (PrintWriter writer = FileProcessing.checkAndGetWriterFileCSV(fileName)) {
            writer.println("passport_series,passport_number,surname,firstname,patronymic," +
                    "precinct_id,day of birth,month of birth,year of birth," +
                    "bulletin_has_been_received");
            List<Elector> electors = dataBaseElector.readElectors();
            electors.stream().map((elector) -> elector.passportSeries() +
                    ',' + elector.passportNumber() +
                    ',' + elector.surname() +
                    ',' + elector.firstname() +
                    ',' + elector.patronymic() +
                    ',' + elector.birthday().getDayOfMonth() +
                    ',' + elector.birthday().getMonthValue() +
                    ',' + elector.birthday().getYear() +
                    ',' + elector.precinctId() +
                    ',' + elector.bulletinReceived()).forEach(writer::println);
        }
    }
    /**
     * Reads electors from a CSV file and inserts them into the database.
     *
     * @param fileName The name of the CSV file.
     * @throws ConnectionException           If there is an issue with the database connection.
     * @throws ReadElectorsFromFileException If there is an issue reading electors from the file.
     */
    public void readElectorsFromCSV(String fileName) throws ConnectionException, ReadElectorsFromFileException {
        log.println("READING:");
        try (BufferedReader source = FileProcessing.checkAndGetReaderFileCSV(fileName)) {
            Objects.requireNonNull(source);
            dataBaseElector.createElectors(source.lines().map((x) -> x.split(",")).filter((x) -> x.length == 9 || x.length == 10)
                    .map((x) -> {
                        String passportSeriesNumber = x[0] + x[1];
                        String surname = x[2];
                        String firstname = x[3];
                        String patronymic = x[4];
                        int day;
                        int month;
                        int year;
                        int precinctId;
                        try {
                            day = Integer.parseInt(x[5]);
                            month = Integer.parseInt(x[6]);
                            year = Integer.parseInt(x[7]);
                            precinctId = Integer.parseInt(x[8]);
                        } catch (NumberFormatException e) {
                            log.println(e.getMessage());
                            return null;
                        }
                        Elector elector;
                        try {
                            elector = (x.length == 9) ? new Elector(passportSeriesNumber, surname,
                                    firstname, patronymic, precinctId, day, month, year) :
                                    new Elector(passportSeriesNumber, surname,
                                            firstname, patronymic, precinctId, day, month, year, Boolean.getBoolean(x[9]));
                        } catch (Exception ex) {
                            log.println(ex + ", Elector: " + passportSeriesNumber);
                            return null;
                        }
                        log.println("Added: " + elector);
                        return elector;
                    })
                    .filter(Objects::nonNull).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new ReadElectorsFromFileException(e);
        }
    }
    /**
     * Closes the log file.
     */
    @Override
    public void close() {
        log.close();
    }
}
