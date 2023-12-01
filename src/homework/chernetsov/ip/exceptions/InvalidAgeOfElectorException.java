package homework.chernetsov.ip.exceptions;

import java.time.LocalDate;

/**
 * Exception thrown to indicate that an elector is not of legal age.
 */
public class InvalidAgeOfElectorException extends RuntimeException {
    /**
     * The legal age required for an elector.
     */
    private final int legalAge;
    /**
     * The birthdate of the elector that caused the exception.
     */
    private final LocalDate inputBirthday;

    /**
     * Constructs an InvalidAgeOfElectorException with the specified legal age and input birthdate.
     *
     * @param legalAge      The legal age required for an elector.
     * @param inputBirthday The birthdate of the elector that caused the exception.
     */
    public InvalidAgeOfElectorException(int legalAge, LocalDate inputBirthday) {
        this.legalAge = legalAge;
        this.inputBirthday = inputBirthday;
    }

    /**
     * Gets the legal age required for an elector.
     *
     * @return The legal age.
     */
    public int getLegalAge() {
        return legalAge;
    }

    /**
     * Gets the birthdate of the elector that caused the exception.
     *
     * @return The birthdate of the elector.
     */
    public LocalDate getInputBirthday() {
        return inputBirthday;
    }

    /**
     * Returns a message  of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String getMessage() {
        return this.toString();
    }

    /**
     * Returns a string representation of the exception.
     *
     * @return A string describing the exception.
     */
    @Override
    public String toString() {
        return "Sorry, the elector must be of legal age " + legalAge + ", but his birthdate " + inputBirthday;
    }
}
