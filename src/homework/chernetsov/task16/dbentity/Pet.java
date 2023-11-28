package homework.chernetsov.task16.dbentity;


import java.util.List;

public record Pet(Integer medCardNumber, List<Client> clients, String name, Integer age) {

    public boolean addClient(Client client) {
        return clients.contains(client) ? false : clients.add(client);
    }

    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "medCardNumber=" + medCardNumber +
                ", clients=" + clients +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
