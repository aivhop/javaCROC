package homework.chernetsov.task16.dbentity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDao {
    private final Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Client client) throws SQLException {
        String sql = "insert into Client (client_id, client_surname, client_firstname, client_phone)" +
                "values(?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, client.id());
        statement.setString(2, client.surname());
        statement.setString(3, client.firstname());
        statement.setString(4, client.phone());
        statement.execute();

    }

    public Client read(int id) throws SQLException {
        String sql = "select * from Client where client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        String surname = null;
        String firstname = null;
        String phone = null;
        while (result.next()) {
            surname = result.getString("client_surname");
            firstname = result.getString("client_firstname");
            phone = result.getString("client_phone");
        }
        return new Client(id, surname, firstname, phone);
    }

    public void update(Client client) throws SQLException {
        String sql = "update Client SET client_surname = ?, client_firstname = ?, client_phone = ? WHERE client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, client.surname());
        statement.setString(2, client.firstname());
        statement.setString(3, client.phone());
        statement.setInt(4, client.id());
        statement.executeUpdate();

    }

    public void delete(Client client) throws SQLException {
        String sql = "delete from Client where client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, client.id());
        statement.execute();
    }

}
