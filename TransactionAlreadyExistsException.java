/**
 * @author Rohan Dayal
 * ID Num: 112768867
 * Recitation 02
 * 
*/

package cse214;
/**
 * defines class for TransactionAlreadyExistsException and extends Exception
 * Specified when to call in GeneralLedger class
 *
 */
public class TransactionAlreadyExistsException extends Exception{
	/**
	 * Returns a String message from the method which throws the exception
	 * @param message The message that the throwing method is sending to be
	 * output if a duplicate Transaction is made.
	 */
	public TransactionAlreadyExistsException(String message) {
		super(message);
	}
}
