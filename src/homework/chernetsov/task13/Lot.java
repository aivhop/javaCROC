package homework.chernetsov.task13;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Lot {
    private BigDecimal currentValue;
    private Participant currentParticipant;
    private ZonedDateTime endTime;

    public Lot(BigDecimal currentValue, Participant currentParticipant, ZonedDateTime endTime) {
        this.currentValue = currentValue;
        this.currentParticipant = currentParticipant;
        this.endTime = endTime;
    }
//todo synch
    synchronized public BigDecimal getCurrentValue() {
        return currentValue;
    }

    synchronized public Participant getWinner(ZonedDateTime atTime){ //todo проверь в телеграмме
        return null;
    }

    synchronized public boolean  placeBet(Participant participant, BigDecimal value){ //todo время ставки, now или передаваемое значение
        ZonedDateTime timeBet = ZonedDateTime.now();
        if(value.compareTo(currentValue) > 0 && timeBet.isBefore(endTime)){
            currentValue = value;
            endTime = timeBet; // todo время действия ставки в конструкторе как новое поле лота или как передаваемое значение
            return true;
        }
        //todo exception или false в случае, если ставку уже делать нельяз
        return false;
    }
}
