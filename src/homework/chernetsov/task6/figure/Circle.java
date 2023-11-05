package homework.chernetsov.task6.figure;

public class Circle extends Figure {
    double xCenter;
    double yCenter;
    double radius;

    public Circle(double xCenter, double yCenter, double radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
    }

    @Override
    public boolean contains(double x, double y) {
        return radius >= Math.sqrt((xCenter - x) * (xCenter - x) + (yCenter - y) * (yCenter - y));
    }

    @Override
    public void move(int dx, int dy) {
        xCenter += dx;
        yCenter += dy;
    }

    @Override
    public String toString() {
        return "Circle (" + xCenter + ", " + yCenter + "), " + radius;
    }
}
