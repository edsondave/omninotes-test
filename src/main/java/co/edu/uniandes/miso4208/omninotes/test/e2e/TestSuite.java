package co.edu.uniandes.miso4208.omninotes.test.e2e;

import co.edu.uniandes.miso4208.omninotes.model.Note;
import co.edu.uniandes.miso4208.omninotes.test.TestConfiguration;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class TestSuite {

    private final String DEVICE_NAME = "Android Emulator";
    private final String AUTOMATION_NAME = "UiAutomator2";
    private final String APP_WAIT_ACTIVITY = "it.feio.android.omninotes.intro.IntroActivity";
    protected final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    protected AndroidDriver<WebElement> driver;
    protected WebDriverWait wait;
    protected List <Note> notes = new ArrayList<>();

    @BeforeClass
    public void setUp() throws IOException {

        TestConfiguration config = TestConfiguration.getInstance();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", DEVICE_NAME);
        capabilities.setCapability("automationName", AUTOMATION_NAME);
        capabilities.setCapability("app", config.getAppUnderTestPath());
        capabilities.setCapability("appWaitActivity", APP_WAIT_ACTIVITY);

        driver = new AndroidDriver<>(config.getAppiumServerUrl(), capabilities);
        wait = new WebDriverWait(driver, 5);

        skipWelcomeWizard();

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public URL getServiceUrl () throws MalformedURLException {
        return new URL("http://localhost:4723/wd/hub");
    }

    protected void skipWelcomeWizard() {

        String[] expectedTitle = { "Welcome to Omni Notes!", "Navigate", "Categories", "Improve", "Links", "Enjoy"};

        for (int i = 0; i < 6; i++) {

            wait.until(ExpectedConditions.textToBe(By.id("it.feio.android.omninotes:id/intro_title"), expectedTitle[i]));

            if (i == 5) {
                driver.findElementById("it.feio.android.omninotes:id/done").click();
            } else {
                driver.findElementById("it.feio.android.omninotes:id/next").click();
            }

        }

    }

}