package homework.chernetsov.task15;

import homework.chernetsov.task15.dbservice.DBConnection;
import homework.chernetsov.task15.dbservice.DBFilling;
import homework.chernetsov.task15.dbservice.FileProcessing;
import homework.chernetsov.task16.dbentity.dao.ClientDao;

import java.io.BufferedReader;
import java.util.Arrays;


public class ShowTask {
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
/*            ClientDao clientDao = new ClientDao(con.getConnection());
            System.out.println(clientDao.isExistPhone("+79995554433"));
            System.out.println(clientDao.isExistPhone("+7999555443"));*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
