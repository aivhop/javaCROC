package chernetsov.homework.last;

public class Fridge extends Appliance{
    private int numberOfFridgeShelves;
    public Fridge(int numberOfFridgeShelves ,double price, double weight, double width, double height,
                  double depth, Color color, String description) {
        super(price, weight, width, height, depth, color, description);
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", number of fridge shelves=" + numberOfFridgeShelves;
    }

    public int getNumberOfFridgeShelves() {
        return numberOfFridgeShelves;
    }

    public void setNumberOfFridgeShelves(int numberOfFridgeShelves) {
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }
}
