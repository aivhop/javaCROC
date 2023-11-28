package homework.chernetsov.task16.dbentity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientPetRelationDao {
    private final Connection connection;

    public ClientPetRelationDao(Connection connection) {
        this.connection = connection;
    }

    public List<Integer> getClientsIdForPet(int petMedCardNumber) throws SQLException {
        String sql = "SELECT client_id FROM Client_Pet WHERE pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, petMedCardNumber);
        ResultSet resultSet = statement.executeQuery();
        List<Integer> clientsId = new ArrayList<>();
        while (resultSet.next()) {
            clientsId.add(resultSet.getInt("client_id"));
        }
        return clientsId;
    }


    public void create(TupleDB tupleDB) throws SQLException {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tupleDB.pet().medCardNumber());
        statement.setInt(2, tupleDB.client().id());
        statement.execute();
    }

    public boolean isExist(int petMedCardNumber, int clientId) throws SQLException {
        String sql = "select 1 from Client_Pet where pet_medical_card_number = ? AND client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, petMedCardNumber);
        statement.setInt(2, clientId);
        return statement.execute();//todo check correct work
    }


/*    public static void update(ClientPetRelation clientPetRelation, Connection connection) throws SQLException {
        String sql = "update Client_Pet SET client_id = ? WHERE pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, clientPetRelation.clientId());
        statement.setInt(2, clientPetRelation.petMedCardNumber());
        statement.executeUpdate();
        //todo зачем нам обновлять запись, если для того чтобы ее идентифицировать нужно знать оба значения
    }*/

    public void delete(TupleDB tupleDB) throws SQLException {
        String sql = "delete from Client_Pet where pet_medical_card_number = ?, client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tupleDB.pet().medCardNumber());
        statement.setInt(1, tupleDB.client().id());
        statement.execute();
    }

}
