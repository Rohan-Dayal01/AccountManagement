/**
 * @author Rohan Dayal
 * ID Num: 112768867
 * Recitation 02
 * 
*/
package cse214;
/**
 * class defines the InvalidTransactionException and extends Exception. 
 * called when the user seeks to create a Transaction with an amount =0 or a
 * date outside of the given constraints.
 *
 */
public class InvalidTransactionException extends Exception{
	/**
	 * @param message is String message the throwing method sends when 
	 * conditional is triggered. Passes the message to be output
	 */
	public InvalidTransactionException(String message) {
		super(message);
	}
	
}
