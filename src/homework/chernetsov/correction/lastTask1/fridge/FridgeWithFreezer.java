package homework.chernetsov.correction.lastTask1.fridge;


public class FridgeWithFreezer extends Fridge{
    private int numberOfFreezerShelves;


    public FridgeWithFreezer(int numberOfFreezerShelves, int numberOFridgeShelves, double price, double weight,
                             double width, double height, double depth, Color color, String description) {
        super(numberOFridgeShelves, price, weight, width, height, depth, color, description);
        setNumberOfFreezerShelves(numberOfFreezerShelves);
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
        if (numberOfFreezerShelves <= 0) {
            throw new IllegalArgumentException("Sorry, there should be at least one shelf in the fridge");
        }
        this.numberOfFreezerShelves = numberOfFreezerShelves;
    }
}
