package chernetsov.homework.lastTask2;

public class Task6 {
    public static void main(String[] args) {
        Circle circle1 = new Circle(0, 0, 10);
        Rectangle rectangle1 = new Rectangle(0, 0, 5, 5);
        Circle circle2 = new Circle(3, 3, 100);
        Annotation ann1 = new Annotation("someArea", circle1);
        Annotation ann2 = new Annotation("someArea2", rectangle1);
        Annotation ann3 = new Annotation("test", circle2);
        AnnotatedImage image = new AnnotatedImage("somePath", ann1, ann2, ann3);
        System.out.println(image.getImagePath());
        for (Annotation ann : image.getAnnotations()) {
            System.out.println(ann);
        }
        System.out.println("Label t: " + image.findByLabel("t"));
        double x = 30;
        double y = 15;
        System.out.println("Annotations contains point: x=" + x + ", y=" + y + " : "
                + image.findByPoint(x,y));
        System.out.println();
        circle2.move(5,5);
        rectangle1.move(10,10);
        ann1.setFigure(circle2);
        ann3.setFigure(rectangle1);
        System.out.println(ann1);
        System.out.println(ann3);

    }
}
