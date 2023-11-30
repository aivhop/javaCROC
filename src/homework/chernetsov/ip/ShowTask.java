package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.interfaces.ElectionCommissionInter;
import homework.chernetsov.ip.interfaces.ElectorInter;

public class ShowTask {
    public static void main(String[] args) {
        try (DataBaseElector db = new DataBaseElector("jdbc:h2:mem:/db", "empty", "")) {
            Elector el1 = new Elector("0000000000", "surname", "name", "father", 1, true);
            Elector el2 = new Elector("0000000001", "surname", "A", "M", 2, true);
            Elector el3 = new Elector("0000000002", "C", "name", "M", 2, false);
            Elector el4 = new Elector("0000000003", "C", "A", "father", 3, false);

            db.createElector(el1);
            db.createElector(el2);
            db.createElector(el3);
            db.createElector(el4);

            System.out.println(db);


            System.out.println(db.getElectorsByOpportunityVote(false));

            ElectionCommissionInter employee = db;
            ElectorInter elect = db;
            employee.getElectorsByFirstname("123");
            System.out.println(elect.isElectorRegistered("0000000010"));
/*            System.out.println(dao.createElector(el));
            System.out.println(dao.createElector(el));
            System.out.println(dao.findElector(el.passportSeriesNumber()));
            System.out.println();
            System.out.println(dao.updateElector(new Elector(el.passportSeriesNumber(), "new","new","-",2,true)));
            System.out.println(dao.updateElector(new Elector("0000000000", "new","new","-",2,true)));
            System.out.println();
*//*            System.out.println(dao.deleteElector(el.passportSeriesNumber()));
            System.out.println(dao.deleteElector(el.passportSeriesNumber()));*//*
            System.out.println(dao.findElector("123567890"));
            System.out.println(db);*/
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
