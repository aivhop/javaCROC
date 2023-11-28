package homework.chernetsov.task16.dbentity.dao;

import homework.chernetsov.task16.dbentity.Client;
import homework.chernetsov.task16.dbentity.Pet;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PetDao {
    private Integer medCardLast = 0;
    private final Connection connection;

    private final ClientPetRelationDao clientPetRelationDao;

    private final ClientDao clientDao;


    public PetDao(Connection connection, ClientPetRelationDao clientPetRelationDao, ClientDao clientDao) {
        this.connection = connection;
        this.clientPetRelationDao = clientPetRelationDao;
        this.clientDao = clientDao;
    }

    public List<Pet> getAllPetsOf(Client client) {
        try {
            return clientPetRelationDao.getPetMedCardNumbersForClient(client.id()).stream()
                    .map(medCardNumber -> {
                        try {
                            return this.findPet(medCardNumber);
                        } catch (SQLException e) {
                            throw new RuntimeException("Critical error: " + e);
                        }
                    }).toList();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }


    public List<String> findClientPhoneNumbersBy(Pet pet) {
        try {
            return clientPetRelationDao.getClientsIdForPet(pet.medCardNumber()).stream()
                    .map(id -> {
                        try {
                            return clientDao.findClient(id);
                        } catch (SQLException e) {
                            throw new RuntimeException("Critical error: " + e);
                        }
                    }).map(Client::phone).toList();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public Pet createPet(String name, Integer age, List<Client> clients) throws SQLException {
        medCardLast++;
        String sql = "insert into Pet (pet_name, pet_age)" +
                "values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, age);
        statement.execute();
        Pet pet = new Pet(medCardLast, clients, name, age);
        clientDao.addOwnersOfPet(pet);
        clientPetRelationDao.updateWithPetOwners(pet);
        return pet;
    }


/*    public void create(Pet pet) throws SQLException {
        String sql = "insert into Pet (pet_medical_card_number, pet_name, pet_age)" +
                "values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, pet.medCardNumber());
        statement.setString(2, pet.name());
        statement.setInt(3, pet.age());
        statement.execute();
        clientDao.addOwnersOfPet(pet);
        clientPetRelationDao.updateWithPetOwners(pet);
    }*/

    public Pet findPet(Integer medCardNumber) throws SQLException {
        String sql = "select * from Pet where pet_medical_card_number = " + medCardNumber;
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        String name = null;
        int age = 0;
        while (result.next()) {
            name = result.getString("pet_name");
            age = result.getInt("pet_age");
        }
        return new Pet(medCardNumber,
                clientPetRelationDao.getClientsIdForPet(medCardNumber).stream().map((x) -> {
                    try {
                        return clientDao.findClient(x);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()),
                name,
                age);

    }

    public Pet updatePet(Pet pet) throws SQLException {
        for (Client client : pet.clients()) {
            try {
                clientDao.createClient(client);
            } catch (SQLException | InvalidClientPhoneException e) {
                //client already exist
                clientDao.updateClient(client);
            }
        }
        clientPetRelationDao.updateWithPetOwners(pet);

        Pet oldValue = findPet(pet.medCardNumber());
        String sql = "update Pet SET pet_name = ?, pet_age = ? WHERE pet_medical_card_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pet.name());
        statement.setInt(2, pet.age());
        statement.setInt(3, pet.medCardNumber());
        statement.executeUpdate();
        return oldValue;
    }

    public void deletePet(Integer medCardNumber) throws SQLException {
        String sql = "delete from Pet where pet_medical_card_number = " + medCardNumber;
        Statement statement = connection.createStatement();
        statement.execute(sql);
        clientPetRelationDao.clearRelationsForPet(medCardNumber);
    }
}
