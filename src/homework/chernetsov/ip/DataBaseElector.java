package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.dbentity.ElectorDao;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;
import homework.chernetsov.ip.interfaces.ElectionCommissionInter;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * The `DataBaseElector` class represents a database handler for electors.
 */
public class DataBaseElector implements AutoCloseable, ElectionCommissionInter {
    /**
     * Represents the database connection.
     */
    private final Connection connection;
    /**
     * Represents the data access object for interacting with the Elector table.
     */
    private final ElectorDao electorDao;

    /**
     * Creates a new `DataBaseElector` instance and establishes a connection to the database.
     *
     * @param url      The URL of the database.
     * @param user     The username for accessing the database.
     * @param password The password for accessing the database.
     * @throws ConnectionException If there is an issue establishing a connection to the database.
     */
    public DataBaseElector(String url, String user, String password) throws ConnectionException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.electorDao = new ElectorDao(connection);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        createTableElector();
    }

    /**
     * Retrieves a list of all electors from the database.
     *
     * @return A list of electors.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> readElectors() throws ConnectionException {
        return electorDao.readElectors();
    }

    /**
     * Creates a new elector in the database.
     *
     * @param elector The elector to be created.
     * @return True if the elector is created successfully, false otherwise.
     * @throws ConnectionException If there is an issue creating the elector in the database.
     */
    @Override
    public boolean createElector(Elector elector) throws ConnectionException {
        return electorDao.createElector(elector);
    }

    /**
     * Creates multiple electors in the database.
     *
     * @param electors The list of electors to be created.
     * @return The number of electors successfully created.
     * @throws ConnectionException If there is an issue creating electors in the database.
     */
    @Override
    public int createElectors(List<Elector> electors) throws ConnectionException {
        return electorDao.createElectors(electors);
    }

    /**
     * Finds an elector in the database by passport series and number.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @return The elector if found, null otherwise.
     * @throws ConnectionException If there is an issue finding the elector in the database.
     */
    @Override
    public Elector findElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.findElector(passportSeriesNumber);
    }

    /**
     * Updates an existing elector in the database.
     *
     * @param elector The elector with updated information.
     * @return True if the elector is updated successfully, false otherwise.
     * @throws ConnectionException If there is an issue updating the elector in the database.
     */
    @Override
    public boolean updateElector(Elector elector) throws ConnectionException {
        return electorDao.updateElector(elector);
    }

    /**
     * Deletes an elector from the database by passport series and number.
     *
     * @param passportSeriesNumber The passport series and number of the elector to be deleted.
     * @return True if the elector is deleted successfully, false otherwise.
     * @throws ConnectionException If there is an issue deleting the elector from the database.
     */
    @Override
    public boolean deleteElector(String passportSeriesNumber) throws ConnectionException {
        return electorDao.deleteElector(passportSeriesNumber);
    }

    /**
     * Retrieves a list of electors with a specific first name from the database.
     *
     * @param firstname The first name of the electors.
     * @return A list of electors with the specified first name.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException {
        return electorDao.getElectorsByFirstname(firstname);
    }

    /**
     * Retrieves a list of electors with a specific surname from the database.
     *
     * @param surname The surname of the electors.
     * @return A list of electors with the specified surname.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsBySurname(String surname) throws ConnectionException {
        return electorDao.getElectorsBySurname(surname);
    }

    /**
     * Retrieves a list of electors with a specific patronymic from the database.
     *
     * @param patronymic The patronymic of the electors.
     * @return A list of electors with the specified patronymic.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException {
        return electorDao.getElectorsByPatronymic(patronymic);
    }

    /**
     * Retrieves a list of electors with a specific precinct ID from the database.
     *
     * @param precinctId The precinct ID of the electors.
     * @return A list of electors with the specified precinct ID.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException {
        return electorDao.getElectorsByPrecinctId(precinctId);
    }

    /**
     * Retrieves a list of electors based on whether they have received a bulletin or not from the database.
     *
     * @param bulletinHasBeenReceived True for electors who have received a bulletin, false otherwise.
     * @return A list of electors based on the bulletin reception status.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException {
        return electorDao.getElectorsByBulletinHasBeenReceived(bulletinHasBeenReceived);
    }

    @Override
    public boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorRegistered(passportSeriesNumber);
    }

    /**
     * Issues a bulletin to an elector for a specified precinct in the database.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The ID of the precinct for which the bulletin is issued.
     * @throws ConnectionException        If there is an issue issuing the bulletin to the elector in the database.
     * @throws ReceivingBulletinException If there is an issue with the elector's eligibility to receive a bulletin.
     */
    @Override
    public void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException {
        electorDao.issueBulletin(passportSeriesNumber, precinctId);
    }

    /**
     * Checks if an elector is eligible to receive a bulletin for a specified precinct in the database.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The ID of the precinct for which bulletin eligibility is checked.
     * @return True if the elector can receive a bulletin for the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue checking the elector's eligibility in the database.
     */
    @Override
    public boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletinOnThisPrecinct(passportSeriesNumber, precinctId);
    }

    /**
     * Checks if an elector is registered on a specified precinct in the database.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The ID of the precinct for which registration is checked.
     * @return True if the elector is registered on the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue checking the elector's registration in the database.
     */
    @Override
    public boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        return electorDao.isElectorRegisteredOnPrecinct(passportSeriesNumber, precinctId);
    }

    /**
     * Checks if an elector is eligible to receive a bulletin in the database.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @return True if the elector can receive a bulletin, false otherwise.
     * @throws ConnectionException If there is an issue checking the elector's eligibility in the database.
     */
    @Override
    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException {
        return electorDao.isElectorCanReceiveBulletin(passportSeriesNumber);
    }

    /**
     * Retrieves a list of electors based on their age and election date from the database.
     *
     * @param age          The age of the electors.
     * @param electionDate The date of the election for calculating the age.
     * @return A list of electors based on the specified age and election date.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsByAge(int age, LocalDate electionDate) throws ConnectionException {
        return electorDao.getElectorsByAge(age, electionDate);
    }

    /**
     * Retrieves a list of electors based on a custom predicate from the database.
     *
     * @param electorPredicate The predicate to filter electors.
     * @return A list of electors based on the specified predicate.
     * @throws ConnectionException If there is an issue retrieving electors from the database.
     */
    @Override
    public List<Elector> getElectorsBy(Predicate<Elector> electorPredicate) throws ConnectionException {
        return electorDao.getElectorsBy(electorPredicate);
    }

    /**
     * Closes the database connection.
     *
     * @throws Exception If there is an issue closing the database connection.
     */
    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Creates the Elector table in the database.
     *
     * @throws ConnectionException If there is an issue creating the Elector table.
     */
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

    /**
     * Provides a string representation of the Elector table in the database.
     *
     * @return A string representation of the Elector table.
     */
    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            String tableName = "Elector";
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
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return db.toString();
    }
}
