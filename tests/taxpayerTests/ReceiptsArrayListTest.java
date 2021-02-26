/*
		 * GIA NA TESTAROUME TIS METHODOUS
		 * 
		 * getReceipt
		 * 
		 * getReceiptsArrayList
		 * 
		 * getReceiptsList
		 * 
		 * addReceiptToList
		 * 
		 * removeReceiptFromList
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
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class ReceiptsArrayListTest {
	
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
    public void addAndGetReceiptFromListTest() {
    	Taxpayer taxpayer = new Taxpayer("Panagiotis Papaioannou", "123456789", 
    										"married filing separately", "25000");
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		taxpayer.addReceiptToList(expectedReceipts.get(i));
    	}
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		Receipt expectedReceipt = expectedReceipts.get(i);
    		Receipt actualReceipt = taxpayer.getReceipt(i);
    		assertEquals(expectedReceipt,actualReceipt);
    	}
    }
    
    
    @Test
    public void removeReceiptFromListTest() {
    	Taxpayer taxpayer = new Taxpayer("Panagiotis Papaioannou", "123456789", 
											"married filing separately", "25000");
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		taxpayer.addReceiptToList(expectedReceipts.get(i));
    	}
    	
    	for (int i=expectedReceipts.size()-1;i>=0;i--){
    		Receipt toBeRemovedReceipt = taxpayer.getReceipt(i);
    		taxpayer.removeReceiptFromList(i);
    		
    		for (int j=0;j<taxpayer.getReceiptsArrayList().size();j++){
        		Receipt remainingReceipt = taxpayer.getReceipt(j);
        		
        		assertNotEquals(toBeRemovedReceipt,remainingReceipt);
        	}
    	}
    }
    
    @Test
    public void getReceiptsArrayListTest() {
    	Taxpayer taxpayer = new Taxpayer("Panagiotis Papaioannou", "123456789", 
											"married filing separately", "25000");
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		taxpayer.addReceiptToList(expectedReceipts.get(i));
    	}
    	
    	ArrayList<Receipt> actualReceiptsArrayList = taxpayer.getReceiptsArrayList();
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		Receipt expectedReceipt = expectedReceipts.get(i);
    		Receipt actualReceipt = actualReceiptsArrayList.get(i);
    		assertEquals(expectedReceipt,actualReceipt);
    	}
    	
    }
    
    
    @Test
    public void getReceiptsList() {
    	Taxpayer taxpayer = new Taxpayer("Panagiotis Papaioannou", "123456789", 
											"married filing separately", "25000");
    	
    	for(int i=0;i<expectedReceipts.size();i++) {
    		taxpayer.addReceiptToList(expectedReceipts.get(i));
    	}
    	
    	String[] expectedReceiptsList = new String[expectedReceipts.size()];
    	
    	int c = 0;
    	for(Receipt receipt : expectedReceipts) {
    		String expectedReceipt = receipt.getId() + " | " + receipt.getDate() + " | " + receipt.getAmount();
    		expectedReceiptsList[c++] = expectedReceipt;
    	}
    	
    	String[] actualReceiptsList = taxpayer.getReceiptsList();
    	
    	
    	for(int i=0;i<expectedReceiptsList.length;i++) {
    		assertEquals(expectedReceiptsList[i],actualReceiptsList[i]);
    	}
    	
    }
    
}
