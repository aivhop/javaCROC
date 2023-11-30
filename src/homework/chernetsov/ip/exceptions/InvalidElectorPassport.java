package homework.chernetsov.ip.exceptions;

public class InvalidElectorPassport extends RuntimeException {
    private final String inputPassport;
    private final String message;

    public InvalidElectorPassport(String inputPassport, String message) {
        this.inputPassport = inputPassport;
        this.message = message;
    }


    public String getInputPassport() {
        return inputPassport;
    }

    @Override
    public String toString() {
        return "Sorry, incorrect passport series and number:" + inputPassport + '\n' + message;
    }
}
