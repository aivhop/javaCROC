package homework.chernetsov.task16.dbentity.dao;

import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.sql.*;

public class ClientDao {
    private final Connection connection;
    private final ClientPetRelationDao clientPetRelationDao;

    public ClientDao(Connection connection, ClientPetRelationDao clientPetRelationDao) {
        this.connection = connection;
        this.clientPetRelationDao = clientPetRelationDao;
    }

    public Client createClient(Client client) throws SQLException, InvalidClientPhoneException {
        if (isExistPhone(client.phone())) {
            throw new InvalidClientPhoneException(client.phone());
        }
        String sql = "insert into Client (client_id, client_surname, client_firstname, client_phone)" +
                "values(?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, client.id());
        statement.setString(2, client.surname());
        statement.setString(3, client.firstname());
        statement.setString(4, client.phone());
        statement.execute();
        return client;
    }


    public void addOwnersOfPet(Pet pet) {
        for (Client client : pet.clients()) {
            try {
                createClient(client);
            } catch (InvalidClientPhoneException | SQLException e) {
                // client already exist-ignore
            }
        }
    }

    public Client findClient(Integer id) throws SQLException {
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

    public Client updateClient(Client client) throws SQLException {
        Client oldValue = findClient(client.id());
        String sql = "update Client SET client_surname = ?, client_firstname = ?, client_phone = ? WHERE client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, client.surname());
        statement.setString(2, client.firstname());
        statement.setString(3, client.phone());
        statement.setInt(4, client.id());
        statement.executeUpdate();
        return oldValue;
    }

    public void deleteClient(Integer id) throws SQLException {
        String sql = "delete from Client where client_id = " + id;
        Statement statement = connection.createStatement();
        statement.execute(sql);
        clientPetRelationDao.clearRelationsForClient(id);
    }

    public boolean isExistPhone(String phone) {
        String sql = "select * from Client where client_phone = " + phone;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
