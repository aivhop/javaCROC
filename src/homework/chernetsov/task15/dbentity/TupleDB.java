package homework.chernetsov.task15.dbentity;

public class TupleDB {
    private final Pet pet;
    private final Client client;

    public TupleDB(Pet pet, Client client) {
        this.pet = pet;
        this.client = client;
    }

    public Pet getPet() {
        return pet;
    }

    public Client getClient() {
        return client;
    }

    //1,Иванова,Ксения,+79995554433,1,Котик,2
}
