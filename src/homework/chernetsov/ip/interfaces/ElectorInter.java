package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

public interface ElectorInter {
    void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException;
    boolean isElectorCanVote(String passportSeriesNumber) throws ConnectionException;

    boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException;
    boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;

    boolean isElectorCanReceiveBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException;
}
