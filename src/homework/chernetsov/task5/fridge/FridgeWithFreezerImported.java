package homework.chernetsov.task5.fridge;

import homework.chernetsov.task5.base.ApplianceImported;


public class FridgeWithFreezerImported extends FridgeWithFreezer implements ApplianceImported{
    private String country;
    private int guarantee;


    public FridgeWithFreezerImported(String country, int guarantee, int numberOfFreezerShelves,
                                     int numberOFridgeShelves, double price, double weight,
                                     double width, double height, double depth, Color color, String description) {
        super(numberOfFreezerShelves, numberOFridgeShelves, price, weight, width, height, depth, color, description);
        setCountry(country);
        setGuarantee(guarantee);
    }

    public FridgeWithFreezerImported(String country, int numberOfFreezerShelves, int numberOFridgeShelves,
                                     double price, double weight, double width,
                                     double height, double depth, Color color, String description) {
        this(country, ApplianceImported.DEFAULT_GUARANTEE, numberOfFreezerShelves, numberOFridgeShelves,
                price, weight, width, height, depth, color, description);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", country=" + country +
                ((guarantee != 0) ? ", guarantee=" + guarantee + " month" : "");
    }

    public String getCountry() {
        return country;
    }

    public int getGuarantee() {
        return guarantee;
    }

    @Override
    public void setCountry(String country) {
        ApplianceImported.super.setCountry(country);
        this.country = country;
    }

    @Override
    public void setGuarantee(int guarantee) {
        ApplianceImported.super.setGuarantee(guarantee);
        this.guarantee = guarantee;
    }
}
