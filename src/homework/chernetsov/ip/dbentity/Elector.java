package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.InvalidAgeOfElectorException;
import homework.chernetsov.ip.exceptions.InvalidElectorPassport;

import java.time.LocalDate;


public record Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                      int precinctId, boolean bulletinReceived, LocalDate birthday) {

    public static int AGE_OF_ELECTORS = 18;

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic, int precinctId, boolean bulletinReceived, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(passportSeriesNumber, surname, firstname, patronymic, precinctId, bulletinReceived, checkBirthDay(dayOfBirth, monthOfBirth, yearOfBirth));
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic, int precinctId, boolean bulletinReceived, LocalDate birthday) {
        this.passportSeriesNumber = checkPassport(passportSeriesNumber);
        this.surname = checkName(surname);
        this.firstname = checkName(firstname);
        this.patronymic = patronymic == null || patronymic.isEmpty() ? "-" : patronymic;
        this.precinctId = checkPrecinctId(precinctId);
        this.bulletinReceived = bulletinReceived;
        this.birthday = birthday;
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, int precinctId, boolean bulletinReceived, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(passportSeriesNumber, surname, firstname, null, bulletinReceived, precinctId, dayOfBirth, monthOfBirth, yearOfBirth);
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, int precinctId, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(passportSeriesNumber, surname, firstname, null, false, precinctId, dayOfBirth, monthOfBirth, yearOfBirth);
    }

    public String passportSeries() {
        return passportSeriesNumber.substring(0, 4);
    }

    public String passportNumber() {
        return passportSeriesNumber.substring(4);
    }


    private static String checkPassport(String passportSeriesNumber) {
        if (passportSeriesNumber == null || !passportSeriesNumber.matches("\\d{10}")) {
            throw new InvalidElectorPassport(passportSeriesNumber, "The passport series and number must consist of 10 digits");
        }
        return passportSeriesNumber;
    }

    private static String checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sorry, name can't be empty");
        }
        return name;
    }

    private static int checkPrecinctId(int precinctId) {
        if (precinctId < 1) {
            throw new IllegalArgumentException("Sorry, the precinct id must be a positive number");
        }
        return precinctId;
    }

    private static LocalDate checkBirthDay(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        LocalDate date = java.time.LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        if (date.isAfter(java.time.LocalDate.now().minusYears(AGE_OF_ELECTORS))) {
            throw new InvalidAgeOfElectorException(AGE_OF_ELECTORS, date.getYear());
        }
        return date;
    }

    public static void setAgeOfElectors(int ageOfElectors) {
        if (ageOfElectors <= 0) {
            throw new IllegalArgumentException("Sorry, age is a positive number");
        }
        AGE_OF_ELECTORS = ageOfElectors;
    }

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

    public static void main(String[] args) {
        Elector elector = new Elector("1234567890", "C", "A", "M", 1, 4, 12, 2004);
        System.out.println(elector);
    }
}
