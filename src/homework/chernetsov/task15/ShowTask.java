package homework.chernetsov.task15;

import homework.chernetsov.task15.dbservice.DBConnection;
import homework.chernetsov.task15.dbservice.DBFilling;
import homework.chernetsov.task15.dbservice.FileProcessing;

import java.io.BufferedReader;


public class ShowTask {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Sorry, we need file path");
        }
        try (BufferedReader reader = FileProcessing.checkAndReadFileCSV(args[0]);
             DBConnection con = DBConnection.tryConnect();
             DB db = new DB(con.getConnection())) {
            DBFilling.fillFromCSVFileReader(db, reader);
            System.out.println(db);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
