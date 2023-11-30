package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.exceptions.ConnectionException;

public interface ElectorInter {
    boolean isElectorCanVote(String passportSeriesNumber) throws ConnectionException;

    boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException;
    boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;

    boolean isElectorCanReceiveBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException;
}
