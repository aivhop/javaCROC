package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

import java.util.List;

public interface ElectionCommissionInter extends ElectorInter {

    List<Elector> readElectors() throws ConnectionException;

    boolean createElector(Elector elector) throws ConnectionException;

    int createElectors(List<Elector> electors) throws ConnectionException;

    Elector findElector(String passportSeriesNumber) throws ConnectionException;

    boolean updateElector(Elector elector) throws ConnectionException;

    boolean deleteElector(String passportSeriesNumber) throws ConnectionException;

    List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException;

    List<Elector> getElectorsBySurname(String surname) throws ConnectionException;

    List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException;

    List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException;

    List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException;

    //todo birthday statistic
}
