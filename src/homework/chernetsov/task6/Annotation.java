package homework.chernetsov.task6;

import homework.chernetsov.task6.figure.Figure;
import homework.chernetsov.task6.figure.Movable;

import java.util.Objects;

public class Annotation implements Movable {
    private String label;

    private Figure figure;

    public Annotation(String label, Figure figure) {
        setFigure(figure);
        this.label = label;
    }

    @Override
    public void move(int dx, int dy) {
        figure.move(dx, dy);
    }

    @Override
    public String toString() {
        return figure.toString() + ": " + (label==null? "": label);
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
        Objects.requireNonNull(figure); // from lecture
        this.figure = figure;
    }
}
