package homework.chernetsov.ip.interfaces;

import homework.chernetsov.ip.exceptions.ConnectionException;
import homework.chernetsov.ip.exceptions.ReceivingBulletinException;

/**
 * Interface defining methods related to elector registration and bulletin issuance.
 */
public interface ElectorInter {
    /**
     * Checks if an elector with the given passport series and number is registered in lists.
     *
     * @param passportSeriesNumber The passport series and number of the elector to check.
     * @return True if the elector is registered, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean isElectorRegistered(String passportSeriesNumber) throws ConnectionException;

    /**
     * Issues a bulletin to an elector for a specific precinct.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The precinct ID for issuing the bulletin.
     * @throws ConnectionException        If there is an issue with the database connection.
     * @throws ReceivingBulletinException If the elector is not eligible to receive a bulletin.
     */
    void issueBulletin(String passportSeriesNumber, int precinctId) throws ConnectionException, ReceivingBulletinException;

    /**
     * Checks if an elector can receive a bulletin in a specific precinct.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The precinct ID to check.
     * @return True if the elector can receive a bulletin in the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean isElectorCanReceiveBulletinOnThisPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;

    /**
     * Checks if an elector is registered in a specific precinct.
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @param precinctId           The precinct ID to check.
     * @return True if the elector is registered in the specified precinct, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean isElectorRegisteredOnPrecinct(String passportSeriesNumber, int precinctId) throws ConnectionException;

    /**
     * Checks if an elector can receive a bulletin(for this, he should not have received the bulletin before).
     *
     * @param passportSeriesNumber The passport series and number of the elector.
     * @return True if the elector can receive a bulletin, false otherwise.
     * @throws ConnectionException If there is an issue with the database connection.
     */
    boolean isElectorCanReceiveBulletin(String passportSeriesNumber) throws ConnectionException;
}

