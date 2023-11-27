package homework.chernetsov.task15;

import homework.chernetsov.task15.dbentity.Client;
import homework.chernetsov.task15.dbentity.ClientPetRelation;
import homework.chernetsov.task15.dbentity.Pet;
import homework.chernetsov.task15.dbentity.TupleDB;
import homework.chernetsov.task15.exceptions.ConnectionException;

import java.sql.*;

public class DB implements AutoCloseable {
    private final Connection connection;

    public DB(Connection connection) throws ConnectionException {
        this.connection = connection;
        initTaskTables();
    }

    private void initTaskTables() throws ConnectionException {
        String tableClient = "CREATE TABLE Client( client_id INTEGER PRIMARY KEY," +
                " client_surname VARCHAR, client_firstname VARCHAR, client_phone VARCHAR)";
        String tablePet = "CREATE TABLE Pet( pet_medical_card_number INTEGER PRIMARY KEY," +
                " pet_owner INTEGER, pet_name VARCHAR, pet_age INTEGER)";
        String tableClientPet = "CREATE TABLE Client_Pet( pet_medical_card_number INTEGER," +
                " client_id INTEGER)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(tableClient);
            statement.execute(tablePet);
            statement.execute(tableClientPet);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }


    public void create(TupleDB tupleDB) {
        Pet pet = tupleDB.pet();
        Client client = tupleDB.client();
        ClientPetRelation clientPetRelation = new ClientPetRelation(pet.medCardNumber(), client.id());
        create(pet);
        create(client);
        create(clientPetRelation);
    }

    public TupleDB readTupleDB(int petMedCardNumber) {
        Pet pet = readPet(petMedCardNumber);
        return new TupleDB(pet, pet.owner());
    }

    public void update(TupleDB tupleDB) {
        int petMedCardNumber = tupleDB.pet().medCardNumber();
        int clientId = tupleDB.client().id();
        update(tupleDB.pet());
        update(tupleDB.client());
        update(new ClientPetRelation(petMedCardNumber, clientId));
    }

    public void delete(TupleDB tupleDB) {
        int petMedCardNumber = tupleDB.pet().medCardNumber();
        int clientId = tupleDB.client().id();
        delete(tupleDB.pet());
        delete(tupleDB.client());
        delete(new ClientPetRelation(petMedCardNumber, clientId));
    }

    private void create(ClientPetRelation clientPetRelation) {
        String sql = "insert into Client_Pet (pet_medical_card_number, client_id)" +
                "values(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.petMedCardNumber());
            statement.setInt(2, clientPetRelation.clientId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientPetRelation readClientPetRelation(int petMedCardNumber) {
        String sql = "select * from Client_Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petMedCardNumber);
            ResultSet result = statement.executeQuery(sql);
            return new ClientPetRelation(petMedCardNumber, result.getInt("client_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(ClientPetRelation clientPetRelation) {
        String sql = "update Client_Pet SET client_id = ? WHERE pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.clientId());
            statement.setInt(2, clientPetRelation.petMedCardNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(ClientPetRelation clientPetRelation) {
        String sql = "delete from Client_Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientPetRelation.petMedCardNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void create(Client client) {
        String sql = "insert into Client (client_id, client_surname, client_firstname, client_phone)" +
                "values(?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.id());
            statement.setString(2, client.surname());
            statement.setString(3, client.firstname());
            statement.setString(4, client.phone());
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
            throw new RuntimeException(e);
        }
    }

    private void update(Client client) {
        String sql = "update Client SET client_surname = ?, client_firstname = ?, client_phone = ? WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.surname());
            statement.setString(2, client.firstname());
            statement.setString(3, client.phone());
            statement.setInt(4, client.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Client client) {
        String sql = "delete from Client where client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Pet pet) {
        String sql = "insert into Pet (pet_medical_card_number, pet_owner, pet_name, pet_age)" +
                "values(?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.medCardNumber());
            statement.setInt(2, pet.owner().id());
            statement.setString(3, pet.name());
            statement.setInt(4, pet.age());
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
            throw new RuntimeException(e);
        }
    }

    private void update(Pet pet) {
        String sql = "update Pet SET pet_owner = ?, pet_name = ?, pet_age = ? WHERE pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.owner().id());
            statement.setString(2, pet.name());
            statement.setInt(3, pet.age());
            statement.setInt(4, pet.medCardNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Pet pet) {
        String sql = "delete from Pet where pet_medical_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pet.medCardNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            String[] tables = {"Client", "Pet", "Client_Pet"};
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
