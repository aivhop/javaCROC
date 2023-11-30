package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElectorDao {

    private final Connection connection;

    public ElectorDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createElector(Elector elector) throws ConnectionException {
        String sql = "INSERT INTO Elector(elector_passport_series_number, " +
                "elector_surname, elector_firstname, elector_patronymic, " +
                "electoral_precinct_id, opportunity_vote) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, elector.passportSeriesNumber());
            statement.setString(2, elector.surname());
            statement.setString(3, elector.firstname());
            statement.setString(4, elector.patronymic());
            statement.setInt(5, elector.precinctId());
            statement.setBoolean(6, elector.opportunityVote());
            try {
                statement.execute();
            } catch (SQLException ex) {
                return false;
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return true;
    }

    public Elector findElector(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                return new Elector(passportSeriesNumber,
                        resultSet.getString("elector_surname"),
                        resultSet.getString("elector_firstname"),
                        resultSet.getString("elector_patronymic"),
                        resultSet.getInt("electoral_precinct_id"),
                        resultSet.getBoolean("opportunity_vote"));
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return null;
    }

    public boolean updateElector(Elector elector) throws ConnectionException {
        String sql = "UPDATE Elector SET elector_surname = ?, elector_firstname = ?," +
                " elector_patronymic = ?, electoral_precinct_id = ?, opportunity_vote = ? " +
                "WHERE elector_passport_series_number = " + elector.passportSeriesNumber();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, elector.surname());
            statement.setString(2, elector.firstname());
            statement.setString(3, elector.patronymic());
            statement.setInt(4, elector.precinctId());
            statement.setBoolean(5, elector.opportunityVote());
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

    public List<Elector> getElectorsByOpportunityVote(boolean hasOpportunityVote) throws ConnectionException {
        String sql = "SELECT * FROM Elector WHERE opportunity_vote = ?";
        return getElectors(sql, hasOpportunityVote);
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
        boolean isElectorCanVote = isElectorCanVote(passportSeriesNumber);
        if (!isElectorRegisteredOnPrecinct || !isElectorCanVote) {
            throw new ReceivingBulletinException(isElectorRegisteredOnPrecinct, isElectorRegisteredOnPrecinct);
        }
        String sql = "UPDATE Elector SET opportunity_vote = ? " +
                "WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, false);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public boolean isElectorCanReceiveBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException {
        String sql = "SELECT opportunity_vote FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return resultSet.getBoolean("opportunity_vote") &&
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

    public boolean isElectorCanVote(String passportSeriesNumber) throws ConnectionException {
        String sql = "SELECT opportunity_vote FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return resultSet.getBoolean("opportunity_vote");
                }
                throw new InvalidElectorPassport(passportSeriesNumber, "There is no such elector on the lists");
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

/*    public boolean isElectorCanVote(String passportSeriesNumber, Integer precinctId) throws ConnectionException {
        String sql = "SELECT opportunity_vote FROM Elector WHERE elector_passport_series_number = " + passportSeriesNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    return resultSet.getBoolean("opportunity_vote") &&
                            (precinctId == null || precinctId.equals(resultSet.getInt("electoral_precinct_id")));
                }
                throw new InvalidElectorPassport(passportSeriesNumber, "There is no such elector on the lists");
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }*/

/*    String tableElector = "CREATE TABLE Elector( elector_passport_series_number VARCHAR(10) PRIMARY KEY NOT NULL, " +
            "elector_surname VARCHAR NOT NULL, " +
            "elector_firstname VARCHAR NOT NULL, elector_patronymic VARCHAR, electoral_precinct_id INTEGER NOT NULL, " +
            "opportunity_vote BOOLEAN NOT NULL)";*/

    private List<Elector> getElectors(String sql, Object value) throws ConnectionException {
        List<Elector> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setObject(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Elector(
                            resultSet.getString("elector_passport_series_number"),
                            resultSet.getString("elector_surname"),
                            resultSet.getString("elector_firstname"),
                            resultSet.getString("elector_patronymic"),
                            resultSet.getInt("electoral_precinct_id"),
                            resultSet.getBoolean("opportunity_vote")));
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return result;
    }
}
