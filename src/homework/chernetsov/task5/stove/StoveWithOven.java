package homework.chernetsov.task5.stove;

public class StoveWithOven extends Stove{
    double minTemperature;
    double maxTemperature;
    double ovenVolume;


    public StoveWithOven(double minTemperature, double maxTemperature, double ovenVolume,
                         String coverage, double price, double weight, double width, double height, double depth,
                         Color color, String description, Burner.Burners... burners) {
        super(false, coverage, price, weight, width, height, depth, color, description, burners);
        setMinTemperature(minTemperature);
        setMaxTemperature(maxTemperature);
        setOvenVolume(ovenVolume);
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
        if (minTemperature <= -273.15) {
            throw new IllegalArgumentException("Sorry, incorrect minimum temperature");
        }
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        if (maxTemperature <= 0 || maxTemperature < minTemperature) {
            throw new IllegalArgumentException("Sorry, incorrect maximum temperature");
        }
        this.maxTemperature = maxTemperature;
    }

    public void setOvenVolume(double ovenVolume) {
        if (ovenVolume <= 0) {
            throw new IllegalArgumentException("Sorry, the volume must be greater than zero");
        }
        this.ovenVolume = ovenVolume;
    }
}
