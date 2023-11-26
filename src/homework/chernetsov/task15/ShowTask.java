package homework.chernetsov.task15;

import homework.chernetsov.task15.dbservice.DBConnection;
import homework.chernetsov.task15.dbservice.DBFilling;
import homework.chernetsov.task15.dbservice.FileProcessing;
import homework.chernetsov.task15.exceptions.InvalidResourceFileName;

import java.io.BufferedReader;


public class ShowTask {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Sorry, we need file path");
        }
        try (BufferedReader reader = FileProcessing.checkAndReadFileCSV(args[0]);
             DB db = new DB(DBConnection.tryConnect().getConnection())) {
            DBFilling.fill(db, reader);
            System.out.println(db);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
