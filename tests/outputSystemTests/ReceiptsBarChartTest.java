		/*
		 * GIA NA TESTAROUME TIN METHODO
		 * 
		 * createTaxpayerTaxAnalysisBarJFreeChart
		 * 
		 */
package outputSystemTests;

import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import inputManagePackage.InputSystem;
import outputManagePackage.BarChart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class ReceiptsBarChartTest {
	
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
	BarChart barChart = BarChart.getInstance();
    
    @Test
    public void SaveUpdatedTaxpayerInputFileTestTest() {
    	String afmInfoFilesFolderPath = "tests"+File.separator+"testFiles";
    	inputSystem.addTaxpayersIntoDatabase(afmInfoFilesFolderPath,
    															expectedTaxpayersAfmInfoFiles);
    	
    	for(int i=0;i<expectedTaxpayersAfmInfoFiles.size();i++) {
    		Taxpayer taxpayer = database.getTaxpayerFromArrayList(i);
    		
    		String taxVariationType = taxpayer.getTaxInxrease()!=0? "Tax Increase" : "Tax Decrease";
    		double taxVariationAmount = taxpayer.getTaxInxrease()!=0? taxpayer.getTaxInxrease() : taxpayer.getTaxDecrease(); // was taxpayer.getTaxDecrease()*(-1)
    		
    		Double expectedBasicTax = taxpayer.getBasicTax();
    		Double expectedTaxVariationAmount = taxVariationAmount;
    		Double TaxVariationAmount = taxpayer.getTotalTax();
    		
    		barChart.createTaxpayerTaxAnalysisBarJFreeChart(i);
    		DefaultCategoryDataset barValueDataset= barChart.getTaxAnalysisBarChartDataset();
    		
    		Double actualBasicTax = (Double) barValueDataset.getValue("Tax", "Basic Tax");
    		Double actualTaxVariationAmount = (Double) barValueDataset.getValue("Tax", taxVariationType);
    		Double actualTotalTax = (Double) barValueDataset.getValue("Tax", "Total Tax");
    		
    		
    		assertEquals(expectedBasicTax, actualBasicTax);
    		assertEquals(expectedTaxVariationAmount, actualTaxVariationAmount);
    		assertEquals(TaxVariationAmount, actualTotalTax);
    		
    	}
    	
    	database.clearTaxpayersArrayList();

    }
    
}
