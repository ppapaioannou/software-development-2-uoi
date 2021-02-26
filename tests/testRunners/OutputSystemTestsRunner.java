package testRunners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import outputSystemTests.ReceiptsBarChartTest;
import outputSystemTests.ReceiptsPieChartTest;
import outputSystemTests.SaveTaxpayerInfoToLogFileTest;
import outputSystemTests.SaveUpdatedTaxpayerInputFileTest;


@RunWith(Suite.class)
@SuiteClasses({
	SaveUpdatedTaxpayerInputFileTest.class, SaveTaxpayerInfoToLogFileTest.class,
	ReceiptsPieChartTest.class, ReceiptsBarChartTest.class
})

public class OutputSystemTestsRunner {
	
}
