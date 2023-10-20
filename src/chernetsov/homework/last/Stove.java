package chernetsov.homework.last;

import java.util.Arrays;

public class Stove extends Appliance{
    public enum Burners{GAS,ELECTRIC,INDUCTION};
    private Burners[] burners;
    private int numberOfBurners;
    private boolean isPortable;
    private String coverage;

    public Stove(boolean isPortable, String coverage, double price, double weight, double width, double height,
                 double depth, Color color, String description, Burners... burners) {
        super(price, weight, width, height, depth, color, description);
        this.burners = burners;
        this.numberOfBurners = burners.length;
        this.isPortable = isPortable;
        this.coverage = coverage;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", burners=" + Arrays.toString(burners) +
                ", numberOfBurners=" + numberOfBurners +
                (isPortable?", Portable": ", Not portable") +
                ", coverage='" + coverage + '\'';
    }

    public Burners[] getBurners() {
        return burners;
    }

    public int getNumberOfBurners() {
        return numberOfBurners;
    }

    public boolean isPortable() {
        return isPortable;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setBurners(Burners[] burners) {
        this.burners = burners;
    }

    public void setNumberOfBurners(int numberOfBurners) {
        this.numberOfBurners = numberOfBurners;
    }

    public void setPortable(boolean portable) {
        isPortable = portable;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}
