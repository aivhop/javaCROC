package chernetsov.homework.last;

public class FridgeWithFreezer extends Fridge{
    private int numberOfFreezerShelves;

    public FridgeWithFreezer(int numberOfFreezerShelves, int numberOFridgeShelves, double price, double weight,
                             double width, double height, double depth, Color color, String description) {
        super(numberOFridgeShelves, price, weight, width, height, depth, color, description);
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", number of freezer shelves=" + numberOfFreezerShelves;
    }

    public int getNumberOfFreezerShelves() {
        return numberOfFreezerShelves;
    }

    public void setNumberOfFreezerShelves(int numberOfFreezerShelves) {
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }
}
