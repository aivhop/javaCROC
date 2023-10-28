package homework.chernetsov.correction.lastTask2.figure;


public class Rectangle extends Figure {

    double xLeft;
    double yLeft;
    double xRight;
    double yRight;

    public Rectangle(double xLeft, double yLeft, double xRight, double yRight) {
        if (xLeft < xRight) {
            this.xLeft = xLeft;
            this.xRight = xRight;
        } else {
            this.xLeft = xRight;
            this.xRight = xLeft;
        }
        if (yLeft < yRight) {
            this.yLeft = yLeft;
            this.yRight = yRight;
        } else {
            this.yLeft = yRight;
            this.yRight = yLeft;
        }
    }

    @Override
    public boolean contains(double x, double y) {
        return (y >= yLeft && y <= yRight && x >= xLeft && x <= xRight);
    }

    @Override
    public void move(int dx, int dy) {
        xRight += dx;
        xLeft += dx;
        yRight += dy;
        yLeft += dy;
    }

    @Override
    public String toString() {
        return "Rectangle (" + xLeft + ", " + yLeft +
                "), (" + xRight + ", " + yRight + ")";
    }
}
