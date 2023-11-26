package homework.chernetsov.task15.exceptions;

public class CreationTableException extends RuntimeException {

    private String tableName;

    public CreationTableException(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Sorry, error in creation table: " + tableName;
    }
}
