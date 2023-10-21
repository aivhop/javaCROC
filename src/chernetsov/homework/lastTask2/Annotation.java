package chernetsov.homework.lastTask2;

public class Annotation implements Movable {
    private String label;

    private Figure figure;

    public Annotation(String label, Figure figure) {
        this.label = label;
        this.figure = figure;
    }

    @Override
    public void move(int dx, int dy) {
        figure.move(dx, dy);
    }

    @Override
    public String toString() {
        return figure.toString() + ": " + label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
