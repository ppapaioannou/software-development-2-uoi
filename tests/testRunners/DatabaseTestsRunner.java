package testRunners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import databaseTests.GetValuesPairListTest;
import databaseTests.ProccessTaxpayerDataFromFileTest;
import databaseTests.TaxpayersArrayListTest;
import databaseTests.TaxpayersInfoFilesPathTest;
import databaseTests.UpdateTaxpayerInputFileTest;


@RunWith(Suite.class)
@SuiteClasses({
	TaxpayersInfoFilesPathTest.class, TaxpayersArrayListTest.class,
	GetValuesPairListTest.class, ProccessTaxpayerDataFromFileTest.class, 
	UpdateTaxpayerInputFileTest.class
})

public class DatabaseTestsRunner {
	
}
