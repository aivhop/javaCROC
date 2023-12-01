package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.dbentity.Elector;
import homework.chernetsov.ip.exceptions.ConnectionException;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Interface defining methods for interacting with an election commission's database.
 */
public interface ElectionCommissionInter extends ElectorInter {
    /**
     * Retrieves a list of all electors from the database.
     *
     * @return A list of electors.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> readElectors() throws ConnectionException;

    /**
     * Creates a new elector in the database.
     *
     * @param elector The elector to be created.
     * @return True if the elector is successfully created, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean createElector(Elector elector) throws ConnectionException;

    /**
     * Creates multiple electors in the database.
     *
     * @param electors The list of electors to be created.
     * @return The number of electors successfully created.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    int createElectors(List<Elector> electors) throws ConnectionException;

    /**
     * Finds an elector in the database by passport series and number.
     *
     * @param passportSeriesNumber The passport series and number of the elector to find.
     * @return The elector if found, null otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    Elector findElector(String passportSeriesNumber) throws ConnectionException;

    /**
     * Updates an existing elector in the database.
     *
     * @param elector The elector with updated information.
     * @return True if the elector is successfully updated, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean updateElector(Elector elector) throws ConnectionException;

    /**
     * Deletes an elector from the database by passport series and number.
     *
     * @param passportSeriesNumber The passport series and number of the elector to delete.
     * @return True if the elector is successfully deleted, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean deleteElector(String passportSeriesNumber) throws ConnectionException;

    /**
     * Retrieves a list of electors with a specific first name.
     *
     * @param firstname The first name to filter by.
     * @return A list of electors with the specified first name.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsByFirstname(String firstname) throws ConnectionException;

    /**
     * Retrieves a list of electors with a specific surname.
     *
     * @param surname The surname to filter by.
     * @return A list of electors with the specified surname.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsBySurname(String surname) throws ConnectionException;

    /**
     * Retrieves a list of electors with a specific patronymic.
     *
     * @param patronymic The patronymic to filter by.
     * @return A list of electors with the specified patronymic.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsByPatronymic(String patronymic) throws ConnectionException;

    /**
     * Retrieves a list of electors in a specific precinct.
     *
     * @param precinctId The precinct ID to filter by.
     * @return A list of electors in the specified precinct.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsByPrecinctId(int precinctId) throws ConnectionException;

    /**
     * Retrieves a list of electors based on whether they have received a bulletin.
     *
     * @param bulletinHasBeenReceived Whether the elector has received a bulletin.
     * @return A list of electors based on the specified criteria.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsByBulletinHasBeenReceived(boolean bulletinHasBeenReceived) throws ConnectionException;

    /**
     * Retrieves a list of electors within a specific age range as of the given election date.
     *
     * @param age          The age to filter by.
     * @param electionDate The date of the election.
     * @return A list of electors within the specified age.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsByAge(int age, LocalDate electionDate) throws ConnectionException;

    /**
     * Retrieves a list of electors based on a custom predicate.
     *
     * @param electorPredicate The custom predicate to filter by.
     * @return A list of electors based on the specified predicate.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    List<Elector> getElectorsBy(Predicate<Elector> electorPredicate) throws ConnectionException;
}
