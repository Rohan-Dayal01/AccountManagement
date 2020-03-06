/**
 * @author Rohan Dayal
 * 
 * class defines InvalidLedgerPositionException and extends Exception.
 * Should be thrown when ledger position value entered is invalid
 *
 */
public class InvalidLedgerPositionException extends Exception{
	/**
	 * 
	 * @param message is String message to be output when an
	 * InvalidLedgerPositionException is thrown.
	 */
	public InvalidLedgerPositionException(String message) {
		super(message);
	}
}
