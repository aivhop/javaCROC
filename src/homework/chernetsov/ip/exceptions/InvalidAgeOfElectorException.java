package homework.chernetsov.ip.exceptions;

public class InvalidAgeOfElectorException extends RuntimeException {
    private final int legalAge;
    private final int inputAge;

    public InvalidAgeOfElectorException(int legalAge, int inputAge) {
        this.legalAge = legalAge;
        this.inputAge = inputAge;
    }

    @Override
    public String toString() {
        return "Sorry, the elector must be of legal age " + legalAge + ", not " + inputAge;
    }
}
