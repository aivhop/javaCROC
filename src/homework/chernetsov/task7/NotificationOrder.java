package homework.chernetsov.task7;

public class NotificationOrder {
    public static String getNotification(Order order) {
        return String.format("""
                        Уважаемый %s!
                                        
                        Рады сообщить, что Ваш заказ %s готов к выдаче.
                                        
                        Состав заказа:
                %s
                                        
                                        
                        Сумма к оплате: %s₽
                                        
                        Срок хранения заказа %s
                                        
                                        
                        С наилучшими пожеланиями, магазин “Кошки и картошки”
                """, order.getFullName(), order.getId(),
                order.getProductsFormatted(), order.getSum(),
                order.getStorageTimeFormatted());
        //todo( interface)
    }
}
