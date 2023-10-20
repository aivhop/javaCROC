package chernetsov.homework.last;

public class StoveWithOvenImported extends StoveImported{
    double minTemperature;
    double maxTemperature;
    double ovenVolume;

    public StoveWithOvenImported(double minTemperature, double maxTemperature, double ovenVolume, String coverage,
                                 String country, double price, double weight, double width, double height, double depth,
                                 Color color, String description, Stove.Burners... burners) {
        super(false, coverage, country, price, weight, width, height, depth, color, description, burners);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.ovenVolume = ovenVolume;
    }

    public StoveWithOvenImported(double minTemperature, double maxTemperature, double ovenVolume, String coverage,
                                 String country, int guarantee, double price, double weight, double width,
                                 double height, double depth, Color color, String description, Stove.Burners... burners) {
        super(false, coverage, country, guarantee, price, weight, width, height, depth, color, description, burners);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.ovenVolume = ovenVolume;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", minTemperature=" + minTemperature + " °C" +
                ", maxTemperature=" + maxTemperature + " °C" +
                ", ovenVolume=" + ovenVolume + " liter";
    }
    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getOvenVolume() {
        return ovenVolume;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setOvenVolume(double ovenVolume) {
        this.ovenVolume = ovenVolume;
    }
}
