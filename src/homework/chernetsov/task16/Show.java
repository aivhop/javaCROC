package homework.chernetsov.task16;


import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.dbservice.DBConnection;
import homework.chernetsov.task16.dbservice.DBFilling;
import homework.chernetsov.task16.dbservice.FileProcessing;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.io.BufferedReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


public class Show {
    public static void main(String[] arg) {
        String[] args = {"src/homework/chernetsov/task15/resource/data.csv"};
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
            System.out.println("delete client, wait clean client when find him: " + db.findClient(20) + '\n');


            Pet addedPet = db.createPet("newPet", 100, List.of(clientToAdd));

            String sql = "insert into Pet (pet_name, pet_age)" +
                    "values(?, ?)";
            PreparedStatement statement = con.getConnection().prepareStatement(sql);
            statement.setString(1, "name");
            statement.setInt(2, 2);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("createPet new : " + addedPet);

            System.out.println("createPet already exist" + db.createPet(addedPet.name(),addedPet.age(),addedPet.clients()));

            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
