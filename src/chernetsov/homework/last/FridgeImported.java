package chernetsov.homework.last;

public class FridgeImported extends ApplianceImported{
    private int numberOfFridgeShelves;

    public FridgeImported(int numberOfFridgeShelves, String country, double price, double weight,
                          double width, double height, double depth, Color color, String description) {
        super(country, price, weight, width, height, depth, color, description);
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }

    public FridgeImported(int numberOfFridgeShelves, String country, int guarantee, double price,
                          double weight, double width, double height, double depth, Color color, String description) {
        super(country, guarantee, price, weight, width, height, depth, color, description);
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", numberOfFridgeShelves=" + numberOfFridgeShelves;
    }

    public int getNumberOfFridgeShelves() {
        return numberOfFridgeShelves;
    }

    public void setNumberOfFridgeShelves(int numberOfFridgeShelves) {
        this.numberOfFridgeShelves = numberOfFridgeShelves;
    }
}

