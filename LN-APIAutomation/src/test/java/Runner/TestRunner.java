package Runner;
import com.sun.codemodel.JFormatter;
import io.cucumber.junit.Cucumber;
import com.github.mkolisnyk.cucumber.runner.*;
import org.junit.runner.RunWith;
//import io.cucumber.junit.CucumberOptions;
import cucumber.api.CucumberOptions;

import cucumber.api.testng.AbstractTestNGCucumberTests;
@RunWith(Cucumber.class)
/*
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        retryCount = 3,
        detailedAggregatedReport = true,
        detailedReport = true,
        overviewReport = true,
        coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true
)
*/
@CucumberOptions(
        features = {"src/test/java/features/Acquirer"},
        format = {
                "json:target/cucumber.json"
        },
        glue = {"steps.Acquirer"}
        //monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests{

}

