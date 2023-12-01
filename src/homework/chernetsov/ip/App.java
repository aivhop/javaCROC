package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.dbservice.DBFileProcessingService;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

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
            Elector elector = new Elector("1111222222", "Ф", "И", "О", 100, LocalDate.of(1961, 4, 12));
            System.out.println("expected 11: " + db.readElectors().size());
            System.out.println(db.createElector(elector));
            System.out.println("expected 12: " + db.readElectors().size());
            System.out.println(db.findElector("1111222222"));
            Elector elector1 = new Elector(elector.passportSeriesNumber(), "1", "1", "1", 10, LocalDate.of(2000, 10, 10));
            System.out.println(db.updateElector(elector1));
            System.out.println(db.updateElector(elector1));
            System.out.println(db.findElector("1111222222"));
            System.out.println(db.deleteElector("1111222222"));
            System.out.println("expected 11: " + db.readElectors().size());

            System.out.println(db.getElectorsByFirstname("Александр"));
            System.out.println(db.getElectorsBySurname("Смирнов"));
            System.out.println(db.getElectorsByPatronymic("Михайлович"));
            System.out.println(db.getElectorsByPrecinctId(8));
            db.issueBulletin("0000000006", 1);
            db.issueBulletin("0000000015", 8);
            System.out.println(db.getElectorsByBulletinHasBeenReceived(true));
            System.out.println(db);
            System.out.println("expected true: " + db.isElectorRegistered("0000000006"));
            System.out.println("expected false: " + db.isElectorRegistered("0011100006"));

            try {
                db.issueBulletin("1234567890", 1);
            } catch (ReceivingBulletinException e) {
                System.out.println("isElectorRegisteredOnPrecinct : " + e.isElectorRegisteredOnPrecinct());
                System.out.println("isBulletinHasBeenReceived : " + e.isBulletinHasBeenReceived());
            }

            System.out.println("Equals output: ");
            System.out.println(db.getElectorsByAge(18, LocalDate.of(2022, 12, 2)));
            System.out.println(db.getElectorsBy(
                    (el ->
                            LocalDate.of(2022, 12, 2).minusYears(18).isAfter(el.birthday()) &&
                                    LocalDate.of(2022, 12, 2).minusYears(18 + 1).isBefore(el.birthday()))
            ));

            System.out.println("expected true: " + db.isElectorCanReceiveBulletinOnThisPrecinct("0000000014", 8));
            System.out.println("expected false: " + db.isElectorCanReceiveBulletinOnThisPrecinct("0000000014", 9));
            System.out.println("expected false: " + db.isElectorCanReceiveBulletinOnThisPrecinct("0000000006", 1));
            System.out.println("expected true: " + db.isElectorRegisteredOnPrecinct("0000000014", 8));
            System.out.println("expected false: " + db.isElectorRegisteredOnPrecinct("0000000014", 9));

            System.out.println("expected true: " + db.isElectorCanReceiveBulletin("0000000014"));
            System.out.println("expected false: " + db.isElectorCanReceiveBulletin("0000000006"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
    }
}
