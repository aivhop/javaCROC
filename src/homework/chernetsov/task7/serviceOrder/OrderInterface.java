package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.correction.lastTask1.base.Appliance;
import homework.chernetsov.task7.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface OrderInterface {

    void collect();
    void collect(ZonedDateTime time);

    void give();
    void give(ZonedDateTime time);

    boolean isExpired();
    boolean isExpired(ZonedDateTime time);

    default boolean tryToGet() {
        if (getCollectingDateTime() == null) {
            return false;
        } else if (getCollectingDateTime() != null && getStorageTime().isAfter(getReceivingDateTime())) {
            return true;
        } else {
            throw new ReceivingDateException(getStorageTime(), getReceivingDateTime());
        }
    }

    String getProductsFormatted();

    String getProductsFormatted(int maxWidthOfMessage);

    String getProductsFormatted(String indent);

    String getProductsFormatted(int maxWidthOfMessage, String indent);


    default String calculateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return getCreatingDateTime().format(formatter) + getPhone().substring(getPhone().length() - 4);
    }

    String getNotification();

    default BigDecimal getSum() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < getProducts().length; i++) {
            total = total.add(BigDecimal.valueOf(getProducts()[i].getPrice()));
        }
        return total;
    }

    default ZonedDateTime getStorageTime() {
        return getCollectingDateTime().plusWeeks(2);
    }

    String getStorageTimeFormatted();


    default void setCollectingDateTime(ZonedDateTime collectingDateTime) {
        if (collectingDateTime == null || collectingDateTime.isBefore(getCreatingDateTime())) {
            throw new IllegalArgumentException("Sorry, we can't collect the order before it creates");
        }
    }

    default void setId(String id) {
        if (!id.equals(calculateId())) {
            throw new IllegalArgumentException("Sorry, this is the wrong id");
        }
    }

    default void setReceivingDateTime(ZonedDateTime receivingDateTime) {
        if (receivingDateTime == null || receivingDateTime.isBefore(getCollectingDateTime())) {
            throw new IllegalArgumentException("Sorry, it is not possible to receive an order before its collect");
        }
    }

    default void setFullName(String fullName) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]* [A-Z][a-z]*( [A-Z][a-z]*)?");
        Matcher matcher = pattern.matcher(fullName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Sorry, that's the wrong name");
        }
    }

    default void setPhone(String phone) {
        if (phone.length() < 4) {
            throw new IllegalArgumentException("The phone must consist of at least 4 digits");
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Sorry, numbers in Russia are given in numbers");
        }
    }

    Order.OrderStatus getStatus();

    String getId();

    ZonedDateTime getCreatingDateTime();

    ZonedDateTime getCollectingDateTime();

    ZonedDateTime getReceivingDateTime();

    String getFullName();

    String getPhone();

    Appliance[] getProducts();
}
