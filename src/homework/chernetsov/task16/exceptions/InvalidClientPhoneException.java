package homework.chernetsov.task16.exceptions;

public class InvalidClientPhoneException extends Exception {
    private final String phone;

    public InvalidClientPhoneException(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Sorry, a such phone number already exist " + phone;
    }
}
