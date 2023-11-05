package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.task5.base.Appliance;
import homework.chernetsov.task7.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface OrderInterface {

    void collect();
    void collect(ZonedDateTime time);

    void give();
    void give(ZonedDateTime time);

    boolean isExpiredAndUpdate();
    boolean isExpiredAndUpdate(ZonedDateTime time);

    default boolean canReceive() throws ReceivingDateException {
        if (getCollectingDateTime() == null) {
            return false;
        } else if (getCollectingDateTime() != null && getStorageTime().isAfter(getReceivingDateTime())) {
            return true;
        } else {
            throw new ReceivingDateException(getStorageTime(), getReceivingDateTime());
        }
    }

    String getNotification();

    default BigDecimal getSum() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < getProducts().size(); i++) {
            total = total.add(BigDecimal.valueOf(getProducts().get(i).getPrice()));
        }
        return total;
    }

    default ZonedDateTime getStorageTime() {
        return getCollectingDateTime().plusWeeks(2);
    }

    String getStorageTimeFormatted();

    default void setFullName(String fullName) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]* [A-Z][a-z]*( [A-Z][a-z]*)?");
        Matcher matcher = pattern.matcher(fullName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Sorry, that's the wrong name");
        }
    }

    default void setPhoneUpdateId(String phone) {
        if (phone == null || phone.length() < 4) {
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

    ArrayList<Appliance> getProducts();
}
