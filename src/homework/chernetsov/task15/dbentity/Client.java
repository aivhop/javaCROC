package homework.chernetsov.task15.dbentity;

public record Client(int id, String surname, String firstname, String phone) {

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
