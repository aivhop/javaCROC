package homework.chernetsov.task15.dbservice;

import homework.chernetsov.task15.DB;
import homework.chernetsov.task15.dbentity.Client;
import homework.chernetsov.task15.dbentity.Pet;
import homework.chernetsov.task15.dbentity.TupleDB;

import java.io.BufferedReader;
import java.io.IOException;

public class DBFilling {
    public static void fillFromCSVFileReader(DB dataBase, BufferedReader source) {
        //todo source!=null
        String line;
        try {
            line = source.readLine();
            while (line != null) {
                String[] parameters = parseCSV(line);
                int idClient = Integer.parseInt(parameters[0]);
                String surnameClient = parameters[1];
                String firstnameClient = parameters[2];
                String phoneClient = parameters[3];
                int medCardNumberPet = Integer.parseInt(parameters[4]);
                String namePet = parameters[5];
                int agePet = Integer.parseInt(parameters[6]);
                Client client = new Client(idClient, surnameClient, firstnameClient, phoneClient);
                Pet pet = new Pet(medCardNumberPet, client, namePet, agePet);
                dataBase.create(new TupleDB(pet, client));
                line = source.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);//todo custom
        }
    }

    private static String[] parseCSV(String line) {
        return line.split(",");
    }
}
