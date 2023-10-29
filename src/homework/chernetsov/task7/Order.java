package homework.chernetsov.task7;


import homework.chernetsov.correction.lastTask1.base.Appliance;
import homework.chernetsov.task7.serviceOrder.FormatOrder;
import homework.chernetsov.task7.serviceOrder.NotificationOrder;
import homework.chernetsov.task7.serviceOrder.OrderInterface;
import homework.chernetsov.task7.serviceOrder.TestOrder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;


public class Order implements OrderInterface {
    public enum OrderStatus {
        CREATED,
        COLLECTED,
        EXPIRED,
        CLOSED
    }

    private OrderStatus status;
    private String id;
    final private ZonedDateTime creatingDateTime;
    private ZonedDateTime collectingDateTime;
    private ZonedDateTime receivingDateTime;
    private String fullName;
    private String phone;
    private final Appliance[] products;


    public Order(String fullName, String phone, ZonedDateTime creatingDateTime, Appliance... products) {
        if (products.length > 75) {
            throw new IllegalArgumentException("Sorry, the order does not support more than 75 items");
        }
        this.creatingDateTime = creatingDateTime;
        this.products = products;
        setFullName(fullName);
        setPhone(phone);
        id = calculateId();
        status = OrderStatus.CREATED;
    }

    @Override
    public void collect() {
        collectingDateTime = ZonedDateTime.now();
        status = OrderStatus.COLLECTED;
    }

    @Override
    public void collect(ZonedDateTime time) {
        collectingDateTime = time;
        status = OrderStatus.COLLECTED;
    }

    @Override
    public void give() {
        receivingDateTime = ZonedDateTime.now();
        status = OrderStatus.CLOSED;
    }

    @Override
    public void give(ZonedDateTime time) {
        receivingDateTime = time;
        status = OrderStatus.CLOSED;

    }

    @Override
    public boolean isExpired() {
        if (ZonedDateTime.now().isAfter(getStorageTime())) {
            status = OrderStatus.EXPIRED;
            return true;
        }
        return false;
    }

    @Override
    public boolean isExpired(ZonedDateTime time) {
        if (time.isAfter(getStorageTime())) {
            status = OrderStatus.EXPIRED;
            return true;
        }
        return false;
    }

    @Override
    public boolean tryToGet() {
        return OrderInterface.super.tryToGet();
    }

    @Override
    public String getProductsFormatted() {
        return new FormatOrder().getProductsFormatted(products);
    }

    @Override
    public String getProductsFormatted(int maxWidthOfMessage) {
        return new FormatOrder(maxWidthOfMessage).getProductsFormatted(products);
    }

    @Override
    public String getProductsFormatted(String indent) {
        return new FormatOrder(indent).getProductsFormatted(products);
    }

    @Override
    public String getProductsFormatted(int maxWidthOfMessage, String indent) {
        return new FormatOrder(maxWidthOfMessage, indent).getProductsFormatted(products);
    }

    @Override
    public String calculateId() {
        return OrderInterface.super.calculateId();
    }

    @Override
    public String getNotification() {
        return NotificationOrder.getNotification(this);
    }

    @Override
    public BigDecimal getSum() {
        return OrderInterface.super.getSum();
    }

    @Override
    public ZonedDateTime getStorageTime() {
        return OrderInterface.super.getStorageTime();
    }

    @Override
    public String getStorageTimeFormatted() {
        return FormatOrder.getStorageTimeFormatted(this);
    }

    @Override
    public void setCollectingDateTime(ZonedDateTime zonedDateTime) {
        OrderInterface.super.setCollectingDateTime(zonedDateTime);
        this.collectingDateTime = zonedDateTime;
    }

    @Override
    public void setId(String id) {
        OrderInterface.super.setId(id);
        this.id = id;
    }

    @Override
    public void setReceivingDateTime(ZonedDateTime receivingDateTime) {
        OrderInterface.super.setReceivingDateTime(receivingDateTime);
        this.receivingDateTime = receivingDateTime;
    }

    @Override
    public void setFullName(String fullName) {
        OrderInterface.super.setFullName(fullName);
        this.fullName = fullName;
    }

    @Override
    public void setPhone(String phone) {
        OrderInterface.super.setPhone(phone);
        this.phone = phone;
        this.id = calculateId();
    }

    @Override
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatingDateTime() {
        return creatingDateTime;
    }

    @Override
    public ZonedDateTime getCollectingDateTime() {
        return collectingDateTime;
    }

    @Override
    public ZonedDateTime getReceivingDateTime() {
        return receivingDateTime;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public Appliance[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "status=" + status +
                ", id='" + id + '\'' +
                ", creatingDateTime=" + creatingDateTime +
                ", collectingDateTime=" + collectingDateTime +
                ", receivingDateTime=" + receivingDateTime +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", products=" + Arrays.toString(products) +
                '}';
    }


    public static void main(String[] args) {
        TestOrder.testNotification();
        TestOrder.testIsExpired();
    }
}
