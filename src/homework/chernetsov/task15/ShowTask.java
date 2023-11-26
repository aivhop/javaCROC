package homework.chernetsov.task15;


import homework.chernetsov.task15.exceptions.ConnectionException;
import homework.chernetsov.task15.exceptions.InvalidResourceFileName;

import java.io.BufferedReader;


public class ShowTask {
    public static void main(String[] args) {
        try {
            BufferedReader reader = FileProcessing.checkAndReadFileCSV("src/homework/chernetsov/task15/resource/data.csv");
            DB db = new DB(DBConnection.tryConnect().getConnection());
            DBFilling.fill(db, reader);
        } catch (InvalidResourceFileName | ConnectionException ex) {
            System.out.println(ex.getMessage());
        } catch ()
    }

}
