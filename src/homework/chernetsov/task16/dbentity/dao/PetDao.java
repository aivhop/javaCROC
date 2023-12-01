package homework.chernetsov.task16.dbentity.dao;

import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.sql.*;
import java.util.List;

public class PetDao {
    private static Integer medCardLast = 0;
    private final Connection connection;

    private final ClientPetRelationDao clientPetRelationDao;

    private final ClientDao clientDao;


    public PetDao(Connection connection, ClientPetRelationDao clientPetRelationDao, ClientDao clientDao) {
        this.connection = connection;
        this.clientPetRelationDao = clientPetRelationDao;
        this.clientDao = clientDao;
    }

    public Pet createPet(String name, Integer age, List<Client> clients) throws SQLException {
        medCardLast++;
        Pet pet = new Pet(medCardLast, clients, name, age);
        clientDao.addOwnersOfPet(pet);
        clientPetRelationDao.updateWithPetOwners(pet);
        String sql = "insert into Pet (pet_medical_card_number, pet_name, pet_age)" +
                "values(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medCardLast);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.execute();
            return pet;
        }
    }

    public Pet findPet(Integer medCardNumber) throws SQLException {
        String sql = "select * from Pet where pet_medical_card_number = " + medCardNumber;
        Pet pet = null;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery(sql)) {
                if (result.next()) {
                    pet = new Pet(medCardNumber,
                            clientPetRelationDao.getClientsForPet(medCardNumber),
                            result.getString("pet_name"),
                            result.getInt("pet_age"));
                }
            }
        }
        return pet;
    }

    public Pet updatePet(Pet pet) throws SQLException {
        Pet oldValue = findPet(pet.medCardNumber());
        clientPetRelationDao.clearRelationsForPet(oldValue.medCardNumber());
        for (Client client : pet.clients()) {
            try {
                clientDao.createClient(client);
            } catch (SQLException | InvalidClientPhoneException e) {
                //client already exist
                clientDao.updateClient(client);
            }
        }
        clientPetRelationDao.updateWithPetOwners(pet);
        String sql = "update Pet SET pet_name = ?, pet_age = ? WHERE pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.name());
            statement.setInt(2, pet.age());
            statement.setInt(3, pet.medCardNumber());
            statement.executeUpdate();
            return oldValue;
        }
    }

    public void deletePet(Integer medCardNumber) throws SQLException {
        String sql = "delete from Pet where pet_medical_card_number = " + medCardNumber;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            clientPetRelationDao.clearRelationsForPet(medCardNumber);
        }
    }

    public void create(Pet pet) throws SQLException {
        clientDao.addOwnersOfPet(pet);
        clientPetRelationDao.updateWithPetOwners(pet);
        String sql = "insert into Pet (pet_medical_card_number, pet_name, pet_age)" +
                "values(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.medCardNumber());
            statement.setString(2, pet.name());
            statement.setInt(3, pet.age());
            statement.execute();
            medCardLast = pet.medCardNumber();
        }
    }
}
