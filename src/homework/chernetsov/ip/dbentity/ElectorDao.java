package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ElectorDao {

    private final Connection connection;

    public ElectorDao(Connection connection) {
        this.connection = connection;
    }

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

    public boolean createElector(Elector elector) throws ConnectionException {
        return createElectors(List.of(elector)) == 1;
    }

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

    public boolean deleteElector(String passportSeriesNumber) throws ConnectionException {
        String sql = "DELETE FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql) == 1;
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_firstname = ?";
        return getElectors(sql, firstname);
    }

    public List<Elector> getElectorsBySurname(String surname) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_surname = ?";
        return getElectors(sql, surname);
    }

    public List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_patronymic = ?";
        return getElectors(sql, patronymic);
    }

    public List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE electoral_precinct_id = ?";
        return getElectors(sql, precinctId);
    }

    public List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE opportunity_vote = ?";
        return getElectors(sql, bulletinHasBeenReceived);
    }

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

    public boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        String sql = "SELECT bulletin_has_been_received FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
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

    public boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException {
        String sql = "SELECT electoral_precinct_id FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return precinctId == resultSet.getInt("electoral_precinct_id");
                }
                throw new InvalidElectorPassport(passportSeriesNumber, "There is no such elector on the lists");
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT bulletin_has_been_received FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return !resultSet.getBoolean("bulletin_has_been_received");
                }
                throw new InvalidElectorPassport(passportSeriesNumber, "There is no such elector on the lists");
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public List<Elector> getElectorsByAge(int age, LocalDate electionDate) throws ConnectionException {
        return readElectors().stream()
                .filter(elector ->
                        electionDate.minusYears(age).isAfter(elector.birthday()) &&
                                electionDate.minusYears(age + 1).isBefore(elector.birthday()))
                .collect(Collectors.toList());
    }

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
