package homework.chernetsov.task16.dbservice;

import homework.chernetsov.task16.DB;
import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.dbentity.TupleDB;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DBFilling {
    public static void fillFromCSVFileReader(DB dataBase, BufferedReader source) {
        Objects.requireNonNull(dataBase);
        Objects.requireNonNull(source);
        String line;
        try {
            line = source.readLine();
            while (line != null) {
                String[] parameters = parseCSV(line);
                Integer idClient = Integer.parseInt(parameters[0]);
                String surnameClient = parameters[1];
                String firstnameClient = parameters[2];
                String phoneClient = parameters[3];
                Integer medCardNumberPet = Integer.parseInt(parameters[4]);
                String namePet = parameters[5];
                Integer agePet = Integer.parseInt(parameters[6]);
                Client client = new Client(idClient, surnameClient, firstnameClient, phoneClient);
                Pet pet = new Pet(medCardNumberPet, List.of(client), namePet, agePet);
                dataBase.createPet(pet);
                line = source.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] parseCSV(String line) {
        return line.split(",");
    }
}
