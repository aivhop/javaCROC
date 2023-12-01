package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.dbservice.FileProcessing;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;
import homework.chernetsov.ip.exceptions.ReadElectorsFromFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface FileService {
    void uploadElectorsToCSV(String fileName) throws ConnectionException;

    void readElectorsFromCSV(String fileName) throws ConnectionException, ReadElectorsFromFileException;
}
