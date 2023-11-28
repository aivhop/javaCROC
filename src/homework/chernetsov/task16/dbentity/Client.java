package homework.chernetsov.task16.dbentity;


public record Client(Integer id, String surname, String firstname, String phone) {

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
