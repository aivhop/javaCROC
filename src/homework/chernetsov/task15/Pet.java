package homework.chernetsov.task15;


public class Pet {
    private final int medCardNumber;
    private Client owner;
    private final String name;
    private int age;

    public Pet(int medCardNumber, Client owner, String name, int age) {
        this.medCardNumber = medCardNumber;
        this.owner = owner;
        this.name = name;
        this.age = age;
    }

    public int getMedCardNumber() {
        return medCardNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "medCardNumber=" + medCardNumber +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
