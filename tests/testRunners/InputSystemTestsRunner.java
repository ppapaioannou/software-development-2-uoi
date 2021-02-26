package testRunners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import inputSystemTests.InfoFilesParserTest;
import inputSystemTests.TaxpayerDataFromFilesTest;


@RunWith(Suite.class)
@SuiteClasses({
	TaxpayerDataFromFilesTest.class, InfoFilesParserTest.class
})

public class InputSystemTestsRunner {
	
}
