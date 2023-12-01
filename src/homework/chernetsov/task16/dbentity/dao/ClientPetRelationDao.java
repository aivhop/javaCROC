package homework.chernetsov.task16.dbentity.dao;

import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientPetRelationDao {
    private final Connection connection;

    public ClientPetRelationDao(Connection connection) {
        this.connection = connection;
    }

    public List<Client> getClientsForPet(Integer petMedCardNumber) throws SQLException {
        String sql = "SELECT * FROM Client_Pet cp JOIN Client c ON cp.client_id = c.client_id WHERE pet_medical_card_number = " + petMedCardNumber;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                List<Client> clients = new ArrayList<>();
                while (resultSet.next()) {
                    clients.add(new Client(
                            resultSet.getInt("client_id"),
                            resultSet.getString("client_surname"),
                            resultSet.getString("client_firstname"),
                            resultSet.getString("client_phone"))
                    );
                }
                return clients;
            }
        }
    }

    public List<Pet> getPetsForClient(Integer id) throws SQLException {
        String sql = "SELECT * FROM Client_Pet cp JOIN Pet p ON p.pet_medical_card_number = cp.pet_medical_card_number  WHERE client_id = " + id;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                List<Pet> pets = new ArrayList<>();
                while (resultSet.next()) {
                    Integer petMedCardNumber = resultSet.getInt("pet_medical_card_number");
                    pets.add(new Pet(
                            petMedCardNumber,
                            getClientsForPet(petMedCardNumber),
                            resultSet.getString("pet_name"),
                            resultSet.getInt("pet_age")
                    ));
                }
                return pets;
            }
        }
    }

    public List<String> findClientPhoneNumbersBy(Pet pet) throws SQLException {
        return getClientsForPet(pet.medCardNumber()).stream().map(Client::phone).toList();
    }


    public void updateWithPetOwners(Pet pet) {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, pet.medCardNumber());
            for (Client client : pet.clients()) {
                statement.setInt(2, client.id());
                try {
                    statement.execute();
                } catch (SQLException ignored) {
                    // relation already exist
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void create(Integer petMedCardNumber, Integer clientId) throws SQLException {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petMedCardNumber);
            statement.setInt(2, clientId);
            statement.execute();
        }
    }

    public boolean isExist(Integer petMedCardNumber, Integer clientId) throws SQLException {
        String sql = "select 1 from Client_Pet where pet_medical_card_number = ? AND client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petMedCardNumber);
            statement.setInt(2, clientId);
            return statement.execute();
        }
    }


    public void delete(Integer petMedCardNumber, Integer clientId) throws SQLException {
        String sql = "delete from Client_Pet where pet_medical_card_number = ? AND client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petMedCardNumber);
            statement.setInt(2, clientId);
            statement.execute();
        }
    }

    public void clearRelationsForPet(Integer medCardNumber) throws SQLException {
        String sql = "DELETE FROM Client_Pet WHERE pet_medical_card_number = " + medCardNumber;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void clearRelationsForClient(Integer id) throws SQLException {
        String sql = "DELETE FROM Client_Pet WHERE client_id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
