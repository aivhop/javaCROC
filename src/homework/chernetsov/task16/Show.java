package homework.chernetsov.task15;

import homework.chernetsov.task15.dbservice.DBConnection;
import homework.chernetsov.task15.dbservice.DBFilling;
import homework.chernetsov.task15.dbservice.FileProcessing;

import java.io.BufferedReader;
import java.util.Arrays;


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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
