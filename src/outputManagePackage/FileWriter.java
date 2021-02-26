package outputManagePackage;

import java.io.PrintWriter;

import dataManagePackage.Taxpayer;
import dataManagePackage.Receipt.Receipt;

public class FileWriter {
	
	private static FileWriter instance;
	
	public void writeTaxpayer(	PrintWriter outputStream, Taxpayer taxpayer, 
								String[] starts, String[] ends) {

		outputStream.println(starts[0]+taxpayer.getName()+ends[0]);
		outputStream.println(starts[1]+taxpayer.getAFM()+ends[1]);
		outputStream.println(starts[2]+taxpayer.getFamilyStatus()+ends[2]);
		outputStream.println(starts[3]+taxpayer.getIncome()+ends[3]);
	}

	public void writeReceipt(	PrintWriter outputStream,Receipt receipt, 
			String[] starts,String[] ends) {

		outputStream.println(starts[5] + receipt.getId() + ends[5]);
		outputStream.println(starts[6] + receipt.getDate() + ends[6]);
		outputStream.println(starts[7] + receipt.getKind() + ends[7]);
		outputStream.println(starts[8] + receipt.getAmount() + ends[8]);
		outputStream.println(starts[9] + receipt.getCompany().getName() + ends[9]);
		outputStream.println(starts[10] + receipt.getCompany().getCountry() + ends[10]);
		outputStream.println(starts[11] + receipt.getCompany().getCity() + ends[11]);
		outputStream.println(starts[12] + receipt.getCompany().getStreet() + ends[12]);
		outputStream.println(starts[13] + receipt.getCompany().getNumber() + ends[13]);
		outputStream.println();
	}
	
	public void writeLogFile(	PrintWriter outputStream,Taxpayer taxpayer, 
			String[] starts, String[] ends) {

		outputStream.println(starts[0]+taxpayer.getName()+ends[0]);
		outputStream.println(starts[1]+taxpayer.getAFM()+ends[1]);
		outputStream.println(starts[2]+taxpayer.getIncome()+ends[2]);
		outputStream.println(starts[3]+taxpayer.getBasicTax()+ends[3]);
		if (taxpayer.getTaxInxrease()!=0){
		outputStream.println(starts[4]+taxpayer.getTaxInxrease()+ends[4]);
		}else{
		outputStream.println(starts[5]+taxpayer.getTaxDecrease()+ends[5]);
		}
		outputStream.println(starts[6]+taxpayer.getTotalTax()+ends[6]);
		outputStream.println(starts[7]+taxpayer.getTotalReceiptsAmount()+ends[7]);
		outputStream.println(starts[8]+taxpayer.getReceiptsTotalAmount("Entertainment")+ends[8]);
		outputStream.println(starts[9]+taxpayer.getReceiptsTotalAmount("Basic")+ends[9]);
		outputStream.println(starts[10]+taxpayer.getReceiptsTotalAmount("Travel")+ends[10]);
		outputStream.println(starts[11]+taxpayer.getReceiptsTotalAmount("Health")+ends[11]);
		outputStream.println(starts[12]+taxpayer.getReceiptsTotalAmount("Other")+ends[12]);
		
		outputStream.close();
	}

	public static FileWriter getInstance() {
		if(instance == null) {
			instance = new FileWriter();
		}
		return instance;
	}

}
