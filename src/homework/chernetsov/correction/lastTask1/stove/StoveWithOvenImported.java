package homework.chernetsov.correction.lastTask1.stove;

import homework.chernetsov.correction.lastTask1.base.ApplianceImported;


public class StoveWithOvenImported extends StoveWithOven implements ApplianceImported{
    private String country;
    private int guarantee;


    public StoveWithOvenImported(String country, int guarantee, double minTemperature, double maxTemperature,
                                 double ovenVolume, String coverage, double price, double weight, double width,
                                 double height, double depth, Color color, String description, Burner.Burners... burners) {
        super(minTemperature, maxTemperature, ovenVolume, coverage, price, weight,
                width, height, depth, color, description, burners);
        setCountry(country);
        setGuarantee(guarantee);
    }

    public StoveWithOvenImported(String country, double minTemperature, double maxTemperature, double ovenVolume,
                                 String coverage, double price, double weight, double width, double height,
                                 double depth, Color color, String description, Burner.Burners... burners) {
        this(country, ApplianceImported.DEFAULT_GUARANTEE, minTemperature, maxTemperature,
                ovenVolume, coverage, price, weight, width, height, depth, color, description, burners);
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
