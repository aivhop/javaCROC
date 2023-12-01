package homework.chernetsov.ip;

import homework.chernetsov.ip.dbservice.DBFileProcessingService;

import java.time.LocalDate;


public class App {
    public static void main(String[] args) {

        try (DataBaseElector db = new DataBaseElector("jdbc:h2:mem:/db", "empty", "");
             DBFileProcessingService file = new DBFileProcessingService(db, "src/homework/chernetsov/ip/out/log.txt", "txt")) {
            file.readElectorsFromCSV("src/homework/chernetsov/ip/resource/data.csv");
            System.out.println(db);
            file.uploadElectorsToCSV("src/homework/chernetsov/ip/out/out.csv");
            System.out.println(db);
            System.out.println(db.getElectorsByAge(18, LocalDate.of(2022, 12, 2)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
    }
}
