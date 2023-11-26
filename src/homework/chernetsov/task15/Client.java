package homework.chernetsov.task15;

public class Client {
    private final int id;
    private String surname;

    private String firstname;

    private String phone;

    public Client(int id, String surname, String firstname, String phone) {
        this.id = id;
        this.surname = surname;
        this.firstname = firstname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
