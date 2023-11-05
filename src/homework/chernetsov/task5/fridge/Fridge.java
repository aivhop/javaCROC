package homework.chernetsov.task5.fridge;

import homework.chernetsov.task5.base.Appliance;

public class Fridge extends Appliance {
    private int numberOfFridgeShelves;


    public Fridge(int numberOfFridgeShelves, double price, double weight, double width, double height,
                  double depth, Color color, String description) {
        super(price, weight, width, height, depth, color, description);
        setNumberOfFridgeShelves(numberOfFridgeShelves);
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
        if (numberOfFridgeShelves <= 0) {
            throw new IllegalArgumentException("Sorry, there should be at least one shelf in the fridge");
        }
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }
}
