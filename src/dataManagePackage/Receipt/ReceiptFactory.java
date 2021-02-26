package dataManagePackage.Receipt;

import java.util.Arrays;

public class ReceiptFactory {
	
	public static Receipt createNewReceipt( String kind, String id, String date,
											String amount, String name, String country, 
											String city, String street, String number){
		
		//If you want to add a new type of Receipt, add it here
		String[] kinds = new String[]{"Basic","Travel","Health","Entertainment","Other"}; 
		
		if(!Arrays.stream(kinds).anyMatch(kind::equals)) {
			System.out.println("Receipt of kind: \""+kind+"\" not supported");
			System.out.println("Exiting now");
			System.exit(0);
		}
		
		return new Receipt(kind, id, date, amount, name, country, city, street, number);
		
	}
}
