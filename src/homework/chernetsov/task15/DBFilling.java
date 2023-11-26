package homework.chernetsov.task15;

import java.io.BufferedReader;
import java.io.IOException;

public class DBFilling {
    public static void fill(DB dataBase, BufferedReader source) {
        //todo source!=null
        String line = null;
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
                System.out.print(pet.getMedCardNumber() + " ");
                System.out.println(client.getId());
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
