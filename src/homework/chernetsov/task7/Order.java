package homework.chernetsov.task7;


import homework.chernetsov.task5.base.Appliance;
import homework.chernetsov.task7.serviceOrder.FormatOrders;
import homework.chernetsov.task7.serviceOrder.NotificationOrders;
import homework.chernetsov.task7.serviceOrder.OrderInterface;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


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
    private final ArrayList<Appliance> products;


    public Order(String fullName, String phone, ZonedDateTime creatingDateTime, ArrayList<Appliance> products) {
        if (products == null || products.size() == 0) {
            throw new IllegalArgumentException("Sorry, the order must contain at least one product");
        }
        if (products.size() > 75) {
            throw new IllegalArgumentException("Sorry, the order does not support more than 75 items");
        }
        if (creatingDateTime == null) {
            throw new IllegalArgumentException("Sorry, the order must have a creation time");
        }
        this.products = products;
        this.creatingDateTime = creatingDateTime;
        setFullName(fullName);
        setPhoneUpdateId(phone);
        status = OrderStatus.CREATED;
    }

    @Override
    public void collect() {
        collect(ZonedDateTime.now());
    }

    @Override
    public void collect(ZonedDateTime time) {
        if (isExpiredAndUpdate(time) || status != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Sorry, the order cannot be collected because it is " +
                    status.toString());
        }
        if (time == null || time.isBefore(creatingDateTime)) {
            throw new IllegalArgumentException("Sorry, the build time must be after the order creation time");
        }
        collectingDateTime = time;
        status = OrderStatus.COLLECTED;
    }

    @Override
    public void give() {
        give(ZonedDateTime.now());
    }

    @Override
    public void give(ZonedDateTime time) {
        if (isExpiredAndUpdate(time) || status != OrderStatus.COLLECTED) {
            throw new IllegalArgumentException("Sorry, the order cannot be given because it is " +
                    status.toString());
        }
        if (time == null || time.isBefore(collectingDateTime)) {
            throw new IllegalArgumentException("Sorry, the giving time must be after the collection time");
        }
        receivingDateTime = time;
        status = OrderStatus.CLOSED;
    }

    @Override
    public boolean isExpiredAndUpdate() {
        return isExpiredAndUpdate(ZonedDateTime.now());
    }

    @Override
    public boolean isExpiredAndUpdate(ZonedDateTime time) {
        if (status == OrderStatus.COLLECTED) {
            if (time == null || time.isBefore(collectingDateTime)) {
                throw new IllegalArgumentException("Sorry, the time must be after the collection time");
            }
            if (time.isAfter(getStorageTime())) {
                status = OrderStatus.EXPIRED;
            }
        }
        return status == OrderStatus.EXPIRED;
    }

    @Override
    public String getNotification() {
        isExpiredAndUpdate();
        return NotificationOrders.getNotification(this);
    }

    @Override
    public String getStorageTimeFormatted() {
        return FormatOrders.getStorageTimeFormatted(this);
    }

    @Override
    public void setFullName(String fullName) {
        OrderInterface.super.setFullName(fullName);
        this.fullName = fullName;
    }

    @Override
    public void setPhoneUpdateId(String phone) {
        OrderInterface.super.setPhoneUpdateId(phone);
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
    public ArrayList<Appliance> getProducts() {
        return new ArrayList<>(products);
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
                ", products=" + products.toString() +
                '}';
    }

    private String calculateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return creatingDateTime.format(formatter) + phone.substring(phone.length() - 4);
    }
}