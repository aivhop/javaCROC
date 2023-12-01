package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.InvalidAgeOfElectorException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;

import java.time.LocalDate;

/**
 * Class representing an elector.
 * This class provides a data structure to store information about an elector, including details
 * such as passport information, name, precinct ID, birthday, and whether the elector can receive a voting bulletin.
 */
public record Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                      int precinctId, LocalDate birthday, boolean bulletinReceived, int ageOfElectors) {

    /**
     * Static constant for the default minimum age of electors.
     */
    public static final int DEFAULT_AGE_OF_ELECTORS = 18;

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                   int precinctId, LocalDate birthday, boolean bulletinReceived, int ageOfElectors) {
        this.passportSeriesNumber = checkPassport(passportSeriesNumber);
        this.surname = checkName(surname);
        this.firstname = checkName(firstname);
        this.patronymic = patronymic == null || patronymic.isEmpty() ? "-" : patronymic;
        this.precinctId = checkPrecinctId(precinctId);
        this.birthday = checkBirthDay(birthday);
        this.bulletinReceived = bulletinReceived;
        this.ageOfElectors = checkAgeOfElectors(ageOfElectors);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic, int precinctId,
                   int dayOfBirth, int monthOfBirth, int yearOfBirth, boolean bulletinReceived) {
        this(passportSeriesNumber, surname, firstname, patronymic, precinctId,
                convertDate(dayOfBirth, monthOfBirth, yearOfBirth), bulletinReceived, DEFAULT_AGE_OF_ELECTORS);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic, int precinctId,
                   int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(passportSeriesNumber, surname, firstname, patronymic, precinctId,
                dayOfBirth, monthOfBirth, yearOfBirth, false);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                   int precinctId, LocalDate birthday, boolean bulletinReceived) {
        this(passportSeriesNumber, surname, firstname, patronymic, precinctId,
                birthday, bulletinReceived, DEFAULT_AGE_OF_ELECTORS);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                   int precinctId, LocalDate birthday) {
        this(passportSeriesNumber, surname, firstname, patronymic, precinctId,
                birthday, false, DEFAULT_AGE_OF_ELECTORS);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, int precinctId,
                   int dayOfBirth, int monthOfBirth, int yearOfBirth, boolean bulletinReceived) {
        this(passportSeriesNumber, surname, firstname, null, precinctId,
                dayOfBirth, monthOfBirth, yearOfBirth, bulletinReceived);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, int precinctId,
                   int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(passportSeriesNumber, surname, firstname, null, precinctId,
                dayOfBirth, monthOfBirth, yearOfBirth, false);
    }

    /**
     * getter for passport series
     *
     * @return The passport series
     */
    public String passportSeries() {
        return passportSeriesNumber.substring(0, 4);
    }

    /**
     * getter for passport number
     *
     * @return The passport number
     */
    public String passportNumber() {
        return passportSeriesNumber.substring(4);
    }


    // Private methods for checking the validity of parameters.

    /**
     * @param age the value to check
     * @return the passed verified value
     * @throws IllegalArgumentException If age is not a positive number
     */
    private static int checkAgeOfElectors(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("Sorry, the age must be a positive number");
        }
        return age;
    }

    /**
     * @param passportSeriesNumber the value to check
     * @return the passed verified value
     * @throws InvalidElectorPassport If the passport series and number do not consist of 10 digits
     */
    private static String checkPassport(String passportSeriesNumber) {
        if (passportSeriesNumber == null || !passportSeriesNumber.matches("\\d{10}")) {
            throw new InvalidElectorPassport(passportSeriesNumber, "The passport series and number must consist of 10 digits");
        }
        return passportSeriesNumber;
    }

    /**
     * @param name the value to check
     * @return the passed verified value
     * @throws IllegalArgumentException If name is empty or null
     */
    private static String checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sorry, name can't be empty");
        }
        return name;
    }

    /**
     * @param precinctId the value to check
     * @return the passed verified value
     * @throws IllegalArgumentException If precinct id is not a positive number
     */
    private static int checkPrecinctId(int precinctId) {
        if (precinctId < 1) {
            throw new IllegalArgumentException("Sorry, the precinct id must be a positive number");
        }
        return precinctId;
    }


    /**
     * @param date the value to check
     * @return the passed verified value
     * @throws InvalidAgeOfElectorException If the potential elector has not reached the AGE_OF_ELECTORS
     */
    private static LocalDate checkBirthDay(LocalDate date) {
        if (date.isAfter(LocalDate.now().minusYears(DEFAULT_AGE_OF_ELECTORS))) {
            throw new InvalidAgeOfElectorException(DEFAULT_AGE_OF_ELECTORS, date);
        }
        return date;
    }

    /**
     * @param dayOfBirth   day of elector's birthdate
     * @param monthOfBirth month of elector's birthdate
     * @param yearOfBirth  year of elector's birthdate
     * @return object LocalDate which represent parameters date
     */
    private static LocalDate convertDate(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        return LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
    }

    /**
     * @return A string representation of the elector.
     */
    @Override
    public String toString() {
        return "Elector{" +
                "passportSeriesNumber='" + passportSeriesNumber + '\'' +
                ", surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", precinctId=" + precinctId +
                ", birthday=" + birthday +
                '}';
    }
}
