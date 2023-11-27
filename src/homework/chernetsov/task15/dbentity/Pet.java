package homework.chernetsov.task15.dbentity;


public record Pet(int medCardNumber, Client owner, String name, int age) {
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
