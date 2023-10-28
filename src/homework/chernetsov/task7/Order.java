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


public class Order {
    private String id;
    final private ZonedDateTime entranceDateTime;
    private ZonedDateTime assemblingDateTime;
    private ZonedDateTime receivingDateTime; // todo подумать над локальным временем и часовыми поясам
    private String fullName; // todo проверка что есть хотя бы имя и фамилия
    private String phone; // если меняем, нужно поменять id заказа todo
    private final Appliance[] products; //todo maxsize 75


    public Order(String fullName, String phone, ZonedDateTime entranceDateTime, Appliance... products) {
        this.fullName = fullName;
        this.phone = phone;
        this.entranceDateTime = entranceDateTime;
        this.products = products;
        id = calculateId();

        //todo проверки и исключения
    }



    public String getProductsFormatted() {
        return new FormatOrder().getProductsFormatted(products);
    }

    public String getProductsFormatted(int maxWidthOfMessage){
        return new FormatOrder(maxWidthOfMessage).getProductsFormatted(products);
    }

    public String getProductsFormatted(String indent){
        return new FormatOrder(indent).getProductsFormatted(products);
    }

    public String getProductsFormatted(int maxWidthOfMessage, String indent) {
        return new FormatOrder(maxWidthOfMessage, indent).getProductsFormatted(products);
    }

    private String calculateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return entranceDateTime.format(formatter) + phone.substring(phone.length() - 4);
    }

    public String getNotification() {
        return NotificationOrder.getNotification(this);
    }

    public BigDecimal getSum() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < products.length; i++) {
            total = total.add(new BigDecimal(products[i].getPrice()));
        }
        return total;
        //todo( interface)
    }

    public ZonedDateTime getStorageTime() {
        return assemblingDateTime.plusWeeks(2);
        //todo( interface)
    }

    public String getStorageTimeFormatted() {
        return FormatOrder.getStorageTimeFormatted(this);
        //todo( interface)
    }

    //SETTERS todo с помощью  них и сделать exceptions
    public void setAssemblingDateTime(ZonedDateTime zonedDateTime) {
        this.assemblingDateTime = zonedDateTime;
    }


    //GETTERS


    public String getId() {
        return id;
    }

    public ZonedDateTime getEntranceDateTime() {
        return entranceDateTime;
    }

    public ZonedDateTime getAssemblingDateTime() {
        return assemblingDateTime;
    }

    public ZonedDateTime getReceivingDateTime() {
        return receivingDateTime;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public Appliance[] getProducts() {
        return products;
    }



    public static void main(String[] args) {
        Appliance fridge = new Fridge(3, 10_000,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");
        Appliance stove = new Stove(true, "glass ceramics", 2_000, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION, Burner.Burners.ELECTRIC);

        Order order = new Order("Chernetsov Alexander", "89822939299", ZonedDateTime.of(2000, 1, 1, 1, 1, 1, 1, ZoneId.of("Europe/Moscow")), fridge, stove);
        order.setAssemblingDateTime(order.getEntranceDateTime().plusDays(1));

        System.out.println(order.getNotification());
        System.out.println(order.getStorageTimeFormatted());
        System.out.println("Standard fridge: price=10000.0 rub, weight=47.0 kg, width=110.0 cm, height=210.0 cm,".length());
    }
}
