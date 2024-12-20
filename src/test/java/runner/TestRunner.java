package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/ResetPassword.feature", // Path to the folder containing feature files
        glue = "steps", // Package name where step definitions are located for binding step definitions with steps
        plugin = { "pretty", "html:target/cucumber-reports.html" } // Specifies output formats for test results
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // This class serves as the entry point for running the Cucumber tests
}