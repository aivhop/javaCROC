package homework.chernetsov.ip;

import homework.chernetsov.ip.dbentity.ElectorDao;
import homework.chernetsov.ip.exceptions.ConnectionException;

import java.sql.*;

public class DataBaseElection implements AutoCloseable {
    private final Connection connection;

    private final ElectorDao electorDao;

    public Connection getConnection() {
        return connection;
    }

    public DataBaseElection(String url, String user, String password) throws ConnectionException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.electorDao = new ElectorDao(connection);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        createTablesElections();
    }

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    private void createTablesElections() throws ConnectionException {
        String tableElector = "CREATE TABLE Elector( elector_passport_series_number VARCHAR(10) PRIMARY KEY NOT NULL, " +
                "elector_surname VARCHAR NOT NULL, " +
                "elector_firstname VARCHAR NOT NULL, elector_patronymic VARCHAR, electoral_precinct_id INTEGER NOT NULL, " +
                "opportunity_vote BOOLEAN NOT NULL)";
        String tableEmployeeElectionCommission = "CREATE TABLE Employee( employee_id INTEGER PRIMARY KEY NOT NULL, " +
                "electoral_precinct_id INTEGER NOT NULL)";
        String tableEmployeeElectionCommissionElectoralPrecinctRelation = "CREATE TABLE Employee_Precinct( employee_id INTEGER NOT NULL, " +
                "electoral_precinct_id INTEGER NOT NULL)";
        String tableELectorElectoralPrecinctRelation = "CREATE TABLE Elector_Precinct( elector_passport_series_number VARCHAR(10) NOT NULL, " +
                "electoral_precinct_id INTEGER NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(tableElector);
            statement.execute(tableEmployeeElectionCommission);
            statement.execute(tableEmployeeElectionCommissionElectoralPrecinctRelation);
            statement.execute(tableELectorElectoralPrecinctRelation);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }


    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            String[] tables = {"Elector", "Employee", "Employee_Precinct", "Elector_Precinct"};
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
