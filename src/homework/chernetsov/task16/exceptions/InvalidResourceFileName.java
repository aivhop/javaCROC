package homework.chernetsov.task16.exceptions;

public class InvalidResourceFileName extends RuntimeException {
    private String correctFormat;
    private String inputFileName;

    public InvalidResourceFileName(String inputFileName, String correctFormat) {
        this.inputFileName = inputFileName;
        this.correctFormat = correctFormat;
    }


    public InvalidResourceFileName(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return inputFileName == null ? super.getMessage() : this.toString();
    }

    @Override
    public String toString() {
        return "Sorry, a format file is needed: " + correctFormat +
                ", your file name: " + inputFileName;
    }
}
