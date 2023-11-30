package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.CreationElectorException;
import homework.chernetsov.ip.exceptions.ConnectionException;

import java.sql.*;

public class ElectorDao {

    private final Connection connection;

    public ElectorDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createElector(Elector elector) throws ConnectionException, CreationElectorException {
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
                //throw new CreationElectorException("Sorry, such elector already exist", elector);
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
        try (Statement statement = connection.createStatement()){
            return statement.executeUpdate(sql) == 1;
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

/*    String tableElector = "CREATE TABLE Elector( elector_passport_series_number VARCHAR(10) PRIMARY KEY NOT NULL, " +
            "elector_surname VARCHAR NOT NULL, " +
            "elector_firstname VARCHAR NOT NULL, elector_patronymic VARCHAR, electoral_precinct_id INTEGER NOT NULL, " +
            "opportunity_vote BOOLEAN NOT NULL)";*/
}
