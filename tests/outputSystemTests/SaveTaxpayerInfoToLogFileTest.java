		/*
		 * GIA NA TESTAROUME TIN METHODO
		 * 
		 * saveTaxpayerInfoToTxtLogFile
		 * 
		 * saveTaxpayerInfoToXmlLogFile
		 * 
		 */
package outputSystemTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import inputManagePackage.InputSystem;
import outputManagePackage.OutputFiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



@RunWith(Parameterized.class)
public class SaveTaxpayerInfoToLogFileTest {
	
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
    
	Database database = Database.getInstance();
	InputSystem inputSystem = InputSystem.getInstance();
	OutputFiles outputFiles = OutputFiles.getInstance();
    
    @Test
    public void saveTaxpayerInfoToTxtLogFile() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	inputSystem.addTaxpayersIntoDatabase(afmInfoFilesFolderPath,
    															expectedTaxpayersAfmInfoFiles);
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		
    		ArrayList<String> expectedFileContents = null;
    		//ArrayList<String> actualFileContents = new ArrayList<String>();

    		String taxpayerFile = expectedTaxpayersAfmInfoFiles.get(i);
    		String filepath = afmInfoFilesFolderPath+File.separator+taxpayerFile;
    		
    		String taxpayersAfm = null;
    		String logFilepath = null;
    		

    		//System.out.println()
    		
    		expectedFileContents = expectedTxtLogContents(filepath,i);

    		//creates file XXXXXXXXX_LOG.txt!!!!!
    		outputFiles.saveTaxpayerInfoToLogFile(afmInfoFilesFolderPath, i,"txt",true);
    			
    		taxpayersAfm = database.getTaxpayerFromArrayList(i).getAFM();
    		logFilepath = afmInfoFilesFolderPath+File.separator+taxpayersAfm+"_LOG.txt";

    		
    		ArrayList<String> actualFileContents = readTaxpayerFile(logFilepath);
    		
    		
    		for(int j = 0; j < expectedFileContents.size(); j++ ) {
    			//System.out.println(expectedFileContents.get(j));
    			//System.out.println(actualFileContents.get(j));
    			assertEquals(expectedFileContents.get(j),actualFileContents.get(j));
    		}
    		
    		
    	}
    	
    	database.clearTaxpayersArrayList();

    }
    
    
    @Test
    public void saveTaxpayerInfoToXmlLogFile() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	inputSystem.addTaxpayersIntoDatabase(afmInfoFilesFolderPath,
    															expectedTaxpayersAfmInfoFiles);
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		
    		ArrayList<String> expectedFileContents = null;
    		//ArrayList<String> actualFileContents = new ArrayList<String>();

    		String taxpayerFile = expectedTaxpayersAfmInfoFiles.get(i);
    		String filepath = afmInfoFilesFolderPath+File.separator+taxpayerFile;
    		
    		String taxpayersAfm = null;
    		String logFilepath = null;
    		


			expectedFileContents = expectedXmlLogContents(filepath,i);
				
			//creates file XXXXXXXXX_LOG.xml!!!!!
			outputFiles.saveTaxpayerInfoToLogFile(afmInfoFilesFolderPath, i,"xml",true); 
				
			taxpayersAfm = database.getTaxpayerFromArrayList(i).getAFM();
    		logFilepath = afmInfoFilesFolderPath+File.separator+taxpayersAfm+"_LOG.xml";
    		
    		ArrayList<String> actualFileContents = readTaxpayerFile(logFilepath);
    		
    		
    		for(int j = 0; j < expectedFileContents.size(); j++ ) {
    			//System.out.println(expectedFileContents.get(j));
    			//System.out.println(actualFileContents.get(j));
    			assertEquals(expectedFileContents.get(j),actualFileContents.get(j));
    		}
    		
    		
    	}
    	
    	database.clearTaxpayersArrayList();

    }
    


	private ArrayList<String> expectedTxtLogContents(String filepath, int index) {
    	ArrayList<String> expectedLogFileContents = new ArrayList<String>();
    	
    	Taxpayer taxpayer = database.getTaxpayerFromArrayList(index);
		
		expectedLogFileContents.add("Name: "+taxpayer.getName());
		expectedLogFileContents.add("AFM: "+taxpayer.getAFM());
		expectedLogFileContents.add("Income: "+taxpayer.getIncome());
		expectedLogFileContents.add("Basic Tax: "+taxpayer.getBasicTax());
		if (taxpayer.getTaxInxrease()!=0){
			expectedLogFileContents.add("Tax Increase: "+taxpayer.getTaxInxrease());
		}else{
			expectedLogFileContents.add("Tax Decrease: "+taxpayer.getTaxDecrease());
		}
		expectedLogFileContents.add("Total Tax: "+taxpayer.getTotalTax());
		expectedLogFileContents.add("Receipts: "+taxpayer.getTotalReceiptsAmount());
		expectedLogFileContents.add("Entertainment: "+taxpayer.getReceiptsTotalAmount("Entertainment"));
		expectedLogFileContents.add("Basic: "+taxpayer.getReceiptsTotalAmount("Basic"));
		expectedLogFileContents.add("Travel: "+taxpayer.getReceiptsTotalAmount("Travel"));
		expectedLogFileContents.add("Health: "+taxpayer.getReceiptsTotalAmount("Health"));
		expectedLogFileContents.add("Other: "+taxpayer.getReceiptsTotalAmount("Other"));
		
		return expectedLogFileContents;
		
	}
	
	
    private ArrayList<String> expectedXmlLogContents(String filepath, int index) {
    	ArrayList<String> expectedLogFileContents = new ArrayList<String>();
    	
    	Taxpayer taxpayer = database.getTaxpayerFromArrayList(index);
		
		expectedLogFileContents.add("<Name> "+taxpayer.getName()+" </Name>");
		expectedLogFileContents.add("<AFM> "+taxpayer.getAFM()+" </AFM>");
		expectedLogFileContents.add("<Income> "+taxpayer.getIncome()+" </Income>");
		expectedLogFileContents.add("<BasicTax> "+taxpayer.getBasicTax()+" </BasicTax>");
		if (taxpayer.getTaxInxrease()!=0){
			expectedLogFileContents.add("<TaxIncrease> "+taxpayer.getTaxInxrease()+" </TaxIncrease>");
		}else{
			expectedLogFileContents.add("<TaxDecrease> "+taxpayer.getTaxDecrease()+" </TaxDecrease>");
		}
		expectedLogFileContents.add("<TotalTax> "+taxpayer.getTotalTax()+" </TotalTax>");
		expectedLogFileContents.add("<Receipts> "+taxpayer.getTotalReceiptsAmount()+" </Receipts>");
		expectedLogFileContents.add("<Entertainment> "+taxpayer.getReceiptsTotalAmount("Entertainment")+" </Entertainment>");
		expectedLogFileContents.add("<Basic> "+taxpayer.getReceiptsTotalAmount("Basic")+" </Basic>");
		expectedLogFileContents.add("<Travel> "+taxpayer.getReceiptsTotalAmount("Travel")+" </Travel>");
		expectedLogFileContents.add("<Health> "+taxpayer.getReceiptsTotalAmount("Health")+" </Health>");
		expectedLogFileContents.add("<Other> "+taxpayer.getReceiptsTotalAmount("Other")+" </Other>");
		
		return expectedLogFileContents;
		
	}


	public ArrayList<String> readTaxpayerFile(String filename){
    	ArrayList<String> fileContents = new ArrayList<String>();
    	File taxpayerFile = new File(filename);
    	try {
			Scanner inputStream = new Scanner(taxpayerFile);
			while (inputStream.hasNextLine()) {
    	        String data = inputStream.nextLine();
    	        fileContents.add(data);
			}
    	    inputStream.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
		return fileContents;
    	
    }
    
}
