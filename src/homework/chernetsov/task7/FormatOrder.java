package homework.chernetsov.task7;

import homework.chernetsov.correction.lastTask1.base.Appliance;

import java.time.format.DateTimeFormatter;

public class FormatOrder {

    public static final int DEFAULT_WIDTH = 55;
    public static final String DEFAULT_INDENT = "\t\t";
    private int maxWidth;
    private String indent;

    public FormatOrder() {
        this.indent = DEFAULT_INDENT;
        this.maxWidth = DEFAULT_WIDTH;
    }

    public FormatOrder(int maxWidth, String indent) {
        setMaxWidth(maxWidth);
        setIndent(indent);
    }

    public FormatOrder(String indent) {
        this(DEFAULT_WIDTH, indent);
    }

    public FormatOrder(int maxWidth) {
        this(maxWidth, DEFAULT_INDENT);
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        if (maxWidth <= 0) {
            maxWidth = DEFAULT_WIDTH;
        }
        this.maxWidth = maxWidth;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        if (indent == null) {
            indent = DEFAULT_INDENT;
        }
        this.indent = indent;
    }

    public static String getStorageTimeFormatted(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return order.getStorageTime().format(formatter);
        //todo( interface)
    }

    public String getProductsFormatted(Appliance[] products) {
        StringBuilder order = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            order.append(this.getIndent());
            String toFormat = products[i].toString();
            StringBuilder currentProduct = new StringBuilder();
            int currentLength = 0;
            for (int j = 0; j < toFormat.length(); j++) {
                StringBuilder parameter = new StringBuilder();
                for (int k = j; k < toFormat.length(); k++) {
                    if (toFormat.charAt(k) == ',' && toFormat.charAt(k + 1) == ' ') {
                        break;
                    }
                    parameter.append(toFormat.charAt(k));
                }
                if (parameter.length() > this.maxWidth - currentLength - 2) {//2 - ", "
                    currentLength = 0;
                    currentProduct.append("\n");
                    currentProduct.append(this.indent);
                    currentProduct.append(parameter);
                } else {
                    if (j != 0) {
                        currentProduct.append(", ");
                    }
                    currentProduct.append(parameter);
                    currentLength += parameter.length();
                }
                j += parameter.length() + 1;
            }
            order.append(currentProduct);
            order.append('\n');
        }
        return order.toString();
    }
}