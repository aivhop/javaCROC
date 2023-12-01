package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.dbentity.ElectorDao;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;
import homework.chernetsov.ip.interfaces.ElectionCommissionInter;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class DataBaseElector implements AutoCloseable, ElectionCommissionInter {
    private final Connection connection;

    private final ElectorDao electorDao;

    public DataBaseElector(String url, String user, String password) throws ConnectionException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.electorDao = new ElectorDao(connection);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        createTableElector();
    }

    @Override
    public List<Elector> readElectors() throws ConnectionException {
        return electorDao.readElectors();
    }

    @Override
    public boolean createElector(Elector elector) throws ConnectionException {
        return electorDao.createElector(elector);
    }

    @Override
    public int createElectors(List<Elector> electors) throws ConnectionException {
        return electorDao.createElectors(electors);
    }

    @Override
    public Elector findElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.findElector(passportSeriesNumber);
    }

    @Override
    public boolean updateElector(Elector elector) throws ConnectionException {
        return electorDao.updateElector(elector);
    }

    @Override
    public boolean deleteElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.deleteElector(passportSeriesNumber);
    }

    @Override
    public List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException {
        return electorDao.getElectorsByFirstname(firstname);
    }

    @Override
    public List<Elector> getElectorsBySurname(String surname) throws ConnectionException {
        return electorDao.getElectorsBySurname(surname);
    }

    @Override
    public List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException {
        return null;
    }

    @Override
    public List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException {
        return electorDao.getElectorsByPrecinctId(precinctId);
    }

    @Override
    public List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException {
        return electorDao.getElectorsByBulletinHasBeenReceived(bulletinHasBeenReceived);
    }

    @Override
    public boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorRegistered(passportSeriesNumber);
    }

    @Override
    public void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException {
        electorDao.issueBulletin(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletinOnThisPrecinct(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        return electorDao.isElectorRegisteredOnPrecinct(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletin(passportSeriesNumber);
    }

    @Override
    public List<Elector> getElectorsByAge(int age, LocalDate electionDate) throws ConnectionException {
        return electorDao.getElectorsByAge(age, electionDate);
    }

    /*    @Override
    public boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorRegistered(passportSeriesNumber);
    }

    @Override
    public boolean isElectorCanVote(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletin(passportSeriesNumber);
    }

    @Override
    public void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException {
        electorDao.issueBulletin(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        return electorDao.isElectorRegisteredOnPrecinct(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletinOnThisPrecinct(passportSeriesNumber, precinctId);
    }

    @Override
    public boolean createElector(Elector elector) throws ConnectionException {
        return electorDao.createElector(elector);
    }

    @Override
    public Elector findElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.findElector(passportSeriesNumber);
    }

    @Override
    public boolean updateElector(Elector elector) throws ConnectionException {
        return electorDao.updateElector(elector);
    }

    @Override
    public boolean deleteElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.deleteElector(passportSeriesNumber);
    }

    @Override
    public List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException {
        return electorDao.getElectorsByFirstname(firstname);
    }

    @Override
    public List<Elector> getElectorsBySurname(String surname) throws ConnectionException {
        return electorDao.getElectorsBySurname(surname);
    }

    @Override
    public List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException {
        return electorDao.getElectorsByPatronymic(patronymic);
    }

    @Override
    public List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException {
        return electorDao.getElectorsByPrecinctId(precinctId);
    }

    @Override
    public List<Elector> getElectorsByOpportunityVote(boolean hasOpportunityVote) throws ConnectionException {
        return electorDao.getElectorsByBulletinHasBeenReceived(hasOpportunityVote);
    }
    @Override
    public List<Elector> readElectors() throws ConnectionException {
        return electorDao.readElectors();
    }
    @Override
    public int createElectors(List<Elector> electors) throws ConnectionException {
        return electorDao.createElectors(electors);
    }
    @Override
    public boolean createElector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                                 int precinctId, boolean opportunityVote) throws ConnectionException {
        return electorDao.createElector(new Elector(passportSeriesNumber, surname, firstname, patronymic, precinctId, opportunityVote));
    }

    @Override
    public boolean createElector(String passportSeriesNumber, String surname, String firstname,
                                 int precinctId, boolean opportunityVote) throws ConnectionException {
        return electorDao.createElector(new Elector(passportSeriesNumber, surname, firstname, precinctId, opportunityVote));
    }*/

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    private void createTableElector() throws ConnectionException {
        String tableElector = "CREATE TABLE Elector( elector_passport_series_number VARCHAR(10) PRIMARY KEY NOT NULL, " +
                "elector_surname VARCHAR NOT NULL, " +
                "elector_firstname VARCHAR NOT NULL, elector_patronymic VARCHAR NOT NULL, electoral_precinct_id INTEGER NOT NULL, " +
                "bulletin_has_been_received BOOLEAN NOT NULL, elector_birthday VARCHAR(10) NOT NULL )";
        try (Statement statement = connection.createStatement()) {
            statement.execute(tableElector);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            String[] tables = {"Elector"};
            for (String tableName : tables) {
                String sql = "select * from " + tableName;
                db.append("\nTable ").append(tableName).append("\n");
                try (ResultSet result = statement.executeQuery(sql)) {
                    ResultSetMetaData meta = result.getMetaData();
                    for (int j = 1; j <= meta.getColumnCount(); j++) {
                        db.append(meta.getColumnName(j))
                                .append("\t");
                    }
                    while (result.next()) {
                        db.append("\n");
                        for (int j = 1; j <= meta.getColumnCount(); j++) {
                            db.append(result.getString(j))
                                    .append("\t\t");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return db.toString();
    }
}
