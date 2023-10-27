package homework.chernetsov.task7;


import homework.chernetsov.correction.lastTask1.base.Appliance;
import homework.chernetsov.correction.lastTask1.fridge.Fridge;
import homework.chernetsov.correction.lastTask1.stove.Burner;
import homework.chernetsov.correction.lastTask1.stove.Stove;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//todo вынести форматирование в отдельный класс

public class Order {
//TODO (класс клиент?)

    private static int maxWidthOfMessage = 80;
    private String id;

    final private ZonedDateTime entranceDateTime;
    private ZonedDateTime assemblingDateTime;
    private ZonedDateTime receivingDateTime;

    private String fullName;
    private String phone; // отдельный класс? если меняем, нужно поменять id заказа todo

    private final Appliance[] products; //todo maxsize 75

    public Order(String fullName, String phone, ZonedDateTime entranceDateTime, Appliance... products) {
        this.fullName = fullName;
        this.phone = phone;
        this.entranceDateTime = entranceDateTime;
        this.products = products;
        id = calculateId();

        //todo проверки и исключения
    }

    public void setAssemblingDateTime(ZonedDateTime zonedDateTime) {
        this.assemblingDateTime = zonedDateTime;
    }

    public Appliance[] getProducts() {
        return products;
    }

    public String getProductsFormatted(){
        StringBuilder product = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            product.append("\t\t");
            product.append(products[i].getDescription());
            //todo мб метод форматированного вывода важных характеристик красивый( ширину ограничить)
            product.append('\n');
        }
        return product.toString();
    }

    public ZonedDateTime getEntranceDateTime() {
        return entranceDateTime;
    }

    private String calculateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return entranceDateTime.format(formatter) + phone.substring(phone.length() - 4);
    }

    public String getNotification() {
        return String.format("""
                        Уважаемый %s!
                                        
                        Рады сообщить, что Ваш заказ %s готов к выдаче.
                                        
                        Состав заказа:
                %s
                                        
                                        
                        Сумма к оплате: %s₽
                                        
                        Срок хранения заказа %s
                                        
                                        
                        С наилучшими пожеланиями, магазин “Кошки и картошки”
                """, fullName, id, getProductsFormatted(), getSum(), getStorageTimeFormatted());
        //todo( interface)
    }

    public BigDecimal getSum() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < products.length; i++) {
            total = total.add(new BigDecimal(products[i].getPrice())); // todo перевести цену в big decimal в appliance?
        }
        return total;
        //todo( interface)
    }

    public ZonedDateTime getStorageTime() {
        return assemblingDateTime.plusWeeks(2);
        //todo( interface)
    }

    public String getStorageTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return getStorageTime().format(formatter);
        //todo( interface)
    }




    public static void main(String[] args) {
        Appliance fridge = new Fridge(3, 10_000,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");
        Appliance stove = new Stove(true, "glass ceramics", 2_000, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION);

        Order order = new Order("Chernetsov Alexander", "89822939299", ZonedDateTime.of(2000,1,1,1,1,1,1, ZoneId.of("Europe/Moscow")), fridge, stove);
        order.setAssemblingDateTime(order.getEntranceDateTime().plusDays(1));

        System.out.println(order.getNotification());
        System.out.println(order.getStorageTimeFormatted());
        System.out.println("Standard fridge: price=10000.0 rub, weight=47.0 kg, width=110.0 cm, height=210.0 cm,".length());
    }
}
