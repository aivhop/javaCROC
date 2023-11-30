package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.dbentity.ElectorDao;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.CreationElectorException;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ShowTask {
    public static void main(String[] args) {
        try {
            DataBaseElection db = new DataBaseElection("jdbc:h2:mem:/db","empty", "");
            ElectorDao dao = new ElectorDao(DriverManager.getConnection("jdbc:h2:mem:/db","empty", ""));
            DriverManager.getConnection("jdbc:h2:mem:/db","empty", "").close();
            Elector el = new Elector("1234567890", "C", "A", "M", 1,true);
            System.out.println(dao.createElector(el));
            System.out.println(dao.createElector(el));
            System.out.println(dao.findElector(el.passportSeriesNumber()));
            System.out.println();
            System.out.println(dao.updateElector(new Elector(el.passportSeriesNumber(), "new","new","-",2,true)));
            System.out.println(dao.updateElector(new Elector("0000000000", "new","new","-",2,true)));
            System.out.println();
/*            System.out.println(dao.deleteElector(el.passportSeriesNumber()));
            System.out.println(dao.deleteElector(el.passportSeriesNumber()));*/
            System.out.println(dao.findElector("123567890"));
            System.out.println(db);
        } catch (ConnectionException | CreationElectorException | SQLException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
