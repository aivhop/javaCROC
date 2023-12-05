package homework.chernetsov.task13.auctionservices;

import java.math.BigDecimal;

public record Lot(BigDecimal startValue, String name) {
    public Lot(BigDecimal startValue, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sorry, the lot name can't be empty");
        }
        this.name = name;
        if (startValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sorry, the cost must be greater than zero");
        }
        this.startValue = startValue;
    }
}
