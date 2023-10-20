package chernetsov.homework.lastTask2;

public class Annotation{
    private String label;

    private Figure figure;

    public Annotation(String label, Figure figure) {
        this.label = label;
        this.figure = figure;
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
