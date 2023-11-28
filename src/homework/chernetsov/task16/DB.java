package homework.chernetsov.task16;

import homework.chernetsov.task16.dbentity.*;
import homework.chernetsov.task16.dbentity.dao.ClientDao;
import homework.chernetsov.task16.dbentity.dao.ClientPetRelationDao;
import homework.chernetsov.task16.dbentity.dao.PetDao;
import homework.chernetsov.task16.exceptions.ConnectionException;
import homework.chernetsov.task16.exceptions.InvalidClientPhoneException;

import java.sql.*;
import java.util.List;

public class DB implements AutoCloseable {
    private final Connection connection;
    private final PetDao petDao;
    private final ClientDao clientDao;
    private final ClientPetRelationDao clientPetRelationDao;

    public DB(Connection connection) throws ConnectionException {
        this.connection = connection;
        this.clientPetRelationDao = new ClientPetRelationDao(connection);
        this.clientDao = new ClientDao(connection, clientPetRelationDao);
        this.petDao = new PetDao(connection, clientPetRelationDao, clientDao);
        initTaskTables();
    }

    private void initTaskTables() throws ConnectionException {
        String tableClient = "CREATE TABLE Client( client_id INTEGER PRIMARY KEY NOT NULL," +
                " client_surname VARCHAR NOT NULL, client_firstname VARCHAR NOT NULL, client_phone VARCHAR NOT NULL)";
        String tablePet = "CREATE TABLE Pet( pet_medical_card_number INTEGER PRIMARY KEY NOT NULL," +
                "pet_name VARCHAR NOT NULL, pet_age INTEGER)";
        String tableClientPet = "CREATE TABLE Client_Pet( pet_medical_card_number INTEGER NOT NULL," +
                " client_id INTEGER NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(tableClient);
            statement.execute(tablePet);
            statement.execute(tableClientPet);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public void create(TupleDB tupleDB) throws SQLException {
        createPet(tupleDB.pet().name(), tupleDB.pet().age(), tupleDB.pet().clients());// clients and relations also added by this method
    }

    public List<String> findClientPhoneNumbersBy(Pet pet) {
        return petDao.findClientPhoneNumbersBy(pet);
    }//todo check

    public List<Pet> getAllPetsOf(Client client) {
        return petDao.getAllPetsOf(client);
    }//todo check

    public TupleDB readTupleDB(Integer petMedCardNumber, Integer clientId) throws SQLException {
        if (clientPetRelationDao.isExist(petMedCardNumber, clientId)) {
            return new TupleDB(findPet(petMedCardNumber), findClient(clientId));
        }
        return null;
    }

    public void update(TupleDB tupleDB) {
        updatePet(tupleDB.pet());
        updateClient(tupleDB.client());
    }

    public void delete(TupleDB tupleDB) throws SQLException {
        deletePet(tupleDB.pet().medCardNumber());
        deleteClient(tupleDB.client().id());
        clientPetRelationDao.delete(tupleDB);
    }

    public Client createClient(Client client) throws InvalidClientPhoneException {
        try {
            return clientDao.createClient(client);
        } catch (SQLException e) {
            //client already exist, throw InvalidClientPhoneException
            return null;
        }
    }

    public Client findClient(Integer id) {
        try {
            return clientDao.findClient(id);
        } catch (SQLException e) {
            //client is absent
            return null;
        }
    }

    public Client updateClient(Client client) {
        try {
            return clientDao.updateClient(client);
        } catch (SQLException e) {
            throw new RuntimeException("Critical error: " + e);
        }
    }

    public void deleteClient(Integer id) {
        try {
            clientDao.deleteClient(id);
        } catch (SQLException e) {
            //client already deleted
        }
    }

    public Pet createPet(String name, Integer age, List<Client> clients) {
        try {
            return petDao.createPet(name, age, clients);
        } catch (SQLException e) {
            // pet already exist
            return null;
        }
    }


    public void createPet(Pet pet) {
        try {
            petDao.create(pet);
        } catch (SQLException e) {
            // pet already exist
        }
    }


    public Pet findPet(Integer medCardNumber) {
        try {
            return petDao.findPet(medCardNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Critical error: " + e);
        }
    }

    public Pet updatePet(Pet pet) {
        try {
            return petDao.updatePet(pet);
        } catch (SQLException e) {
            //pet mb absent
            throw new RuntimeException("Critical error: " + e);
        }
    }

    public void deletePet(Integer medCardNumber) {
        try {
            petDao.deletePet(medCardNumber);
        } catch (SQLException e) {
            //pet already deleted
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
