		/*
		 * GIA NA TESTAROUME TIS PRWTOS 2 PUBLIC METHODOUS TOU DATABASE
		 * 
		 * setTaxpayersInfoFilesPath
		 * 
		 * getTaxpayersInfoFilesPath
		 * 
		 */
package databaseTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import dataManagePackage.Database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;



@RunWith(Parameterized.class)
public class TaxpayersInfoFilesPathTest {

    // fields used together with @Parameter must be public
    @Parameter(0)
    public String expectedFilepath;



    // creates the test data
    @Parameters
    public static List<Object> data() {
    	Object[] data = new Object[] { 
        /*0*/	"/SoftwareDevelopementII/tests/testFiles",
        		
        /*1*/	"/home/panagiotis/Documents/Minnesota Income Tax Calculation Project"
        		+ File.separator + "tests" + File.separator + "testFiles",
        		
        /*2*/	"filepath",
        
        /*3*/	"/SoftwareDevelopementII/inputFiles",
		
        /*4*/	"SoftwareDevelopementII/inputFiles",
        		
        /*5*/	"file"+"path",
        
        /*6*/	File.separator + "SoftwareDevelopementII" + File.separator + "tests"
        		+ File.separator + "testFiles",
		
        /*7*/	"file path with spaces",
        		
        /*8*/	"path/thePath/theFileFolder",
        
        /*9*/	" "
        		};
        return Arrays.asList(data);
    }

    Database database = Database.getInstance();

    @Test
    public void setAndGetTaxpayersFilepath() {
		
		database.setTaxpayersInfoFilesPath(expectedFilepath);
		
		String actualFilepath = database.getTaxpayersInfoFilesPath();
		
		assertEquals(expectedFilepath,actualFilepath);
    }

}
