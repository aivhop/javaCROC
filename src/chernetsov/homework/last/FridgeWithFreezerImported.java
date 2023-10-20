package chernetsov.homework.last;

public class FridgeWithFreezerImported extends FridgeImported{
    private int numberOfFreezerShelves;

    public FridgeWithFreezerImported(int numberOfFreezerShelves, int numberOfFridgeShelves, String country,
                                     double price, double weight, double width, double height,
                                     double depth, Color color, String description) {
        super(numberOfFridgeShelves, country, price, weight, width, height, depth, color, description);
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }

    public FridgeWithFreezerImported(int numberOfFreezerShelves, int numberOfFridgeShelves, String country,
                                     int guarantee, double price, double weight, double width, double height,
                                     double depth, Color color, String description) {
        super(numberOfFridgeShelves, country, guarantee, price, weight, width, height, depth, color, description);
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", numberOfFreezerShelves=" + numberOfFreezerShelves;
    }

    public int getNumberOfFreezerShelves() {
        return numberOfFreezerShelves;
    }

    public void setNumberOfFreezerShelves(int numberOfFreezerShelves) {
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }
}
