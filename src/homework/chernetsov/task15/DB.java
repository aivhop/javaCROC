package homework.chernetsov.task15;

import homework.chernetsov.task15.exceptions.ConnectionException;
import homework.chernetsov.task15.exceptions.CreationTableException;

import java.sql.*;

public class DB {
    private Connection connection;

    public DB(Connection connection) throws ConnectionException {
        this.connection = connection;
        init();
    }

    private void init() throws ConnectionException {
        String tableClient = "create table Client( client_id integer primary key," +
                " client_surname varchar, client_firstname varchar, client_phone varchar)";
        String tablePet = "create table Pet( pet_medical_card_number integer primary key," +
                " pet_owner integer, pet_name varchar, pet_age integer)";
        String tableClientPet = "create table Client_Pet( pet_medical_card_number integer," +
                " client_id integer)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(tableClient);
            statement.execute(tablePet);
            statement.execute(tableClientPet);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }




    public void create(TupleDB tupleDB) {//todo modificator for tuple
        Pet pet = tupleDB.getPet();
        Client client = tupleDB.getClient();
        ClientPetRelation clientPetRelation = new ClientPetRelation(pet.getMedCardNumber(), client.getId());
        create(pet);
        create(client);
        create(clientPetRelation);
    }

    public TupleDB readTupleDB(int petMedCardNumber) {
        Pet pet = readPet(petMedCardNumber);
        return new TupleDB(pet, pet.getOwner());
    }

    public void update(TupleDB tupleDB) {
        int petMedCardNumber = tupleDB.getPet().getMedCardNumber();
        int clientId = tupleDB.getClient().getId();
        update(tupleDB.getPet());
        update(tupleDB.getClient());
        update(new ClientPetRelation(petMedCardNumber, clientId));
    }

    public void delete(TupleDB tupleDB) {
        int petMedCardNumber = tupleDB.getPet().getMedCardNumber();
        int clientId = tupleDB.getClient().getId();
        delete(tupleDB.getPet());
        delete(tupleDB.getClient());
        delete(new ClientPetRelation(petMedCardNumber, clientId));
    }

    private void create(ClientPetRelation clientPetRelation) {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.getPetMedCardNumber());
            statement.setInt(2, clientPetRelation.getClientId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);//todo custom
        }
    }

    private ClientPetRelation readClientPetRelation(int petMedCardNumber) {
        String sql = "select * from Client_Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petMedCardNumber);
            ResultSet result = statement.executeQuery(sql);
            return new ClientPetRelation(petMedCardNumber, result.getInt("client_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);//todo custom
        }
    }

    private void update(ClientPetRelation clientPetRelation) {
        String sql = "update Client_Pet SET client_id = ? WHERE pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.getClientId());
            statement.setInt(2, clientPetRelation.getPetMedCardNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(ClientPetRelation clientPetRelation) {
        String sql = "delete from Client_Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.getPetMedCardNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void create(Client client) {
        String sql = "insert into Client (client_id, client_surname, client_firstname, client_phone)" +
                "values(?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.getId());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getFirstname());
            statement.setString(4, client.getPhone());
            statement.execute();
        } catch (SQLException e) {
            // client already exist
        }
    }

    private Client readClient(int id) {
        String sql = "select * from Client where client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(sql);
            return new Client(id, result.getString("client_surname")
                    , result.getString("client_firstname"),
                    result.getString("client_phone"));
        } catch (SQLException e) {
            throw new RuntimeException(e);//todo custom
        }
    }

    private void update(Client client) {
        String sql = "update Client SET client_surname = ?, client_firstname = ?, client_phone = ? WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getSurname());
            statement.setString(2, client.getFirstname());
            statement.setString(3, client.getPhone());
            statement.setInt(4, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Client client) {
        String sql = "delete from Client where client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Pet pet) {
        String sql = "insert into Pet (pet_medical_card_number, pet_owner, pet_name, pet_age)" +
                "values(?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.getMedCardNumber());
            statement.setInt(2, pet.getOwner().getId());
            statement.setString(3, pet.getName());
            statement.setInt(4, pet.getAge());
            statement.execute();
        } catch (SQLException e) {
            // pet already exist
        }
    }

    private Pet readPet(int medCardNumber) {
        String sql = "select * from Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medCardNumber);
            ResultSet result = statement.executeQuery(sql);
            return new Pet(medCardNumber, readClient(result.getInt("pet_owner"))
                    , result.getString("pet_name"),
                    result.getInt("pet_age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);//todo custom
        }
    }

    private void update(Pet pet) {
        String sql = "update Pet SET pet_owner = ?, pet_name = ?, pet_age = ? WHERE pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.getOwner().getId());
            statement.setString(2, pet.getName());
            statement.setInt(3, pet.getAge());
            statement.setInt(4, pet.getMedCardNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Pet pet) {
        String sql = "delete from Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.getMedCardNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {


        return null;
    }
}
