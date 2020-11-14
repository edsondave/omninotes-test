package co.edu.uniandes.miso4208.omninotes.test.bdd;

import co.edu.uniandes.miso4208.omninotes.test.common.AndroidDriverManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(features = "src/main/resources/features")
public class BddRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setUp() {
        AndroidDriverManager.getInstance().skipWelcomeWizard();
    }

    @AfterClass
    public void tearDown() {
        AndroidDriverManager.getInstance().quit();
    }

}
