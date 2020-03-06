/**
 * This class represents a GeneralLedgerManager which drives the program
 * @author Rohan Dayal
*/

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class GeneralLedgerManager {
	/**
	 * This is the main method that runs the entire simulation of the 
	 * GeneralLedger and Transaction classes.
	 * @param args standard main method
	 */
	public static void main(String[]args) {
		Scanner rod = new Scanner(System.in);
		GeneralLedger theLedger = new GeneralLedger();
		GeneralLedger backup = null;
		String entd = "z";
		while(entd.equals("Q")==false) {
		System.out.println("(A) Add Transaction");
		System.out.println("(G) Get Transaction");
		System.out.println("(R) Remove Transaction");
		System.out.println("(P) Print Transactions in General Ledger");
		System.out.println("(F) Filter by Date");
		System.out.println("(L) Look for Transaction");
		System.out.println("(S) Size");
		System.out.println("(B) Backup");
		System.out.println("(PB) Print Transactions in Backup");
		System.out.println("(RB) Revert to Backup");
		System.out.println("(CB) Compare Backup with Current");
		System.out.println("(PF) Print Financial Information");
		System.out.println("(Q) Quit");
		System.out.println();
		System.out.println("Enter a selection:");
		entd = rod.next();
		entd = entd.toUpperCase();
		/**
		 * @exception TransactionAlreadyExistsException, 
		 * FullGeneralLedgerException,InvalidTransactionException.
		 * When 'A' entered, handles exceptions listed
		 * above, and general exception so code no crash. 
		 */
		
		if(entd.equals("A")) {
			System.out.println("Enter transaction date.");
			String date = rod.next();
			System.out.println("Enter transaction amount.");
			double amount = rod.nextDouble();
				
			/*catch(Exception e) {
				System.out.println("Amount not entered correctly. Must enter "
						+ "a double value");
			}*/
			System.out.println("Enter transaction description.");
			rod.nextLine();
			String descrip = rod.nextLine();
			System.out.println();
			Transaction novel = new Transaction(descrip, amount, date);
			try {
			theLedger.addTransaction(novel);
			System.out.println("Transaction added to the general ledger.");
			}
			catch(TransactionAlreadyExistsException e) {
				System.out.println(e.getMessage());
			}
			catch(FullGeneralLedgerException e) {
				System.out.println(e.getMessage());
			}
			catch(InvalidTransactionException e) {
				System.out.println(e.getMessage());
			}
			catch(Exception e) {
				System.out.println("Something went wrong. Please try again.");
				continue;
			}
		}
		/**
		 * @exception InvalidLedgerPositionException handled in case position
		 * entered that is not in the ledger
		 */
		if(entd.equals("G")){
			System.out.println("Please enter the index of the transaction you"
					+ " would like to get.");
			int index = rod.nextInt();
			try {
				System.out.println(theLedger.getTransaction(index).toString
						(index));
			}
			catch (InvalidLedgerPositionException e) {
				System.out.println(e.getMessage());
			}
			catch(Exception e) {
				System.out.println("Hmm, something went wrong."
						+ " Please try again.");
			}
		}
		/**
		 * @exception InvalidLedgerPositionException handled in case position
		 * entered that is not in the ledger
		 */
		if(entd.equals("R")) {
			System.out.println("Please enter the index of the transaction you "
					+ "would like to remove.");
			int index = rod.nextInt();
			try {
			theLedger.removeTransaction(index);
			System.out.println("Transaction successfully removed.");
			}
			catch(InvalidLedgerPositionException e) {
				System.out.println(e.getMessage());
			}
		}
		if(entd.equals("P")) {
			theLedger.printAllTransactions();	
		}
		if(entd.equals("F")) {
			System.out.println("Enter Date in format YYYY/MM/DD: ");
			String date = rod.next();
			GeneralLedger.filter(theLedger, date);
		}
		/**
		 * @exception InvalidLedgerPositionException is used to catch
		 * case where getTransaction(x+1) refers to invalid position,
		 * though this shouldn't ever happen due to the if check at the
		 * top of the block for theLedger.exists(searching)
		 */
		if(entd.equals("L")) {
			System.out.println("Please enter transaction date.");
			String date = rod.next();
			System.out.println("Please enter transaction amount.");
			double amt = rod.nextDouble();
			System.out.println("Please enter transaction description.");
			rod.nextLine();
			String des = rod.nextLine();
			Transaction searching = new Transaction(des, amt, date);
			if(theLedger.exists(searching)) {
				System.out.println("Yes, the transaction exists in the ledger."
						);
				int index=0;
				for(int x=0;x<theLedger.size();x++) {
					try {
						if(theLedger.getTransaction(x+1).getAmt()==amt&&
								theLedger.getTransaction(x+1).getDate().equals(
										searching.getDate())
								&&theLedger.getTransaction(x+1).getDescription(
										).equals(searching.getDescription())){
									index = x; 
									break;
								}
					} catch (InvalidLedgerPositionException e) {
						System.out.println(e.getMessage());
					}		
				}
				System.out.println(searching.toString(index+1));
			}
			else {
				System.out.println("No, the transaction does not exist in the "
						+ "ledger");
			}
		}
		if(entd.equals("S")) {
			System.out.println("The current general ledger has " +
		theLedger.size() + " transactions.");
		}
		if(entd.equals("B")) {
			backup = (GeneralLedger)theLedger.clone();
			if(theLedger.size()==0) {
				System.out.println("Backup created of General Ledger of size 0"
						);
			}
			else
				System.out.println("Created backup of current general ledger");
			
		}
		if(entd.equals("PB")) {
			if(backup==null) {
				System.out.println("Sorry, no backup exists.");
			}
			else {
				backup.printAllTransactions();
			}
		}
		if(entd.equals("RB")) {
			if(backup==null) {
				System.out.println("Sorry, no backup exists.");
			}
			else {
				theLedger = (GeneralLedger)backup.clone();
				System.out.println("Successfully reverted general ledger to "
						+ "backup.");
			}
		}
		/**
		 * @exception InvalidLedgerPositionException implemented here in case
		 * getTransaction function is given an invalid input value.
		 */
		if(entd.contentEquals("CB")) {
			boolean same = true;
			if(backup.size()!=theLedger.size()) {
				System.out.println("The two ledgers are not equal.");
				same=false;
				}
			else {
				for(int x=0;x<backup.size();x++) {
					try {
						if(backup.getTransaction(x+1).equals(
								theLedger.getTransaction(x+1))==false) {
							System.out.println("The backup and general "
									+ "ledger are not the same");
							same = false;
							break;
						}
					} catch (InvalidLedgerPositionException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
			}
			if(same)
				System.out.println("The backup ledger is the same as the genera"
						+ "l ledger.");
		}
		if(entd.contentEquals("PF")) {
			System.out.println("Financial data for account");
			System.out.println("Assets: $" + theLedger.getDebit());
			System.out.println("Liabilities: $" + theLedger.getCredit());
			double net = theLedger.getDebit() - theLedger.getCredit();
			System.out.println("Net worth: $" + net);
		}
		if(entd.equals("Q")) {
			System.out.println("Program terminating successfully");
			break;
		}
		
		System.out.println();
			
	}
}
}
