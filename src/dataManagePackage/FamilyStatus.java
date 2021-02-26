package dataManagePackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class FamilyStatus {
	
	private static final String filePath = "propertiesFiles/familyStatusRates.txt";

	private static final FamilyStatus SINGLE = initFinalFamilyStatus(filePath, "Single");
	private static final FamilyStatus HEAD_OF_HOUSEHOLD = initFinalFamilyStatus(filePath, "Head of Household");
	private static final FamilyStatus MARRIED_FILING_JOINTLY = initFinalFamilyStatus(filePath, "Married Filing Jointly");
	private static final FamilyStatus MARRIED_FILING_SEPARATELY = initFinalFamilyStatus(filePath, "Married Filing Separately");
	
	private String familyStatus;
	
	private int[] incomeLimits ;
	private double[] incomeTaxRates ;
	
	
	public FamilyStatus(int[] incomeLimits, double[] incomeTaxRates, String familyStatus) {
		this.incomeLimits = incomeLimits;
		this.incomeTaxRates = incomeTaxRates;
		this.familyStatus = familyStatus;
	}
	
	private static FamilyStatus initFinalFamilyStatus(String filePath, String status) { 
						
		int[]  incomeLimits=new int[4];
		double[]  incomeTaxRates=new double[5];
		
		Scanner inputReader = null;
		
		try {
			inputReader = new Scanner(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(inputReader.hasNextLine()) {
			String line = inputReader.nextLine();
			if( line.contains(status) ) {
				inputReader.nextLine();
				line = inputReader.nextLine();
				if(!line.contains("incomeLimits:")) {
					System.out.println("incomeLimits not specified/not in the "
									 + "right place for status: "+status);
					System.exit(0);
				}
				line = line.replace(",", "");						//Keep everything but the commas
				line = line.substring("incomeLimits: ".length());	//Keep only the numbers
				incomeLimits = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
				
				line = inputReader.nextLine();
				if(!line.contains("incomeTaxRates:")) {
					System.out.println("incomeTaxRates not specified/not in the "
									 + "right place for status: "+status);
					System.exit(0);
				}
				line = line.replace(",", "");						//Keep everything but the commas
				line = line.substring("incomeTaxRates: ".length());	//Keep only the numbers
				incomeTaxRates = Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
				 
				 
				break;
			}
		}
		
		inputReader.close();
		
		return new FamilyStatus(incomeLimits, incomeTaxRates, status);
	}
	
	public String getFamilyStatus() {
		return familyStatus;
	}
	
	public static FamilyStatus getFamilyStatus(String status) {
		
		switch(status.toLowerCase()){
			case("married filing jointly"):
				return MARRIED_FILING_JOINTLY;
			case("single"):
				return SINGLE;
			case("married filing separately"):
				return MARRIED_FILING_SEPARATELY;
			case("head of household"):
				return HEAD_OF_HOUSEHOLD;
		}
		return null;
	}
		
	public int[] getIncomeLimits() {
		return incomeLimits;
	}
	
	public double[] getIncomeTaxRates() {
		return incomeTaxRates;
	}
	
}
