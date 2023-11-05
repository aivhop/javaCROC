package homework.chernetsov.task5.base;

public interface ApplianceImported {
    int DEFAULT_GUARANTEE = 0;

    default void setCountry(String country) {
        if (country == null) {
            throw new IllegalArgumentException("Sorry, the country has a name");
        }
    }
    default void setGuarantee(int guarantee) {
        if (guarantee < 0) {
            throw new IllegalArgumentException("Sorry, time is a positive value");
        }
    }
}
