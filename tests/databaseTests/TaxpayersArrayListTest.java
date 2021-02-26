		/*
		 * GIA NA TESTAROUME TIS METHODOUS POU KANOUN PRAGMATA STO ARRAYLIST
		 * 
		 * addTaxpayerToList
		 * 
		 * getTaxpayersArrayListSize
		 * 
		 * getTaxpayerFromArrayList
		 * 
		 * removeTaxpayerFromArrayList
		 * 
		 */
package databaseTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class TaxpayersArrayListTest {
	
  // fields used together with @Parameter must be public
	@Parameter(0)
	public ArrayList<Taxpayer> expectedTaxpayersArrayList;
	
	
	public static Taxpayer taxpayer0 = new Taxpayer("Panagiotis Papaioannou", "130456069", 
			                                        "Married Filing Jointly", "9922");
    
    public static Taxpayer taxpayer1 = new Taxpayer("Paraschos Ferrou Graven", "afm2", 
    		                                        "Head of Household", "83829");
    
    public static Taxpayer taxpayer2 = new Taxpayer("name2 surname2", "afm2", 
    		                                        "single", "2211");

    public static Taxpayer taxpayer3 = new Taxpayer("name3"+"surname3", "afm3", 
    		                                        "Married Filing Separately", "9393939");
    
    public static Taxpayer taxpayer4 = new Taxpayer("name4", "afm3", 
    		                                        "Married Filing Separately", "130456069");
    
    public static Taxpayer taxpayer5 = new Taxpayer("aa", "vv", "single", "999111999");
	
    
  // creates the test data
    @Parameters
    public static List<Object> data() {
    	Object[] data = new Object[] { 
        /*0*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer1, taxpayer2)),
        		
        /*1*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer2)),
        		
        /*2*/	new ArrayList<>(Arrays.asList()),
        
        /*3*/	new ArrayList<>(Arrays.asList(taxpayer1, taxpayer5, taxpayer3, taxpayer2,
        		                              taxpayer4, taxpayer0)),
		
        /*4*/	new ArrayList<>(Arrays.asList(taxpayer5, taxpayer4, taxpayer3)),
        		
        /*5*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer1, taxpayer3)),
        
        /*6*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer3, taxpayer4, taxpayer1)),
		
        /*7*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer5, taxpayer2)),
        		
        /*8*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer5)),
        
        /*9*/	new ArrayList<>(Arrays.asList(taxpayer0, taxpayer1, taxpayer2, taxpayer5,
        		                              taxpayer3))
        		};
        return Arrays.asList(data);
    }
    
    Database database = Database.getInstance();
    
    @Test
    public void addAndGetTaxpayerToListTest() {
    	for (Taxpayer taxpayer : expectedTaxpayersArrayList){
    		database.addTaxpayerToList(taxpayer);
    	}
    	
    	
    	for (int i=0;i<expectedTaxpayersArrayList.size();i++){
    		Taxpayer expectedTaxpayer = expectedTaxpayersArrayList.get(i);
    		Taxpayer actualTaxpayer = database.getTaxpayerFromArrayList(i);
    		
    		assertEquals(expectedTaxpayer,actualTaxpayer);
    	}
    	database.clearTaxpayersArrayList();
    }
    
    
    @Test
    public void getTaxpayersArrayListSizeTest() {
    	
    	for (Taxpayer taxpayer : expectedTaxpayersArrayList){
    		database.addTaxpayerToList(taxpayer);
    	}
    	
    	int expectedTaxpayerArrayListSize = expectedTaxpayersArrayList.size();
		int actualTaxpayerArrayListSize = database.getTaxpayersArrayListSize();
		
		assertEquals(expectedTaxpayerArrayListSize,actualTaxpayerArrayListSize);
		
		database.clearTaxpayersArrayList();
    }
    
    
    @Test
    public void removeTaxpayerFromArrayListTest() {
    	
    	for (Taxpayer taxpayer : expectedTaxpayersArrayList){
    		database.addTaxpayerToList(taxpayer);
    	}
    	
    	for (int i=expectedTaxpayersArrayList.size()-1;i>=0;i--){
    		Taxpayer toBeRemovedTaxpayer = database.getTaxpayerFromArrayList(i);
    		database.removeTaxpayerFromArrayList(i);
    		
    		for (int j=0;j<database.getTaxpayersArrayListSize();j++){
        		Taxpayer remainingTaxpayer = database.getTaxpayerFromArrayList(j);
        		
        		assertNotEquals(toBeRemovedTaxpayer,remainingTaxpayer);
        	}
    	}
    	database.clearTaxpayersArrayList();
    }
    
}
