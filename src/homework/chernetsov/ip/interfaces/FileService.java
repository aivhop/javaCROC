package homework.chernetsov.ip.interfaces;


import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReadElectorsFromFileException;


public interface FileService {
    void uploadElectorsToCSV(String fileName) throws ConnectionException;

    void readElectorsFromCSV(String fileName) throws ConnectionException, ReadElectorsFromFileException;
}

//todo