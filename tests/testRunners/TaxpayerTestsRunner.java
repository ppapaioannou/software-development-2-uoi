package testRunners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import taxpayerTests.CalculationTest;
import taxpayerTests.ReceiptsArrayListTest;


@RunWith(Suite.class)
@SuiteClasses({
	ReceiptsArrayListTest.class,CalculationTest.class
})

public class TaxpayerTestsRunner {
	
}
