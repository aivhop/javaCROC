package homework.chernetsov.ip.exceptions;

public class InvalidElectorPassport extends RuntimeException{
    private final String inputPassport;

    public InvalidElectorPassport(String inputPassport) {
        this.inputPassport = inputPassport;
    }

    public String getInputPassport() {
        return inputPassport;
    }

    @Override
    public String toString() {
        return "Sorry, incorrect passport series and number:" + inputPassport +
                ", the passport series and number must consist of 10 digits";
    }
}
