/**
 * @author Rohan Dayal
 * 
 * 
 * class defines FullGeneralLedgerException and extends Exception.
 * Should be thrown when seeking to add Transaction to a GeneralLedger
 * when the GeneralLedger is at its capacity
 */
public class FullGeneralLedgerException extends Exception {
	/**
	 * @param message String message which is from the throwing
	 * method. Passed to super for output
	 */
	public FullGeneralLedgerException(String message) {
		super(message);
	}
}
