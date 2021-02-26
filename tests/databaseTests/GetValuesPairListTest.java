/*
		 * GIA NA TESTAROUME TIS METHODOUS POU KANOUN GET KAI VALUEPAIRLIST 
		 * 
		 * getTaxpayerNameAfmValuesPairList
		 * 
		 * getTaxpayersNameAfmValuesPairList
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class GetValuesPairListTest {
	
  // fields used together with @Parameter must be public
	@Parameter(0)
	public ArrayList<Taxpayer> expectedTaxpayersArrayList;

	
	public static Taxpayer taxpayer0 = new Taxpayer("Panagiotis Papaioannou", "130456069", 
			                                        "Married Filing Jointly", "9922");
    
    public static Taxpayer taxpayer1 = new Taxpayer("Paraschos Ferrou Graven", "afm2", 
    		                                        "Head of Household", "83829");
    
    public static Taxpayer taxpayer2 = new Taxpayer("name2 surname2", "afm2", 
     		                                        "Single", "2211");

    public static Taxpayer taxpayer3 = new Taxpayer("name3"+" surname3", "afm3", 
    		                                        "Single", "9393939");
    
    public static Taxpayer taxpayer4 = new Taxpayer("name4", "afm3", 
    		                                        "Married Filing Separately", "130456069");
    
    public static Taxpayer taxpayer5 = new Taxpayer("kati", "kapoioAfm1", "Single", "0000");
	
    
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
    public void getTaxpayerNameAfmValuesPairListTest() {
    	for (Taxpayer taxpayer : expectedTaxpayersArrayList){
    		database.addTaxpayerToList(taxpayer);
    	}
    	
    	
    	for (int i=0;i<expectedTaxpayersArrayList.size();i++){
    		Taxpayer expectedTaxpayer = expectedTaxpayersArrayList.get(i);
    		String expectedTaxpayerNameAfmPair = expectedTaxpayer.getName() + " | " 
    		                                                    + expectedTaxpayer.getAFM();
    		
    		String actualTaxpayerNameAfmPair = database.getTaxpayerNameAfmValuesPairList(i);
    		
    		assertEquals(expectedTaxpayerNameAfmPair,actualTaxpayerNameAfmPair);

    	}
    	database.clearTaxpayersArrayList();
    }
    
    
    @Test
    public void getTaxpayersNameAfmValuesPairListTest() {
    	for (Taxpayer taxpayer : expectedTaxpayersArrayList){
    		database.addTaxpayerToList(taxpayer);
    	}    	
    	String[] expectedPairs = initExpectedPairs();
    	
    	String[] actualPairs = database.getTaxpayersNameAfmValuesPairList();
    	
    	for (int i=0;i<expectedTaxpayersArrayList.size();i++){
    		assertEquals(expectedPairs[i],actualPairs[i]);

    	}
    	database.clearTaxpayersArrayList();
    }


	private String[] initExpectedPairs() {
		String[] expectedPairs = new String[expectedTaxpayersArrayList.size()];
		
		for (int i=0;i<expectedPairs.length;i++){
    		Taxpayer expectedTaxpayer = expectedTaxpayersArrayList.get(i);
    		String expectedTaxpayerNameAfmPair = expectedTaxpayer.getName() + " | " 
    		                                                    + expectedTaxpayer.getAFM();
    		expectedPairs[i] = expectedTaxpayerNameAfmPair;

    	}
		
		return expectedPairs;
	}
    
}
