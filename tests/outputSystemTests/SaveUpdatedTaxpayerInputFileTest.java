		/*
		 * GIA NA TESTAROUME TIN METHODO
		 * 
		 * saveUpdatedTaxpayerTxtInputFile
		 * 
		 * saveUpdatedTaxpayerXmlInputFile
		 * 
		 */
package outputSystemTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
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
public class SaveUpdatedTaxpayerInputFileTest {
	
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
    public void SaveUpdatedTaxpayerInputFileTestTest() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	inputSystem.addTaxpayersIntoDatabase(afmInfoFilesFolderPath,
    															expectedTaxpayersAfmInfoFiles);
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		String taxpayerFile = expectedTaxpayersAfmInfoFiles.get(i);
    		String filepath = afmInfoFilesFolderPath+File.separator+taxpayerFile;

    		ArrayList<String> expectedFileContents = readTaxpayerFile(filepath);
    		
    		outputFiles.saveUpdatedTaxpayerInputFile(filepath, i);
			
    		ArrayList<String> actualFileContents = readTaxpayerFile(filepath);
    		
    		for(int j = 0; j < expectedFileContents.size(); j++ ) {
    			assertEquals(expectedFileContents.get(j),actualFileContents.get(j));
    		}
    		
    		
    	}
    	
    	database.clearTaxpayersArrayList();

    }
    
    private ArrayList<String> readTaxpayerFile(String filename){
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
