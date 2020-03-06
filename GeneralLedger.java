/**
 * @author Rohan Dayal
 * ID Num: 112768867
 * Recitation 02
 * 
*/

package cse214;
/**
 * This class defines a GeneralLedger which is the primary object utilized
 * in this bank ledger application. GeneralLedger has MAX_TRANSACTIONS,
 * totalDebitAmount, totalCreditAmount, Transaction[] ledger to store the 
 * transactions, manyItems to keep a running track of the number of 
 * transactions.
*/
public class GeneralLedger {
	private static final int MAX_TRANSACTIONS= 50;
	private double totalDebitAmount =0;
	private double totalCreditAmount =0;
	private Transaction [] ledger;
	private int manyItems = 0;
	/**
	 * This is a constructor for the GeneralLedger class used to create new
	 * GeneralLedger object.
	 */
	public GeneralLedger() {
		ledger = new Transaction[this.MAX_TRANSACTIONS];
	}
	/**
	 * Getter method for private double totalDebitAmount
	 * @return value for totalDebitAmount for calling GeneralLedger
	 */
	public double getDebit() {
		return totalDebitAmount;
	}
	/**
	 * Getter method for private double totalCreditAmount
	 * @return value for totalCreditAmount for calling GeneralLedger
	 */
	public double getCredit() {
		return totalCreditAmount;
	}
	
	/**
	 * Method adds new Transaction object to the GeneralLedger
	 * @param newTransaction is the new Transaction to be added
	 * @throws InvalidTransactionException
	 * Is thrown when the Transaction's amount value is 0 or
	 * when the date entered does not fall within parameters of between [1900
	 * 2050] for year, [1,12] for month, and [1,30] for day
	 * @throws FullGeneralLedgerException
	 * Is thrown when the calling GeneralLedger has size of 50 (i.e. manyItems
	 * ==50)
	 * @throws TransactionAlreadyExistsException
	 * Is thrown when the newTransaction object has the same values for date,
	 * description, and amount as a Transaction already in the GeneralLedger 
	 */
	public void addTransaction(Transaction newTransaction) throws 
	InvalidTransactionException, FullGeneralLedgerException, 
	TransactionAlreadyExistsException{//by default, add 
		//to end of list checks for whether valid transaction (date, amount, 
		//not removed repeated)
		
		if(newTransaction.getAmt()==0) {
			throw new InvalidTransactionException("Invalid transaction of "
					+ "amount $0.");
		}
		else if(newTransaction.validdate()==false) {
			throw new InvalidTransactionException("Invalid date entered.");
		}
		if(manyItems==(MAX_TRANSACTIONS))
			throw new FullGeneralLedgerException("Ledger is full.");
		if(exists(newTransaction))
			throw new TransactionAlreadyExistsException(
					"Action failed: duplicate transaction.");
		if(newTransaction.getAmt()>0)
			totalDebitAmount+=newTransaction.getAmt();
		else if(newTransaction.getAmt()<0)
			totalCreditAmount+=Math.abs(newTransaction.getAmt());
		ledger[manyItems]= (Transaction)newTransaction.clone();
		
		manyItems++;
		this.orderer();
		
	}
	/**
	 * Removes a transaction from the GeneralLedger by moving it all the way
	 * to a position 
	 * @param position
	 * Is the position in the GeneralLedger (numbered from 1 through 50)
	 * that the user would like to be removed.
	 * @throws InvalidLedgerPositionException
	 * Is thrown when the user enters a position in the GeneralLedger to be removed 
	 * that does not contain a Transaction object. Thrown if position>manyItems 
	 * or if position is 0 or negative.
	 */
	public void removeTransaction(int position) throws 
	InvalidLedgerPositionException{
		if(position>manyItems)
			throw new InvalidLedgerPositionException("Action failed: "
					+ "position out of bounds");
		else if(position<1)
			throw new InvalidLedgerPositionException("Action failed: "
					+ "Position must be >=1");
		else {
			int arraypos = position-1;
			Transaction removable = (Transaction)ledger[arraypos].clone();
			if(removable.getAmt()>0)
				totalDebitAmount-=removable.getAmt();
			else if(removable.getAmt()<0)
				totalCreditAmount-=removable.getAmt();
			if(arraypos!=MAX_TRANSACTIONS -1) {
			for(int x=arraypos;x<manyItems-1;x++) {
				ledger[x] = (Transaction)ledger[x+1].clone();
			}
			manyItems--;}
			else if(arraypos==MAX_TRANSACTIONS-1) {
				ledger[arraypos] = null;
			}
			//try
			this.orderer();
			/*catch (Exception e)
				System.out.println("Something went wrong with the sorting.");*/
			
		}
	}
	/**
	 * Getter method for the Transaction object which the user seeks to get
	 * @param position is the position in the GeneralLedger ledger[] which
	 * the user would like to access.
	 * @return a reference to the Transaction from the desired position in 
	 * ledger
	 * @throws InvalidLedgerPositionException
	 * When position input is larger than number of Transactions in the 
	 * ledger[] (represented by manyItems). Also thrown when position
	 * entered is 0 or negative
	 */
	public Transaction getTransaction(int position) throws 
	InvalidLedgerPositionException{
		if(position>manyItems)
			throw new InvalidLedgerPositionException("No such transaction");
		else if(position<1)
			throw new InvalidLedgerPositionException("Position must be<=1");
		else
			return ledger[position-1];
	}
	/**
	 * Method to return size of GeneralLedger (number of transactions)
	 * @return manyItems, which is a counter variable for the number of
	 * Transaction objects currently in the GeneralLedger.
	 */
	public int size() {
		return manyItems;
	}
	/**
	 * Method to create deep clone of the current GeneralLedger
	 * @return a clone object of the GeneralLedger called nuevo 
	 * if no Exceptions.
	 * If any exceptions, code catches and prints error message, 
	 * and returns null (the default value of backup in GeneralLedgerManager
	 * @exception InvalidTransactionException
	 * If Transaction has invalid date or amount. Caught 
	 * because using addTransaction method. However, should never actually
	 * be called as the original ledger checked when Transactions instantiated.
	 * addTransaction specifies when to throw.
	 * @exception FullGeneralLedgerException
	 * If GeneralLedger is full. Caught because using addTransaction method
	 * which specifies when to throw it
	 * @exception TransactionAlreadyExistsException
	 * If Transaction being added is duplicate. Caught because of 
	 * addTransaction which specifies when to throw.
	 * @exception Exception to catch any non-obvious exceptions to
	 * prevent program from crashing
	 */
	public Object clone(){
		try {
		GeneralLedger nuevo = new GeneralLedger();
		for(int x=0;x<manyItems;x++) {
			Transaction novel = (Transaction)ledger[x].clone();
			nuevo.addTransaction(novel);
		}
		return nuevo;
		}
		catch(InvalidTransactionException e) {
			System.out.println(e.getMessage());
		}
		catch(FullGeneralLedgerException e) {
			System.out.println(e.getMessage());
		}
		catch(TransactionAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Something is wrong here.");
		}
		return null;
		
	}
	/**
	 * Method which checks if a Transaction already exists in ledger[] by
	 * comparing Transaction date, amount, and description values.
	 * @param t is the Transaction which is being checked to see if it is
	 * in the GeneralLedger ledger[]
	 * @return boolean to say whether the a Transaction with the same date,
	 * amount, and description already exists in ledger[]
	 * @throws IllegalArgumentException if t passed through is not a
	 * transaction.
	 */
	public boolean exists(Transaction t) throws IllegalArgumentException{
		if((t instanceof Transaction)==false)
			throw new IllegalArgumentException();
		for(int x=0;x<manyItems;x++) {
			if(ledger[x].getAmt()==t.getAmt()&&
					ledger[x].getDate().equals(t.getDate())
					&&ledger[x].getDescription().equals(t.getDescription()))
				return true;
		}
		return false;
			
	}
	/**
	 * Method to convert the entire GeneralLedger into one String object
	 * which can be returned
	 * @return String table which represents the GeneralLedger which the
	 * user is seeking to make into a String
	 */
	public String toString(){
		String table =String.format("%-10s%-30s%-20s%-20s%-40s\n",
				"No.", "Date", "Debit", "Credit", "Description");
		table+="---------------------------------------------------------------"
				+ "------------------------------------------ \n";
		for(int x=0;x<manyItems;x++) {
			if(ledger[x].getAmt()>0) {				
				String addendum = String.format("%-10d%-30s%-40.2f%-40s \n", (x+1),
						ledger[x].getDate(),ledger[x].getAmt(),
						ledger[x].getDescription());
				table = table + addendum;
			}
			else {//since negative number, include absolute value in credit column
				String addendum = String.format("%-10d%-50s%-20.2f%-40s \n", (x+1),
						ledger[x].getDate(),Math.abs(ledger[x].getAmt()),
						ledger[x].getDescription());
				table = table + addendum;
			}
		}
		return table;
	}
	/**
	 * Method to print all of the transactions in the GeneralLedger. Calls
	 * the toString method of the GeneralLedger class.
	 */
	public void printAllTransactions() {
		if(manyItems==0) {
			System.out.println("This ledger is currently empty.");
		}
		else {
			System.out.println(this.toString());
			}
	}
	/**
	 * Method to print out all the transactions in a GeneralLedger for a given
	 * date. If no Transactions exist on that date, will respond accordingly. 
	 * Otherwise will print all information for Transactions on date 
	 * @param general is the GeneralLedger that the user would like to
	 * be filtered by date
	 * @param date is a String representing the date which the user seeks to
	 * to filter by
	 * @exception FullGeneralLedgerException
	 * Must be caught due to using the addTransaction to create temporary
	 * GeneralLedger to check if any Transactions on given date.
	 * @exception InvalidLedgerPositionException
	 * Must be caught because using getTransaction method which throws
	 * this if an invalid index value is entered
	 * @exception InvalidTransactionException
	 * Must be caught because using addTransaction. addTransaction
	 * specifies when to throw InvalidTransactionException
	 * @exception TransactionAlreadyExistsException
	 * Must be caught because using addTransaction which specifies
	 * to throw this exception when a duplicate Transaction is entered
	 * @exception Exception is caught to ensure that the program does not
	 * crash in case of any unforeseen problems.
	 */
	public static void filter(GeneralLedger general, String date) {	
		try {
		GeneralLedger filted = new GeneralLedger();
		for(int x=0;x<general.size();x++) {
			if(general.getTransaction(x+1).getDate().equals(date)) {
				filted.addTransaction((Transaction)general.getTransaction(x+1).
						clone());
			}
		}
		if(filted.size()==0) {
			System.out.println("No transactions for this date.");
		}
		else {
			String table =String.format("%-10s%-30s%-20s%-20s%-40s\n",
					"No.", "Date", "Debit", "Credit", "Description");
			table+="-----------------------------------------------------------"
					+ "---------------------------------------------- \n";
			for(int x=0;x<general.size();x++) {
				if(general.getTransaction(x+1).getDate().equals(date)) {
					if(general.getTransaction(x+1).getAmt()>0) {
						//would have to include a value in the debit column
						String addendum = String.format("%-10d%-30s%-40.2f%-40s"
								+ " \n", (x+1),
								general.getTransaction(x+1).getDate(),
								Math.abs(general.getTransaction(x+1).getAmt()),
								general.getTransaction(x+1).getDescription());
						table = table + addendum;
					}
					else {
						//since negative number, include absolute value in credit column
						String addendum = String.format("%-10d%-50s%-20.2f%-40s"
								+ " \n", (x+1),
								general.getTransaction(x+1).getDate(),
								Math.abs(general.getTransaction(x+1).getAmt()),
								general.getTransaction(x+1).getDescription());
						table = table + addendum;
					}					
				}
			}
			System.out.println(table);
		}
		}
		catch(FullGeneralLedgerException e) {
			System.out.println(e.getMessage());
		}
		catch(InvalidLedgerPositionException e) {
			System.out.println(e.getMessage());
		}
		catch(InvalidTransactionException e) {
			System.out.println(e.getMessage());
		}
		catch(TransactionAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Something went wrong.");
		}
		
		
		
	}
	/**
	 * Method implements insertion sort on the ledger[] to ensure ledger
	 * is ordered by date. Latest transactions will be later in the ledger[]
	 * and earlier transactions will be at beginning of ledger
	 */
	public void orderer() {//implements insertion sort
		if(manyItems!=0) {
			for(int x=1;x<manyItems;x++) {
				for(int y=x;y>0;y--) {
					int numdate1 = Integer.parseInt(
							ledger[y].getDate().substring(0,4) + 
							ledger[y].getDate().substring(5, 7) + 
							ledger[y].getDate().substring(8));
					int numdate2 = Integer.parseInt(
							ledger[y-1].getDate().substring(0,4) + 
							ledger[y-1].getDate().substring(5, 7) + 
							ledger[y-1].getDate().substring(8));
					if(numdate1-numdate2<0) {
						Transaction temp = (Transaction)ledger[y].clone();
						ledger[y] = (Transaction)ledger[y-1].clone();
						ledger[y-1] = (Transaction)temp.clone();
					}
						
				}
			}
		}
	}
	
	
	
}
