package chernetsov.homework.lastTask1;

public class StoveWithOvenImported extends StoveImported {
    double minTemperature;
    double maxTemperature;
    double ovenVolume;

    public StoveWithOvenImported(double minTemperature, double maxTemperature, double ovenVolume, String coverage,
                                 String country, double price, double weight, double width, double height, double depth,
                                 Color color, String description, Stove.Burners... burners) {
        super(false, coverage, country, price, weight, width, height, depth, color, description, burners);
        if (minTemperature <= -273.15) {
            throw new IllegalArgumentException("Sorry, incorrect minimum temperature");
        } else if (maxTemperature <= 0 || maxTemperature < minTemperature) {
            throw new IllegalArgumentException("Sorry, incorrect maximum temperature");
        } else if (ovenVolume <= 0) {
            throw new IllegalArgumentException("Sorry, the volume must be greater than zero");
        }
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.ovenVolume = ovenVolume;
    }

    public StoveWithOvenImported(double minTemperature, double maxTemperature, double ovenVolume, String coverage,
                                 String country, int guarantee, double price, double weight, double width,
                                 double height, double depth, Color color, String description, Stove.Burners... burners) {
        super(false, coverage, country, guarantee, price, weight, width, height, depth, color, description, burners);
        if (minTemperature <= -273.15) {
            throw new IllegalArgumentException("Sorry, incorrect minimum temperature");
        } else if (maxTemperature <= 0 || maxTemperature < minTemperature) {
            throw new IllegalArgumentException("Sorry, incorrect maximum temperature");
        } else if (ovenVolume <= 0) {
            throw new IllegalArgumentException("Sorry, the volume must be greater than zero");
        }
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
