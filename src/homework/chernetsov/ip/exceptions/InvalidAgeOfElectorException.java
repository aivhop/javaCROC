package homework.chernetsov.ip.exceptions;

import java.time.LocalDate;

public class InvalidAgeOfElectorException extends RuntimeException {
    private final int legalAge;
    private final LocalDate inputBirthday;

    public InvalidAgeOfElectorException(int legalAge, LocalDate inputBirthday) {
        this.legalAge = legalAge;
        this.inputBirthday = inputBirthday;
    }

    public int getLegalAge() {
        return legalAge;
    }

    public LocalDate getInputBirthday() {
        return inputBirthday;
    }


    @Override
    public String toString() {
        return "Sorry, the elector must be of legal age " + legalAge + ", but his birthdate " + inputBirthday;
    }
}
