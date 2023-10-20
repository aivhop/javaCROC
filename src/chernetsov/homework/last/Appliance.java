package chernetsov.homework.last;

public abstract class Appliance {
    private double price;
    private double weight;
    private double width;
    private double height;
    private double depth;
    private Color color;

    public enum Color{RED,GREEN,BLUE,BLACK,WHITE}
    private String description;

    public Appliance(double price, double weight, double width, double height, double depth, Color color, String description) {
        this.price = price;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = color;
        this.description = description;
    }

    @Override
    public String toString() {
        return description + ':' +
                " price=" + price + " rub" +
                ", weight=" + weight + " kg" +
                ", width=" + width + " cm" +
                ", height=" + height + " cm" +
                ",\ndepth=" + depth + " cm" +
                ", color=" + color;
    }

    public double getPrice() {
        return price;
    }
    public double getWeight() {
        return weight;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double getDepth() {
        return depth;
    }
    public String getDescription() {
        return description;
    }
    public Color getColor() {
        return color;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setDepth(double depth) {
        this.depth = depth;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
