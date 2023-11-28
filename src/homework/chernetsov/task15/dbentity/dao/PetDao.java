package homework.chernetsov.task15.dbentity.dao;

import homework.chernetsov.task15.dbentity.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class PetDao {
    private final Connection connection;

    private final ClientPetRelationDao clientPetRelationDao;

    private final ClientDao clientDao;


    public PetDao(Connection connection, ClientPetRelationDao clientPetRelationDao, ClientDao clientDao) {
        this.connection = connection;
        this.clientPetRelationDao = clientPetRelationDao;
        this.clientDao = clientDao;
    }

    public void create(Pet pet) throws SQLException {
        String sql = "insert into Pet (pet_medical_card_number, pet_name, pet_age)" +
                "values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, pet.medCardNumber());
        statement.setString(2, pet.name());
        statement.setInt(3, pet.age());
        statement.execute();
    }

    public Pet read(int medCardNumber) throws SQLException {
        String sql = "select * from Pet where pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, medCardNumber);
        ResultSet result = statement.executeQuery();
        String name = null;
        int age = 0;
        while (result.next()) {
            name = result.getString("pet_name");
            age = result.getInt("pet_age");
        }
        return new Pet(medCardNumber,
                clientPetRelationDao.getClientsIdForPet(medCardNumber).stream().map((x) -> {
                    try {
                        return clientDao.read(x);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()),
                name,
                age);

    }

    public void update(Pet pet) throws SQLException {
        String sql = "update Pet SET pet_name = ?, pet_age = ? WHERE pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pet.name());
        statement.setInt(2, pet.age());
        statement.setInt(3, pet.medCardNumber());
        statement.executeUpdate();
    }

    public void delete(Pet pet) throws SQLException {
        String sql = "delete from Pet where pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, pet.medCardNumber());
        statement.execute();
    }
}
