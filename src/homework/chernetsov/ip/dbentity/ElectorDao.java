package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Data Access Object class (DAO) for managing Elector entities in a database.
 */
public class ElectorDao {
    /**
     * field with database connection
     */
    private final Connection connection;

    /**
     * Constructs an ElectorDao with the specified database connection.
     *
     * @param connection The database connection.
     */
    public ElectorDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Reads all electors from the database.
     *
     * @return A list of Elector objects representing all electors in the database.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> readElectors() throws ConnectionException {
        List<Elector> result = new ArrayList<>();
        String sql = "SELECT * FROM Elector";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    result.add(createElectorFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return result;
    }

    /**
     * Creates a single elector in the database.
     *
     * @param elector The Elector object to be created.
     * @return True if the elector was successfully created, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean createElector(Elector elector) throws ConnectionException {
        return createElectors(List.of(elector)) == 1;
    }

    /**
     * Creates multiple electors in the database.
     *
     * @param electors The list of Elector objects to be created.
     * @return The number of electors successfully created.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public int createElectors(List<Elector> electors) throws ConnectionException {
        int count = 0;
        String sql = "INSERT INTO Elector(elector_passport_series_number, " +
                "elector_surname, elector_firstname, elector_patronymic, " +
                "electoral_precinct_id, bulletin_has_been_received, elector_birthday) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Elector elector : electors) {
                statement.setString(1, elector.passportSeriesNumber());
                statement.setString(2, elector.surname());
                statement.setString(3, elector.firstname());
                statement.setString(4, elector.patronymic());
                statement.setInt(5, elector.precinctId());
                statement.setBoolean(6, elector.bulletinReceived());
                statement.setString(7, elector.birthday().toString());
                try {
                    statement.execute();
                    count++;
                } catch (SQLException ex) {
                    // elector already exist
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return count;
    }

    /**
     * Finds an elector in the database based on passport series number.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @return The Elector object if found, null otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public Elector findElector(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                return createElectorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return null;
    }

    /**
     * Updates an existing elector in the database.
     *
     * @param elector The Elector object with updated information.
     * @return True if the elector was successfully updated, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean updateElector(Elector elector) throws ConnectionException {
        String sql = "UPDATE Elector SET elector_surname = ?, elector_firstname = ?," +
                " elector_patronymic = ?, electoral_precinct_id = ?, bulletin_has_been_received = ?, elector_birthday = ? " +
                "WHERE elector_passport_series_number = " + elector.passportSeriesNumber();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, elector.surname());
            statement.setString(2, elector.firstname());
            statement.setString(3, elector.patronymic());
            statement.setInt(4, elector.precinctId());
            statement.setBoolean(5, elector.bulletinReceived());
            statement.setString(6, elector.birthday().toString());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Deletes an elector from the database based on passport series number.
     *
     * @param passportSeriesNumber The passport series number of the elector to be deleted.
     * @return True if the elector was successfully deleted, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean deleteElector(String passportSeriesNumber) throws ConnectionException {
        String sql = "DELETE FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql) == 1;
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Gets electors from the database based on the elector's first name.
     *
     * @param firstname The first name of the elector.
     * @return A list of Elector objects with the specified first name.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_firstname = ?";
        return getElectors(sql, firstname);
    }

    /**
     * Gets electors from the database based on the elector's last name.
     *
     * @param surname The last name of the elector.
     * @return A list of Elector objects with the specified last name.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsBySurname(String surname) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_surname = ?";
        return getElectors(sql, surname);
    }

    /**
     * Gets electors from the database based on the elector's patronymic.
     *
     * @param patronymic The patronymic (middle name) of the elector.
     * @return A list of Elector objects with the specified patronymic.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_patronymic = ?";
        return getElectors(sql, patronymic);
    }

    /**
     * Gets electors from the database based on the electoral precinct ID.
     *
     * @param precinctId The electoral precinct ID.
     * @return A list of Elector objects belonging to the specified precinct.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE electoral_precinct_id = ?";
        return getElectors(sql, precinctId);
    }

    /**
     * Gets electors from the database based on whether they have received a voting bulletin.
     *
     * @param bulletinHasBeenReceived Indicates whether the elector has received a voting bulletin.
     * @return A list of Elector objects based on the specified criteria.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE bulletin_has_been_received = ?";
        return getElectors(sql, bulletinHasBeenReceived);
    }

    /**
     * Checks if an elector is registered in the database based on passport series number.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @return True if the elector is registered, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Issues a voting bulletin to an elector in the database.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @param precinctId           The electoral precinct ID.
     * @throws ConnectionException        If there is an issue with the database connection.
     * @throws ReceivingBulletinException If the elector is not registered on the specified precinct or has already received a bulletin.
     */
    public void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException {
        boolean isElectorRegisteredOnPrecinct = isElectorRegisteredOnPrecinct(passportSeriesNumber, precinctId);
        boolean isElectorCanReceiveBulletin = isElectorCanReceiveBulletin(passportSeriesNumber);
        if (!isElectorCanReceiveBulletin || !isElectorRegisteredOnPrecinct) {
            throw new ReceivingBulletinException(isElectorRegisteredOnPrecinct, isElectorCanReceiveBulletin);
        }
        String sql = "UPDATE Elector SET bulletin_has_been_received = ? " +
                "WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Checks if an elector is eligible to receive a voting bulletin based on the precinct and previous receipt status.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @param precinctId           The electoral precinct ID.
     * @return True if the elector can receive a bulletin on the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        String sql = "SELECT bulletin_has_been_received, electoral_precinct_id FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return !resultSet.getBoolean("bulletin_has_been_received") &&
                            precinctId == resultSet.getInt("electoral_precinct_id");
                }
                throw new InvalidElectorPassport(passportSeriesNumber, "There is no such elector on the lists");
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Checks if an elector is registered on a specific precinct.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @param precinctId           The electoral precinct ID.
     * @return True if the elector is registered on the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        String sql = "SELECT electoral_precinct_id FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    return precinctId == resultSet.getInt("electoral_precinct_id");
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Checks if an elector is eligible to receive a voting bulletin based on the previous receipt status.
     *
     * @param passportSeriesNumber The passport series number of the elector.
     * @return True if the elector can receive a bulletin, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT bulletin_has_been_received FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    return !resultSet.getBoolean("bulletin_has_been_received");
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Gets electors from the database based on age at a given election date.
     *
     * @param age          The age of the electors.
     * @param electionDate The date of the election.
     * @return A list of Elector objects within the specified age.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsByAge(int age, LocalDate electionDate) throws ConnectionException {
        return readElectors().stream()
                .filter(elector ->
                        electionDate.minusYears(age).isAfter(elector.birthday()) &&
                                electionDate.minusYears(age + 1).isBefore(elector.birthday()))
                .collect(Collectors.toList());
    }

    /**
     * Gets electors from the database based on a custom predicate.
     *
     * @param electorPredicate The custom predicate for filtering electors.
     * @return A list of Elector objects satisfying the given predicate.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    public List<Elector> getElectorsBy(Predicate<Elector> electorPredicate) throws ConnectionException {
        return readElectors().stream().filter(electorPredicate).collect(Collectors.toList());
    }

    /**
     * Gets electors from the database based on a specific attribute and value.
     *
     * @param sql   The SQL query for retrieving electors based on a specific attribute.
     * @param value The value used in the SQL query.
     * @return A list of Elector objects satisfying the specified attribute and value.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    private List<Elector> getElectors(String sql, Object value) throws ConnectionException {
        List<Elector> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(createElectorFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return result;
    }

    /**
     * Creates an Elector object from a ResultSet.
     *
     * @param resultSet The ResultSet containing elector information.
     * @return An Elector object created from the ResultSet.
     * @throws SQLException If there is an issue with ResultSet operations.
     */
    private Elector createElectorFromResultSet(ResultSet resultSet) throws SQLException {
        return new Elector(
                resultSet.getString("elector_passport_series_number"),
                resultSet.getString("elector_surname"),
                resultSet.getString("elector_firstname"),
                resultSet.getString("elector_patronymic"),
                resultSet.getInt("electoral_precinct_id"),
                LocalDate.parse(resultSet.getString("elector_birthday")),
                resultSet.getBoolean("bulletin_has_been_received"));
    }
}
