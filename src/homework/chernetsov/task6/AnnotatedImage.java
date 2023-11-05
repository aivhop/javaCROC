package homework.chernetsov.task6;

class AnnotatedImage {
    private final String imagePath;

    private final Annotation[] annotations;

    public Annotation findByPoint(double x, double y){
        for (Annotation ann: annotations) {
            if(ann.getFigure().contains(x,y)){
                return ann;
            }
        }
        return null;
    }
    public Annotation findByLabel(String label){
        for (Annotation ann: annotations) {
            if(ann.getLabel().contains(label)){
                return ann;
            }
        }
        return null;
    }

    public AnnotatedImage(String imagePath, Annotation... annotations) {
        this.imagePath = imagePath;
        this.annotations = annotations;
    }
    public String getImagePath() {
        return this.imagePath;
    }

    public Annotation[] getAnnotations() {
        return this.annotations;
    }
}
