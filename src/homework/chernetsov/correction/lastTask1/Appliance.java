package homework.chernetsov.correction.lastTask1;

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
        if (price <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge shouldn't be free");
        } else if (weight <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should weigh at least some");
        } else if (width <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some width");
        } else if (height <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some height");
        } else if (depth <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some depth");
        } else if (color == null) {
            throw new IllegalArgumentException("Sorry, the fridge should have a color");
        } else if (description == null) {
            throw new IllegalArgumentException("Sorry, the fridge should have a description");
        }
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
                ", depth=" + depth + " cm" +
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
        if (price <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge shouldn't be free");
        }
        this.price = price;
    }
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should weigh at least some");
        }
        this.weight = weight;
    }
    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some width");
        }
        this.width = width;
    }
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some height");
        }
        this.height = height;
    }
    public void setDepth(double depth) {
        if (depth <= 0) {
            throw new IllegalArgumentException("Sorry, the fridge should have at least some depth");
        }
        this.depth = depth;
    }
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Sorry, the fridge should have a color");
        }
        this.color = color;
    }
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Sorry, the fridge should have a description");
        }
        this.description = description;
    }
}
