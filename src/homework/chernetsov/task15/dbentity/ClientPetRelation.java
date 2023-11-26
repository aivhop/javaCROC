package homework.chernetsov.task15.dbentity;

public class ClientPetRelation {
    private final int petMedCardNumber;
    private final int clientId;

    public ClientPetRelation(int petMedCardNumber, int clientId) {
        this.petMedCardNumber = petMedCardNumber;
        this.clientId = clientId;
    }

    public int getPetMedCardNumber() {
        return petMedCardNumber;
    }

    public int getClientId() {
        return clientId;
    }

}
