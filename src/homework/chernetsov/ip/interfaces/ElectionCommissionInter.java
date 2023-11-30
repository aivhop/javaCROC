package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;

import java.util.List;

public interface ElectionCommissionInter {
    boolean createElector(Elector elector) throws ConnectionException;

    Elector findElector(String passportSeriesNumber) throws ConnectionException;

    boolean updateElector(Elector elector) throws ConnectionException;

    boolean deleteElector(String passportSeriesNumber) throws ConnectionException;

    List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException;

    List<Elector> getElectorsBySurname(String surname) throws ConnectionException;

    List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException;
    List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException;
    List<Elector> getElectorsByOpportunityVote(boolean hasOpportunityVote) throws ConnectionException;

    boolean isElectorCanVote(String passportSeriesNumber) throws ConnectionException;
    boolean isElectorCanVote(String passportSeriesNumber, int precinctId) throws ConnectionException;//method for communication with the project on the topic 27

}
