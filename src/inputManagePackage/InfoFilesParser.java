package inputManagePackage;

import java.util.Scanner;

import dataManagePackage.Taxpayer;
import dataManagePackage.Receipt.Receipt;
import dataManagePackage.Receipt.ReceiptFactory;

public class InfoFilesParser {
	
	private static InfoFilesParser instance;

	public Taxpayer createTaxpayer(Scanner inputStream, String[][] identifiers) {
		String[] starts = identifiers[0];
		String[] ends = identifiers[1];
		String taxpayerName = getParameterValueFromFileLine(inputStream.nextLine(), starts[0], ends[0]);
		String taxpayerAFM = getParameterValueFromFileLine(inputStream.nextLine(), starts[1], ends[1]);
		String taxpayerStatus = getParameterValueFromFileLine(inputStream.nextLine(), starts[2], ends[2]);
		String taxpayerIncome = getParameterValueFromFileLine(inputStream.nextLine(), starts[3], ends[3]);
		
		Taxpayer taxpayer = new Taxpayer(taxpayerName, taxpayerAFM, taxpayerStatus, taxpayerIncome);
		
		String fileLine;
		while (inputStream.hasNextLine())
		{
			fileLine = inputStream.nextLine();
			if (fileLine.equals("")) continue;
			if (isReceiptsStartIdentifier(fileLine)) continue;
			if (isReceiptsEndIdentifier(fileLine)) break;
			
			Receipt newReceipt = createReceipt(fileLine, inputStream, starts, ends);
			
			taxpayer.addReceiptToList(newReceipt);
		}
		
		return taxpayer;
	}
	
	
	private Receipt createReceipt(String fileLine, Scanner inputStream,
										String[] starts, String[] ends) {
		
		String receiptID = getParameterValueFromFileLine(fileLine, starts[4], ends[4]);
		String receiptDate = getParameterValueFromFileLine(inputStream.nextLine(), starts[5], ends[5]);
		String receiptKind = getParameterValueFromFileLine(inputStream.nextLine(), starts[6], ends[6]);
		String receiptAmount = getParameterValueFromFileLine(inputStream.nextLine(), starts[7], ends[7]);
		String receiptCompany = getParameterValueFromFileLine(inputStream.nextLine(), starts[8], ends[8]);
		String receiptCountry = getParameterValueFromFileLine(inputStream.nextLine(), starts[9], ends[9]);
		String receiptCity = getParameterValueFromFileLine(inputStream.nextLine(), starts[10], ends[10]);
		String receiptStreet = getParameterValueFromFileLine(inputStream.nextLine(), starts[11], ends[11]);
		String receiptNumber = getParameterValueFromFileLine(inputStream.nextLine(), starts[12], ends[12]);
		
		return ReceiptFactory.createNewReceipt(receiptKind, receiptID, receiptDate, receiptAmount, receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
		
	}


	private String getParameterValueFromFileLine(String fileLine, String parameterStartField, String parameterEndField){
		return fileLine.substring(parameterStartField.length(), fileLine.length()-parameterEndField.length());
	}
	
	private boolean isReceiptsStartIdentifier(String fileLine) {
		return fileLine.indexOf("<Receipts>")!=-1 || fileLine.indexOf("Receipts:")!=-1;
	}
	
	private boolean isReceiptsEndIdentifier(String fileLine) {
		return fileLine.indexOf("</Receipts>")!=-1;
	}
	
	
	public static InfoFilesParser getInstance() {
		if (instance == null) {
			instance = new InfoFilesParser();
		}
		return instance;
	}
	

}
