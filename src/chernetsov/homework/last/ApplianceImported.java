package chernetsov.homework.last;

public abstract class ApplianceImported extends Appliance{
    private String country;
    private int guarantee;

    public ApplianceImported(String country, double price, double weight, double width, double height,
                             double depth, Color color, String description) {
        super(price, weight, width, height, depth, color, description);
        this.country = country;
        guarantee = 0;
    }

    public ApplianceImported(String country, int guarantee, double price, double weight, double width,
                             double height, double depth, Color color, String description) {
        super(price, weight, width, height, depth, color, description);
        this.country = country;
        this.guarantee = guarantee;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", country=" + country +
                ((guarantee!=0)?", guarantee=" + guarantee + " month": "");
    }

    public String getCountry() {
        return country;
    }
    public int getGuarantee() {
        return guarantee;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }
}
