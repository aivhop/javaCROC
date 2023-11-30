package homework.chernetsov.ip.dbentity;

import homework.chernetsov.ip.exceptions.InvalidElectorPassport;


public record Elector(String passportSeriesNumber, String surname, String firstname, String patronymic,
                      int precinctId, boolean opportunityVote) {

    public Elector(String passportSeriesNumber, String surname, String firstname, String patronymic, int precinctId, boolean opportunityVote) {
        this.passportSeriesNumber = checkPassport(passportSeriesNumber);
        this.surname = checkName(surname);
        this.firstname = checkName(firstname);
        this.patronymic = patronymic == null || patronymic.isEmpty() ? "-" : patronymic;
        this.precinctId = checkPrecinctId(precinctId);
        this.opportunityVote = opportunityVote;
    }

    public Elector(String passportSeriesNumber, String surname, String firstname, int precinctId, boolean opportunityVote) {
        this(passportSeriesNumber, surname, firstname, null, precinctId, opportunityVote);
    }

    public String passportSeries() {
        return passportSeriesNumber.substring(0, 4);
    }

    public String passportNumber() {
        return passportSeriesNumber.substring(4);
    }


    private static String checkPassport(String passportSeriesNumber) {
        if (passportSeriesNumber == null || !passportSeriesNumber.matches("\\d{10}")) {
            throw new InvalidElectorPassport(passportSeriesNumber);
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

    @Override
    public String toString() {
        return "Elector{" +
                "passportSeriesNumber='" + passportSeriesNumber + '\'' +
                ", surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", precinctId=" + precinctId +
                ", opportunityVote=" + opportunityVote +
                '}';
    }
}
