
/**
 * @author Rohan Dayal
 * 	
 * This class represents a Transaction that has a description, amount, and date
 */
public class Transaction {
	private String description;
	private double amount;
	private String date;

/**
 * This method constructs Transaction objects	
 * @param des
 * Is the String description of the Transaction
 * @param amt 
 * Is the double amount of the Transaction
 * @param dt 
 * Is the String date of the Transaction
 */
public Transaction(String des, double amt, String dt) {
	description = des;
	amount = amt;
	date = dt;
	
}
/**
 * Getter for private double amount
 * @return
 * Returns the amount double for the Transaction object
 */
public double getAmt () {
	return amount;
}
/**
 * Getter for private String description
 * @return
 * Returns the String description for the Transaction object
 */
public String getDescription() {
	return description; 
}
/**
 * Getter for private String date
 * @return
 * Returns the String date for the Transaction object
 */
public String getDate() {
	return date; //returns date given for this transaction
}
/**
 * Method to create deep-clone of calling Transaction object.
 * @return
 * Returns a deep-cloned new Transaction object as an Object.
 * Note: Must be typecast as Transaction in calling method.
 */
public Object clone() {
	return new Transaction(this.getDescription(), this.getAmt(), this.getDate()
			); //returns new Object which is a Transaction with the same 
	//attributes for amount, date, and description as this transaction
}
/**
 * Method to see if two Transactions are equal.
 * @param obj is the object we will compare to the this calling Transaction 
 * object
 * Method checks to ensure obj is an instanceof Transaction before comparing 
 * parameters. Then, typecasts obj as Transaction.
 * @return boolean representing whether the input obj has all the same values
 * as this calling Transaction object.
 */
public boolean equals(Object obj) {
	if(obj instanceof Transaction) {
	Transaction t = (Transaction)obj;
	if(this.getAmt()==t.getAmt() && this.getDate()==t.getDate() && 
			this.getDescription()==this.getDescription())//checks to see if all 
		//the attribute values are equal
		return true;
	else
		return false;
	}
	else
		return false;
}
/**
 * Checks to see if date entered is in the valid
 * yyyy/mm/dd format. Date must be between 1900
 * and 2050, inclusive. Every month has 30 days (1 through 30)
 * @return boolean representing whether the String date is within the 
 * accepted parameters for a Transaction date
 */
public boolean validdate() {
	if(this.getDate().length()!=10)
		return false;
	else if(Integer.parseInt(this.getDate().substring(0,4))<1900 || 
			Integer.parseInt(
			this.getDate().substring(0,4))>2050) {
		return false;}
	else if(Integer.parseInt(this.getDate().substring(5,7))>12 ||
			Integer.parseInt(
			this.getDate().substring(5,7))<1) {
		return false;}
	else if(Integer.parseInt(this.getDate().substring(8))<1||
			Integer.parseInt(
			this.getDate().substring(8))>30) {
		return false;}
	else
		return true;
	
}
/**
 * Method returns a given transaction in the desired formatting to display
 * index in ledger(No.), Date, Debit, Credit, Description
 * @param index is the input index of the transaction in the original
 * GeneralLedger that it is from.
 * @return a formatted String, table, with column labels at the top and 
 * corresponding values for these columns with this given transaction
 */
public String toString(int index) {
	String table =String.format("%-10s%-30s%-20s%-20s%-40s\n",
			"No.", "Date", "Debit", "Credit", "Description");
	table+="-------------------------------------------------------------------"
			+ "-------------------------------------- \n";
	if(this.getAmt()>0) {//would have to include a value in the debit column
		String addendum = String.format("%-10d%-30s%-40.2f%-40s \n", index,
				this.getDate(),this.getAmt(),
				this.getDescription());
		table = table + addendum;
	}
	else {//since negative number, include absolute value in credit column
		String addendum = String.format("%-10d%-50s%-20.2f%-40s \n", index,
				this.getDate(),Math.abs(this.getAmt()),
				this.getDescription());
		table = table + addendum;
	}
	return table;
}
}
