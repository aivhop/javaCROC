package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.task7.Order;

public class NotificationOrders {
    public static final int DEFAULT_WIDTH = 55;
    public static final String DEFAULT_INDENT = "\t\t";

    public static String getNotification(Order order, String indent, int maxWidth){
        if (indent == null) {
            indent = DEFAULT_INDENT;
        }
        if (maxWidth <= 0) {
            maxWidth = DEFAULT_WIDTH;
        }
        StringBuilder message = new StringBuilder();
        message.append(indent)
                .append("Уважаемый ")
                .append(order.getFullName())
                .append("!\n\n")
                .append(indent)
                .append(switch (order.getStatus()) {
                    case CREATED -> "Ваш заказ " + order.getId() + " принят и готовиться к сборке";
                    case COLLECTED -> "Рады сообщить, что Ваш заказ " + order.getId() + " готов к выдаче";
                    case EXPIRED -> "К сожалению срок хранения вашего заказа " + order.getId() + " истек\n" + indent +
                            "Пожалуйста обратитесь в поддержку";
                    case CLOSED -> "Ваш заказ " + order.getId() + " выдан\n" + indent +
                            "Cпасибо за выбор нашего магазина";
                })
                .append("\n\n")
                .append(indent)
                .append("Состав заказа:\n\n")
                .append(FormatOrders.getProductsFormatted(order.getProducts(), indent, maxWidth))
                .append("\n\n")
                .append(indent)
                .append("Сумма к оплате: ")
                .append(FormatOrders.getPriceFormatted(order.getSum()))
                .append("₽\n\n")
                .append(indent)
                .append(switch (order.getStatus()) {
                    case CREATED -> "Срок хранения заказа будет известен позже";
                    case COLLECTED, EXPIRED -> "Срок хранения заказа: " + order.getStorageTimeFormatted();
                    case CLOSED -> "Ваш заказ выдан: " + FormatOrders.getTimeFormatted(order.getReceivingDateTime());
                })
                .append("\n\n")
                .append(indent)
                .append("С наилучшими пожеланиями, магазин “Кошки и картошки”");
        return message.toString();
    }

    public static String getNotification(Order order) {
        return getNotification(order,DEFAULT_INDENT,DEFAULT_WIDTH);
    }

    private NotificationOrders(){}
}
