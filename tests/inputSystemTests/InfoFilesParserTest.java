		/*
		 * GIA NA TESTAROUME TIN METHODO
		 * 
		 * addTaxpayersDataFromFilesIntoDatabase
		 * 
		 */
package inputSystemTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import dataManagePackage.Receipt.Receipt;
import dataManagePackage.Receipt.ReceiptFactory;
import identifiersManagePackage.IdentifiersManager;
import inputManagePackage.InfoFilesParser;
import inputManagePackage.InputSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



@RunWith(Parameterized.class)
public class InfoFilesParserTest {
	
  // fields used together with @Parameter must be public
	@Parameter(0)
	public List<String> expectedTaxpayersAfmInfoFiles;
	
    
	  // creates the test data
	@Parameters
    public static List<Object> data() {
    	Object[] data = new Object[] { 
        /*0*/	new ArrayList<>(Arrays.asList("111111111_INFO.txt","130456069_INFO.txt",
        		                              "130456094_INFO.xml","130456420_INFO.xml")),
        
        /*1*/	new ArrayList<>(Arrays.asList("131783061_INFO.txt","140981094_INFO.xml",
        		                              "140999999_INFO.xml","921634662_INFO.txt",
        		                              "993456394_INFO.xml","999999999_INFO.txt",
        		                              "111111111_INFO.txt","130456069_INFO.txt",
        		                              "130456094_INFO.xml","130456420_INFO.xml")),
        		
        /*2*/	new ArrayList<>(Arrays.asList()),
        
        /*3*/	new ArrayList<>(Arrays.asList("111111111_INFO.txt","130456069_INFO.txt")),
		
        /*4*/	new ArrayList<>(Arrays.asList("130456094_INFO.xml","130456420_INFO.xml",
        		                              "140981094_INFO.xml","140999999_INFO.xml",
        		                              "993456394_INFO.xml")),
        		
        /*5*/	new ArrayList<>(Arrays.asList("999999999_INFO.txt","921634662_INFO.txt",
        		                              "131783061_INFO.txt","130456069_INFO.txt",
        		                              "111111111_INFO.txt")),
        
        /*6*/	new ArrayList<>(Arrays.asList()),
		
        /*7*/	new ArrayList<>(Arrays.asList("111111111_INFO.txt","130456069_INFO.txt",
        		                              "130456094_INFO.xml")),
        		
        /*8*/	new ArrayList<>(Arrays.asList("993456394_INFO.xml")),
        
        /*9*/	new ArrayList<>(Arrays.asList("921634662_INFO.txt"))
        		};
        return Arrays.asList(data);
    }
	
	String[] fileTags = new String[] {	"Name","AFM","Status","Income",
										"Receipt ID","Date","Kind",
										"Amount","Company","Country",
										"City","Street","Number"};
    
	InputSystem inputSystem = InputSystem.getInstance();
	Database database = Database.getInstance();
	IdentifiersManager infoIdentifiersManager = IdentifiersManager.getInstance();
	InfoFilesParser infoFilesParser = InfoFilesParser.getInstance();
    
    @Test
    public void createTaxpayerTest() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		
    		String taxpayerFile = expectedTaxpayersAfmInfoFiles.get(i);
    		
			String[][] identifiers = infoIdentifiersManager.prepareIdentifiers(taxpayerFile, fileTags);
			Scanner inputStream = initInputStream(afmInfoFilesFolderPath, taxpayerFile);
			
			Taxpayer expectedTaxpayer = taxpayerFromFile(afmInfoFilesFolderPath,taxpayerFile);
			
			Taxpayer actualTaxpayer = infoFilesParser.createTaxpayer(inputStream, identifiers);
			
			inputStream.close();
			
			String expectedName = expectedTaxpayer.getName();
    		String actualName = actualTaxpayer.getName();
			assertEquals(expectedName,actualName);
    		
    		String expectedAfm = expectedTaxpayer.getAFM();
    		String actualAfm = actualTaxpayer.getAFM();
    		assertEquals(expectedAfm,actualAfm);
    		
    		String expectedStatus = expectedTaxpayer.getFamilyStatus();
    		String actualStatus = actualTaxpayer.getFamilyStatus();
    		assertEquals(expectedStatus,actualStatus);
    		
    		Double expectedIncome = expectedTaxpayer.getIncome();
    		Double actualIncome = actualTaxpayer.getIncome();
    		assertEquals(expectedIncome,actualIncome);
    		
    		ArrayList<Receipt> expectedReceiptsList = expectedTaxpayer.getReceiptsArrayList();
    		ArrayList<Receipt> actualReceiptsList = actualTaxpayer.getReceiptsArrayList();
    		for (int j=0; j<expectedReceiptsList.size(); j++) {
    			assertEquals(expectedReceiptsList.get(j).getId(),actualReceiptsList.get(j).getId());
    			assertEquals(expectedReceiptsList.get(j).getDate(),actualReceiptsList.get(j).getDate());
    			assertEquals(expectedReceiptsList.get(j).getKind(),actualReceiptsList.get(j).getKind());
    			assertEquals(expectedReceiptsList.get(j).getAmount(),actualReceiptsList.get(j).getAmount());
    			assertEquals(expectedReceiptsList.get(j).getCompany().getName(),actualReceiptsList.get(j).getCompany().getName());
    			assertEquals(expectedReceiptsList.get(j).getCompany().getCountry(),actualReceiptsList.get(j).getCompany().getCountry());
    			assertEquals(expectedReceiptsList.get(j).getCompany().getCity(),actualReceiptsList.get(j).getCompany().getCity());
    			assertEquals(expectedReceiptsList.get(j).getCompany().getStreet(),actualReceiptsList.get(j).getCompany().getStreet());
    			assertEquals(expectedReceiptsList.get(j).getCompany().getNumber(),actualReceiptsList.get(j).getCompany().getNumber());	
    			
    		}
			
		}
    	
    }

    private Scanner initInputStream(String afmInfoFilesFolderPath, String afmInfoFile) {
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new FileInputStream(afmInfoFilesFolderPath+
															File.separator+afmInfoFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening " + afmInfoFile + " file.");
			System.exit(0);
		}
		return inputStream;	
		
	}

    private Taxpayer taxpayerFromFile(String afmInfoFilesFolderPath, String taxpayerFile){
		Taxpayer expectedTaxpayer = null;
		if (taxpayerFile.endsWith(".txt")){
			expectedTaxpayer = taxpayerFromTxt(afmInfoFilesFolderPath, taxpayerFile);
		}
		else if (taxpayerFile.endsWith(".xml")){
			expectedTaxpayer = taxpayerFromXml(afmInfoFilesFolderPath, taxpayerFile);
		}
		return expectedTaxpayer;
	}
	
	private Taxpayer taxpayerFromTxt(String afmInfoFileFolderPath, String afmInfoFile){
		Scanner inputStream = null;
		try
		{                                                                   /*CHANGED*/
			                                                              /*was '"\\"'*/
			                                                         /*now is File.separator*/
			inputStream = new Scanner(new FileInputStream(afmInfoFileFolderPath+File.separator+afmInfoFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening " + afmInfoFile + " file.");
			System.exit(0);
		}			
		
		String taxpayerName = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Name: ");
		String taxpayerAFM = getParameterValueFromTxtFileLine(inputStream.nextLine(), "AFM: ");
		String taxpayerStatus = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Status: ");
		String taxpayerIncome = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Income: ");
		Taxpayer newTaxpayer = new Taxpayer(taxpayerName, taxpayerAFM, taxpayerStatus, taxpayerIncome);
		
		String fileLine;
		while (inputStream.hasNextLine())
		{
			fileLine = inputStream.nextLine();
			if (fileLine.equals("")) continue;
			if (fileLine.indexOf("Receipts:")!=-1) continue;
			
			String receiptID = getParameterValueFromTxtFileLine(fileLine, "Receipt ID: ");
			String receiptDate = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Date: ");
			String receiptKind = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Kind: ");
			String receiptAmount = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Amount: ");
			String receiptCompany = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Company: ");
			String receiptCountry = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Country: ");
			String receiptCity = getParameterValueFromTxtFileLine(inputStream.nextLine(), "City: ");
			String receiptStreet = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Street: ");
			String receiptNumber = getParameterValueFromTxtFileLine(inputStream.nextLine(), "Number: ");
			Receipt newReceipt = ReceiptFactory.createNewReceipt(receiptKind, receiptID, receiptDate, receiptAmount, receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
			
			newTaxpayer.addReceiptToList(newReceipt);
		}
		
		//Database.addTaxpayerToList(newTaxpayer);
		return newTaxpayer;
	}
	
	private String getParameterValueFromTxtFileLine(String fileLine, String parameterName){
		return fileLine.substring(parameterName.length(), fileLine.length());
	}
	
	private Taxpayer taxpayerFromXml(String afmInfoFileFolderPath, String afmInfoFile){
		Scanner inputStream = null;
		try
		{
														                    /*CHANGED*/
														                  /*was '"\\"'*/
														               /*now is File.separator*/
			inputStream = new Scanner(new FileInputStream(afmInfoFileFolderPath+File.separator+afmInfoFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening " + afmInfoFile + " file.");
			System.exit(0);
		}			
		
		String taxpayerName = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Name> ", " </Name>");
		String taxpayerAFM = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<AFM> ", " </AFM>");
		String taxpayerStatus = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Status> ", " </Status>");
		String taxpayerIncome = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Income> ", " </Income>");
		Taxpayer newTaxpayer = new Taxpayer(taxpayerName, taxpayerAFM, taxpayerStatus, taxpayerIncome);
		
		String fileLine;
		while (inputStream.hasNextLine())
		{
			fileLine = inputStream.nextLine();
			if (fileLine.equals("")) continue;
			if (fileLine.indexOf("<Receipts>")!=-1) continue;
			if (fileLine.indexOf("</Receipts>")!=-1) break;
			
			String receiptID = getParameterValueFromXmlFileLine(fileLine, "<ReceiptID> ", " </ReceiptID>");
			String receiptDate = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Date> ", " </Date>");
			String receiptKind = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Kind> ", " </Kind>");
			String receiptAmount = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Amount> ", " </Amount>");
			String receiptCompany = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Company> ", " </Company>");
			String receiptCountry = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Country> ", " </Country>");
			String receiptCity = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<City> ", " </City>");
			String receiptStreet = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Street> ", " </Street>");
			String receiptNumber = getParameterValueFromXmlFileLine(inputStream.nextLine(), "<Number> ", " </Number>");
			Receipt newReceipt = ReceiptFactory.createNewReceipt(receiptKind, receiptID, receiptDate, receiptAmount, receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
			
			newTaxpayer.addReceiptToList(newReceipt);
		}
		
		//Database.addTaxpayerToList(newTaxpayer);
		return newTaxpayer;
	}
	
	private String getParameterValueFromXmlFileLine(String fileLine, String parameterStartField, String parameterEndField){
		return fileLine.substring(parameterStartField.length(), fileLine.length()-parameterEndField.length());
	}
    
}
