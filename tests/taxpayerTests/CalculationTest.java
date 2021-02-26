/*
		 * GIA NA TESTAROUME TIS METHODOUS
		 * 
		 * calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts
		 * 
		 */
package taxpayerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Taxpayer;
import dataManagePackage.Receipt.Receipt;
import dataManagePackage.Receipt.ReceiptFactory;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class CalculationTest {
	
  // fields used together with @Parameter must be public
	@Parameter(0)
	public List<Receipt> expectedReceipts;
	
	
	static Receipt receipt1 = ReceiptFactory.createNewReceipt(	"Basic", "1", "07-12-2020", 
																"500",
																"Masoutis", "Greece", 
																"Ioannina",
																"Asimakopoulou", "40");

	static Receipt receipt2 = ReceiptFactory.createNewReceipt(	"Other", "2", "07-12-2020", 
																"1000",
																"e-shop Co.", "Greece", "Ioannina",
																"Michail Angelou", "50");

	static Receipt receipt3 = ReceiptFactory.createNewReceipt(	"Health", "3", "07-12-2020", 
																"2000",
																"Dentist", "Greece", "Athens",
																"Ermou", "128");
	
	static Receipt receipt4 = ReceiptFactory.createNewReceipt(	"Travel", "4", "07-12-2020", 
																"200",
																"Aegean Air", "Greece", "Athens",
																"Ermou", "128");
	
	static Receipt receipt5 = ReceiptFactory.createNewReceipt(	"Entertainment", "5", "07-11-2020", 
																"500.0",
																"Floca Cafe", "Greece", "Athens",
																"Ermou", "128");

	
    
  // creates the test data
    @Parameters
    public static List<Object> data() {
    	Object[] data = new Object[] { 
        /*0*/	new ArrayList<>(Arrays.asList(receipt1)),
        
        /*1*/	new ArrayList<>(Arrays.asList(receipt1,receipt2,receipt3)),
        		
        /*2*/	new ArrayList<>(Arrays.asList()),
        
        /*3*/	new ArrayList<>(Arrays.asList(receipt1,receipt2,receipt3,receipt4)),
		
        /*4*/	new ArrayList<>(Arrays.asList(receipt3,receipt1,receipt2,receipt4,receipt5)),
        		
        /*5*/	new ArrayList<>(Arrays.asList(receipt1)),
        
        /*6*/	new ArrayList<>(Arrays.asList()),
		
        /*7*/	new ArrayList<>(Arrays.asList(receipt3)),
        		
        /*8*/	new ArrayList<>(Arrays.asList(receipt5)),
        
        /*9*/	new ArrayList<>(Arrays.asList(receipt5,receipt4))
        		};
        return Arrays.asList(data);
    }
    
    
	@Test
    public void calculateTotalTaxTest() {
    	Taxpayer taxpayer = new Taxpayer("Panagiotis Papaioannou", "123456789", 
    										"married filing separately", "25000");
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		taxpayer.addReceiptToList(expectedReceipts.get(i));
    	}
    	
    	Double income = taxpayer.getIncome();
    	int totalNumberOfReceipts = taxpayer.getReceiptsArrayList().size();
		Double basicTax = taxpayer.getBasicTax();
		
		Double expectedTotalTax = calculateNewTotalTax(income,totalNumberOfReceipts,basicTax);
		
		taxpayer.calculateTaxpayerTaxIncreaseOrDecreaseBasedOnReceipts();
		Double actualTotalTax = taxpayer.getTotalTax();
		
		assertEquals(expectedTotalTax,actualTotalTax);
    }


	@SuppressWarnings("deprecation")
	private Double calculateNewTotalTax(Double income, int totalNumberOfReceipts, Double basicTax) {
		Double taxIncrease = 0.0;
		Double taxDecrease = 0.0;
		Double expectedTotalTax = 0.0;
		
		if ((totalNumberOfReceipts/(double)income) < 0.2){
			taxIncrease = basicTax * 0.08;
		}
		else if ((totalNumberOfReceipts/(double)income) < 0.4){
			taxIncrease = basicTax * 0.04;
		}
		else if ((totalNumberOfReceipts/(double)income) < 0.6){
			taxDecrease = basicTax * 0.15;
		}
		else{
			taxDecrease = basicTax * 0.30;
		}
		expectedTotalTax = new BigDecimal(basicTax + taxIncrease -
				taxDecrease).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return expectedTotalTax;
	}
    
    
    
}
