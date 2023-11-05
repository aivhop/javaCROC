package homework.chernetsov.task5.fridge;

import homework.chernetsov.task5.base.ApplianceImported;


public class FridgeImported extends Fridge implements ApplianceImported {
    private String country;
    private int guarantee;


    public FridgeImported(String country, int guarantee, int numberOfFridgeShelves, double price, double weight,
                          double width, double height, double depth, Color color, String description) {
        super(numberOfFridgeShelves, price, weight, width, height, depth, color, description);
        setCountry(country);
        setGuarantee(guarantee);
    }

    public FridgeImported(String country, int numberOfFridgeShelves, double price, double weight,
                          double width, double height, double depth, Color color, String description) {
        this(country, ApplianceImported.DEFAULT_GUARANTEE, numberOfFridgeShelves,
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
