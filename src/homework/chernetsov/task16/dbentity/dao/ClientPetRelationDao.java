package homework.chernetsov.task16.dbentity.dao;

import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.dbentity.TupleDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientPetRelationDao {
    private final Connection connection;

    public ClientPetRelationDao(Connection connection) {
        this.connection = connection;
    }

    public List<Integer> getClientsIdForPet(Integer petMedCardNumber) throws SQLException {
        String sql = "SELECT client_id FROM Client_Pet WHERE pet_medical_card_number = " + petMedCardNumber;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Integer> clientsId = new ArrayList<>();
        while (resultSet.next()) {
            clientsId.add(resultSet.getInt("client_id"));
        }
        return clientsId;
    }
    public List<Integer> getPetMedCardNumbersForClient(Integer id) throws SQLException {
        String sql = "SELECT pet_medical_card_number FROM Client_Pet WHERE client_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Integer> petMedCardNumbers = new ArrayList<>();
        while (resultSet.next()) {
            petMedCardNumbers.add(resultSet.getInt("pet_medical_card_number"));
        }
        return petMedCardNumbers;
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


    public void create(TupleDB tupleDB) throws SQLException {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tupleDB.pet().medCardNumber());
        statement.setInt(2, tupleDB.client().id());
        statement.execute();
    }

    public boolean isExist(Integer petMedCardNumber, Integer clientId) throws SQLException {
        String sql = "select 1 from Client_Pet where pet_medical_card_number = ? AND client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, petMedCardNumber);
        statement.setInt(2, clientId);
        return statement.execute();
    }


    public void delete(TupleDB tupleDB) throws SQLException {
        String sql = "delete from Client_Pet where pet_medical_card_number = ? AND client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tupleDB.pet().medCardNumber());
        statement.setInt(1, tupleDB.client().id());
        statement.execute();
    }

    public void clearRelationsForPet(Integer medCardNumber) throws SQLException {
        String sql = "DELETE FROM Client_Pet WHERE pet_medical_card_number = " + medCardNumber;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void clearRelationsForClient(Integer id) throws SQLException {
        String sql = "DELETE FROM Client_Pet WHERE client_id = " + id;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
}
