package homework.chernetsov.ip.dbservice;

import homework.chernetsov.ip.DataBaseElector;
import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;
import homework.chernetsov.ip.exceptions.ReadElectorsFromFileException;
import homework.chernetsov.ip.interfaces.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DBFileProcessingService implements FileService {
    private final DataBaseElector dataBaseElector;

    public DBFileProcessingService(DataBaseElector dataBaseElector) {
        this.dataBaseElector = dataBaseElector;
    }

    public void uploadElectorsToCSV(String fileName) throws ConnectionException {
        try (PrintWriter writer = FileProcessing.checkAndGetWriterFileCSV(fileName)) {
            List<Elector> electors = dataBaseElector.readElectors();
            electors.stream().map((elector) -> elector.passportSeries() +
                    ',' + elector.passportNumber() +
                    ',' + elector.surname() +
                    ',' + elector.firstname() +
                    ',' + elector.patronymic() +
                    ',' + elector.precinctId() +
                    ',' + elector.opportunityVote()).forEach(writer::println);
        }
    }

    public void readElectorsFromCSV(String fileName) throws ConnectionException, ReadElectorsFromFileException {
        try (BufferedReader source = FileProcessing.checkAndGetReaderFileCSV(fileName)) {
            Objects.requireNonNull(source);
            dataBaseElector.createElectors(source.lines().map((x) -> x.split(",")).filter((x) -> x.length == 6 || x.length == 7)
                    .map((x) -> {
                        String passportSeriesNumber = x[0] + x[1];
                        String surname = x[2];
                        String firstname = x[3];
                        int nextParam = 4;
                        String patronymic = (x.length == 7) ? x[nextParam++] : null;
                        int precinctId;
                        boolean opportunityToVote;
                        Elector elector;
                        try {
                            precinctId = Integer.parseInt(x[nextParam]);
                            opportunityToVote = Boolean.getBoolean(x[nextParam]);
                            elector = new Elector(passportSeriesNumber, surname, firstname, patronymic, precinctId, opportunityToVote);
                        } catch (InvalidElectorPassport | NumberFormatException e) {
                            return null;
                        }
                        return elector;
                    })
                    .filter(Objects::nonNull).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new ReadElectorsFromFileException(e);
        }
    }
}
