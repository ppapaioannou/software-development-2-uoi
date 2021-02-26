package testRunners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	DatabaseTestsRunner.class, TaxpayerTestsRunner.class,
	InputSystemTestsRunner.class, OutputSystemTestsRunner.class
})

public class MainTestsRunner {
	
}