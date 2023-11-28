package homework.chernetsov.task16;

import homework.chernetsov.task16.dbentity.*;
import homework.chernetsov.task16.exceptions.ConnectionException;

import java.sql.*;

public class DB implements AutoCloseable {
    private final Connection connection;
    private final PetDao petDao;
    private final ClientDao clientDao;
    private final ClientPetRelationDao clientPetRelationDao;

    public DB(Connection connection) throws ConnectionException {
        this.connection = connection;
        this.clientDao = new ClientDao(connection);
        this.clientPetRelationDao = new ClientPetRelationDao(connection);
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
//todo пусть добавление питомца будет учитывать клиентов

    public void create(TupleDB tupleDB) throws SQLException {
        Pet pet = tupleDB.pet();
        Client client = tupleDB.client();
        createPet(pet);
        createClient(client);
        clientPetRelationDao.create(tupleDB);
    }

    public TupleDB readTupleDB(int petMedCardNumber, int clientId) throws SQLException {
        if (clientPetRelationDao.isExist(petMedCardNumber, clientId)) {
            return new TupleDB(readPet(petMedCardNumber), readClient(clientId));
        }
        return null;
    }

    public void update(TupleDB tupleDB) {
        updatePet(tupleDB.pet());
        updateClient(tupleDB.client());
    }

    public void delete(TupleDB tupleDB) throws SQLException {
        deletePet(tupleDB.pet());
        deleteClient(tupleDB.client());
        clientPetRelationDao.delete(tupleDB);
    }

    private void createClient(Client client) {
        try {
            clientDao.create(client);
        } catch (SQLException e) {
            // client already exist
        }
    }

    private Client readClient(int id) {
        try {
            return clientDao.read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateClient(Client client) {
        try {
            clientDao.update(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteClient(Client client) {
        try {
            clientDao.delete(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPet(Pet pet) {
        try {
            petDao.create(pet);
            //todo связи проверить обновить мб и тд
        } catch (SQLException e) {
            // pet already exist
        }
    }

    private Pet readPet(int medCardNumber) {
        try {
            return petDao.read(medCardNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePet(Pet pet) {
        try {
            petDao.update(pet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePet(Pet pet) {
        try {
            petDao.delete(pet);//todo telegram
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
