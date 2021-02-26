		/*
		 * GIA NA TESTAROUME TIN METHODO
		 * 
		 * createTaxpayerReceiptsPieJFreeChart
		 * 
		 */
package outputSystemTests;

import org.jfree.data.general.DefaultPieDataset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import inputManagePackage.InputSystem;
import outputManagePackage.PieChart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class ReceiptsPieChartTest {
	
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
	PieChart pieChart = PieChart.getInstance();
    
    @Test
    public void SaveUpdatedTaxpayerInputFileTestTest() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	inputSystem.addTaxpayersIntoDatabase(afmInfoFilesFolderPath,
    															expectedTaxpayersAfmInfoFiles);
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		Taxpayer taxpayer = database.getTaxpayerFromArrayList(i);
    		
    		Double expectedBasic = taxpayer.getReceiptsTotalAmount("Basic");
    		Double expectedEntertainment = taxpayer.getReceiptsTotalAmount("Entertainment");
    		Double expectedTravel = taxpayer.getReceiptsTotalAmount("Travel");
    		Double expectedHealth = taxpayer.getReceiptsTotalAmount("Health");
    		Double expectedOther = taxpayer.getReceiptsTotalAmount("Other");
    		
    		
    		pieChart.createTaxpayerReceiptsPieJFreeChart(i);
    		
    		DefaultPieDataset pieValueDataset = pieChart.getReceiptPieChartDataset();
    		
    		Double actualBasic = (Double) pieValueDataset.getValue("Basic");
    		assertEquals(expectedBasic, actualBasic);
    		
    		Double actualEntertainment = (Double) pieValueDataset.getValue("Entertainment");
    		assertEquals(expectedEntertainment, actualEntertainment);
    		
    		Double actualTravel = (Double) pieValueDataset.getValue("Travel");
    		assertEquals(expectedTravel, actualTravel);
    		
    		Double actualHealth = (Double) pieValueDataset.getValue("Health");
    		assertEquals(expectedHealth, actualHealth);
    		
    		Double actualOther = (Double) pieValueDataset.getValue("Other");
    		assertEquals(expectedOther,actualOther);
    		
    	}
    	
    	database.clearTaxpayersArrayList();

    }
    
}
