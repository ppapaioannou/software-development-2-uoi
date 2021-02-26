package dataManagePackage;
import dataManagePackage.Receipt.Receipt;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Taxpayer {
	private String name;
	private String afm;
	private FamilyStatus familyStatus;
	private double income;
	private double basicTax;
	private double taxIncrease;
	private double taxDecrease;
	private double totalTax;
	private ArrayList<Receipt> receipts;
	
	public Taxpayer(String name, String afm, String familyStatus, String income){
		this.name = name;
		this.afm = afm;
		this.familyStatus = FamilyStatus.getFamilyStatus(familyStatus);
		this.income = Double.parseDouble(income);
		setBasicTaxBasedOnFamilyStatus();
		taxIncrease = 0;
		taxDecrease = 0;
		
		receipts = new ArrayList<Receipt>();
	}
	
	private void setBasicTaxBasedOnFamilyStatus(){
		int incomeLimits[] = familyStatus.getIncomeLimits();
		double incomeTaxRates[] = familyStatus.getIncomeTaxRates();
		
		basicTax = calculateTax(incomeLimits, incomeTaxRates);
		
		totalTax = basicTax;
	}
	
	private double calculateTax(int[] incomeLimits, double[] incomeTaxRates) {
		double tax;
		
		double flatTaxIncreases[] = calculateFlatTaxIncreases(incomeLimits, incomeTaxRates);
		
		if ( income < incomeLimits[0] ) { tax = incomeTaxRates[0] * income; }
		else if ( income < incomeLimits[1] ) { tax = flatTaxIncreases[0] + incomeTaxRates[1]*(income - incomeLimits[0]); }
		else if ( income < incomeLimits[2] ) { tax = flatTaxIncreases[1] + incomeTaxRates[2]*(income - incomeLimits[1]); }
		else if ( income < incomeLimits[3] ) { tax = flatTaxIncreases[2] + incomeTaxRates[3]*(income - incomeLimits[2]); }
		else { tax = flatTaxIncreases[3] + incomeTaxRates[4]*(income - incomeLimits[3]); }
		
		return tax;
	}
	
	private double[] calculateFlatTaxIncreases(int[] incomeLimits, double[] incomeTaxRates) {
		double flatTaxIncreases[] = new double[] {0.0, 0.0, 0.0, 0.0};
		
		flatTaxIncreases[0] = incomeTaxRates[0] * incomeLimits[0];
		flatTaxIncreases[1] = flatTaxIncreases[0] + incomeTaxRates[1]*(incomeLimits[1] - incomeLimits[0]);
		flatTaxIncreases[2] = flatTaxIncreases[1] + incomeTaxRates[2]*(incomeLimits[2] - incomeLimits[1]);
		flatTaxIncreases[3] = flatTaxIncreases[2] + incomeTaxRates[3]*(incomeLimits[3] - incomeLimits[2]);	
		
		return flatTaxIncreases;
	}
	
	public String toString(){
		return "Name: "+name
				+"\nAFM: "+afm
				+"\nStatus: "+getFamilyStatus()
				+"\nIncome: "+String.format("%.2f", income)
				+"\nBasicTax: "+String.format("%.2f", basicTax)
				+"\nTaxIncrease: "+String.format("%.2f", taxIncrease)
				+"\nTaxDecrease: "+String.format("%.2f", taxDecrease);
	}
	
	public Receipt getReceipt(int receiptID){
		return receipts.get(receiptID);
	}
	
	public ArrayList<Receipt> getReceiptsArrayList(){
		return receipts;
	}
	
	public String[] getReceiptsList(){
		String[] receiptsList = new String[receipts.size()];
		
		int c = 0;
		for (Receipt receipt : receipts){
			receiptsList[c++] = receipt.getId() + " | " + receipt.getDate() + " | " + receipt.getAmount();
		}
		
		return receiptsList;
	}
	
	public double getReceiptsTotalAmount(String kind){
		double receiptsTotalAmount = 0;
		
		for (Receipt receipt : receipts){
			if (receipt.getKind().equals(kind)){
				receiptsTotalAmount += receipt.getAmount();
			}
		}
		
		return (new BigDecimal(receiptsTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public double getTotalReceiptsAmount(){
		double totalReceiptsAmount = 0;
		
		for (Receipt receipt : receipts){
			totalReceiptsAmount += receipt.getAmount();
		}
		
		return (new BigDecimal(totalReceiptsAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public String getName(){
		return name;
	}
	
	public String getAFM(){
		return afm;
	}
	
	public String getFamilyStatus(){
		return familyStatus.getFamilyStatus();
	}
	
	public double getIncome(){
		return (new BigDecimal(income).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public double getBasicTax(){
		return (new BigDecimal(basicTax).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public double getTaxInxrease(){
		return (new BigDecimal(taxIncrease).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public double getTaxDecrease(){
		return (new BigDecimal(taxDecrease).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public double getTotalTax(){
		return (new BigDecimal(totalTax).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
	public void addReceiptToList(Receipt receipt){
		receipts.add(receipt);
		
		calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts();
	}
	
	public void removeReceiptFromList(int index){
		receipts.remove(index);
		
		calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts();
	}
	
	public void calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts(){
		double totalReceiptsAmount = getTotalReceiptsAmount();
		
		taxIncrease = 0;
		taxDecrease = 0;
		
		double receiptsToIncomeRatio = totalReceiptsAmount / (double)income;
		
		if (receiptsToIncomeRatio < 0.2){ taxIncrease = basicTax * 0.08; }
		else if (receiptsToIncomeRatio < 0.4){ taxIncrease = basicTax * 0.04; }
		else if (receiptsToIncomeRatio < 0.6){ taxDecrease = basicTax * 0.15; }
		else{ taxDecrease = basicTax * 0.30; }
		
		totalTax = basicTax + taxIncrease - taxDecrease;
	}
}