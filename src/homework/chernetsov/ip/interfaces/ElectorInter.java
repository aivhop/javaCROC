package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

public interface ElectorInter {
    boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException;
    void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException;
    boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;
    boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;
    boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException;
}
//todo дата рождения
//todo получил бюллетень
//todo статистика по возрастам
