package homework.chernetsov.task5.stove;

import homework.chernetsov.task5.base.Appliance;

import java.util.Arrays;

public class Stove extends Appliance {
    private Burner.Burners[] burners;
    private int numberOfBurners;
    private boolean isPortable;
    private String coverage;

    public Stove(boolean isPortable, String coverage, double price, double weight, double width, double height,
                 double depth, Color color, String description, Burner.Burners... burners) {
        super(price, weight, width, height, depth, color, description);
        setBurners(burners);
        setCoverage(coverage);
        this.isPortable = isPortable;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", burners=" + Arrays.toString(burners) +
                ", numberOfBurners=" + numberOfBurners +
                (isPortable?", Portable": ", Not portable") +
                ", coverage='" + coverage + '\'';
    }

    public Burner.Burners[] getBurners() {
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

    public void setBurners(Burner.Burners... burners) {
        if (burners.length == 0) {
            throw new IllegalArgumentException("Sorry, the stove must have burners");
        }
        this.burners = burners;
        this.numberOfBurners = burners.length;
    }

    public void setNumberOfBurners(int numberOfBurners) {
        if(numberOfBurners!= burners.length){
            throw new IllegalArgumentException("Sorry, incorrect value of quantity");
        }
        this.numberOfBurners = numberOfBurners;
    }

    public void setPortable(boolean portable) {
        isPortable = portable;
    }

    public void setCoverage(String coverage) {
        if (coverage == null) {
            throw new IllegalArgumentException("Sorry, the stove must have a coating");
        }
        this.coverage = coverage;
    }
}
