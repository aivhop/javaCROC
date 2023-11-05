package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.task5.base.Appliance;
import homework.chernetsov.task7.Order;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class FormatOrders {
    public static String getStorageTimeFormatted(Order order) {
        return getTimeFormatted(order.getStorageTime());
    }
    public static String getTimeFormatted(ZonedDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return time.format(formatter);
    }

    public static String getPriceFormatted(BigDecimal value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("Russia"));
        return format.format(value);
    }

    /**
     * Метод, который возвращает список товаров в отформатированном виде
     *
     * @param products список товаров
     * @param indent   отступ
     * @param maxWidth максимальная ширина сообщения
     * @return строка, содержащая товары в отформатированном виде
     */
    public static String getProductsFormatted(ArrayList<Appliance> products, String indent, int maxWidth) {
        StringBuilder order = new StringBuilder(); // строка, в которой будут все товары в отформатированном виде
        for (Appliance product : products) {  // цикл по всем товарам в списке
            order.append(indent); // добавление отступа
            String toFormat = product.toString(); // строка, в которой содержится не отформатированная информация о товаре

            String description = toFormat.substring(0, toFormat.indexOf(':') + 2);
            //получили описание товара, которое также может не влезть в ширину

            String[] wordsDescription = description.split(" ");
            // разделили по словам описание

            StringBuilder currentProduct = new StringBuilder(); // строка, в которой будет информация об 1 товаре в отформатированном виде
            int currentLength = 0; // счетчик длины, для корректного задания ширины
            for (String word : wordsDescription) {
                if (maxWidth > currentLength + word.length()) { // если слово влезает вместе с пробелом
                    currentProduct.append(word) // добавляем слово
                            .append(" "); // добавляем разделитель
                    currentLength += word.length(); // обновляем длину
                } else {
                    currentProduct.append("\n") // выполняем перенос
                            .append(indent); //добавляем отступ
                    currentLength = 0; // обновляем длину
                }
            }
            String[] parameters = toFormat.substring(description.length()).split(", ");
            // по разделителю добавляем характеристики, исключая описание
            for (String parameter : parameters) {
                if (parameter.length() + currentLength + 2 > maxWidth) {
                    // если характеристика не помещается в строку вместе с разделителями
                    currentLength = 0; // обнуляем счетчик длины строки
                    currentProduct.append("\n") // выполняем перенос
                            .append(indent) // добавляем отступ
                            .append(parameter) // вписываем характеристику
                            .append(", "); // добавляем разделитель
                } else {
                    // иначе характеристика помещается
                    currentProduct.append(parameter); // добавляем характеристику
                    currentProduct.append(", "); // добавляем разделитель
                    currentLength += parameter.length(); // обновляем длину строки
                }
            }
            order.append(currentProduct.substring(0, currentProduct.length() - 2));
            // добавляем каждый товар в итоговую строку, исключая последние разделители
            order.append('\n'); // добавляем перенос
        }
        return order.toString();
    }
    
    private FormatOrders(){}
}
