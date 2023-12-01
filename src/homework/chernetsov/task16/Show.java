package homework.chernetsov.task16;


import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.dbservice.DBConnection;
import homework.chernetsov.task16.dbservice.DBFilling;
import homework.chernetsov.task16.dbservice.FileProcessing;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;


public class Show {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Sorry, we need file path");
        }
        try (BufferedReader reader = FileProcessing.checkAndReadFileCSV(args[0]);
             DBConnection con = DBConnection.tryConnect();
             DB db = new DB(con.getConnection())) {
            DBFilling.fillFromCSVFileReader(db, reader);
            System.out.println(db);
            System.out.println("===================test new methods");
            String myPhone = "89822939299";

            Client test = db.findClient(1);
            System.out.println("FindClient: " + test + '\n');

            Client newTestClient = new Client(test.id(), "Changed", "AlsoChanged", "123");
            System.out.println("UpdateClient: Old value: " + db.updateClient(newTestClient));
            System.out.println("UpdateClient: New value: " + db.findClient(1) + '\n');

            Client clientToAdd = new Client(20, "Chernetsov", "Alexander", myPhone);
            db.createClient(clientToAdd);
            System.out.println("correct createClient: " + db.findClient(20) + '\n');


            System.out.print("incorrect createClient(phone already exist): ");
            Client clientWithEqualPhone = new Client(100, "Surname", "Name", myPhone);
            try {
                db.createClient(clientWithEqualPhone);
            } catch (InvalidClientPhoneException e) {
                System.out.println(e.toString() + '\n');
            }
            db.deleteClient(20);
            System.out.println("delete client, wait null client when find him: " + db.findClient(20) + '\n');


            Pet addedPet = db.createPet("newPet", 100, List.of(clientToAdd));
            System.out.println("createPet new : " + addedPet + '\n');
            System.out.println("findPet : " + db.findPet(addedPet.medCardNumber()) + '\n');

            Pet toUpdatePet = new Pet(addedPet.medCardNumber(), List.of(db.findClient(1)), "updated", 10);
            System.out.println("Old db, with old relations:\n");
            System.out.println(db);
            db.updatePet(toUpdatePet);
            System.out.println("New DB:\n");
            System.out.println(db);
            db.deletePet(addedPet.medCardNumber());
            System.out.println("delete pet, wait null pet when find it: " + db.findPet(20));
            System.out.println("DB after removing, cleaned relations: ");
            System.out.println(db);

            System.out.println("findClientPhoneNumbersBy: " + db.findClientPhoneNumbersBy(db.findPet(1)));
            System.out.println("getAllPetsOf: " + db.getAllPetsOf(db.findClient(1)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
