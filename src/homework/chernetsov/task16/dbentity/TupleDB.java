package homework.chernetsov.task16.dbentity;

public record TupleDB(Pet pet, Client client) {

    public TupleDB {
        if (!pet.clients().contains(client)) {
            throw new IllegalArgumentException("This record is not valid, the pet must belong to the client");
        }

    }
    @Override
    public String toString() {
        return "TupleDB{" +
                "pet=" + pet +
                ", client=" + client +
                '}';
    }
}
