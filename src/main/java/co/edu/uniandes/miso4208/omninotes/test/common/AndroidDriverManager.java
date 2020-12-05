package co.edu.uniandes.miso4208.omninotes.test.common;

import co.edu.uniandes.miso4208.omninotes.model.Checklist;
import co.edu.uniandes.miso4208.omninotes.model.Note;
import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import co.edu.uniandes.miso4208.omninotes.test.TestConfiguration;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class AndroidDriverManager {

    private static final String DEVICE_NAME = "Android Emulator";
    private static final String AUTOMATION_NAME = "UiAutomator2";
    private static final String APP_WAIT_ACTIVITY = "it.feio.android.omninotes.intro.IntroActivity";

    private static AndroidDriverManager instance;

    private AndroidDriver<WebElement> driver;
    private WebDriverWait wait;

    private AndroidDriverManager() {
    }

    public static AndroidDriverManager getInstance() {
        if (instance == null) {
            instance = new AndroidDriverManager();
            instance.setup();
        }
        return instance;
    }

    private void setup() {

        TestConfiguration config = TestConfiguration.getInstance();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", DEVICE_NAME);
        capabilities.setCapability("automationName", AUTOMATION_NAME);
        capabilities.setCapability("app", config.getAppUnderTestPath());
        capabilities.setCapability("appWaitActivity", APP_WAIT_ACTIVITY);

        driver = new AndroidDriver <>(config.getAppiumServerUrl(), capabilities);
        wait = new WebDriverWait(driver, 5);

    }

    public void quit() {
        driver.quit();
    }

    public void skipWelcomeWizard() {

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

    public void goToCreateTextNoteForm() {
        // Go to create text notes form
        goBackToMainActivity();
        clickPlusButton();
        driver.findElementById("it.feio.android.omninotes:id/fab_note").click();
    }

    public void clickPlusButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));
        driver.findElementById("it.feio.android.omninotes:id/fab_expand_menu_button").click();
    }

    public void fillCreateTextNoteForm(TextNote textNote) {
        driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(textNote.getTitle());
        driver.findElementById("it.feio.android.omninotes:id/detail_content").sendKeys(textNote.getContent());
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void attachPicture() {
        waitAndClick("it.feio.android.omninotes:id/menu_attachment");
        waitAndClick("it.feio.android.omninotes:id/camera");

        boolean tryAgain = false;
        do {

            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/shutter_button")))
                        .click();
            } catch (TimeoutException ex) {
                driver.pressKey(new KeyEvent(AndroidKey.CAMERA));
            }

            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/done_button"))).click();
            } catch (TimeoutException ex) {
                // If it had not tried again, try again
                tryAgain = !tryAgain;
            }

        } while(tryAgain);

    }

    public boolean isPictureDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("it.feio.android.omninotes:id/menu_attachment")));
        return driver.findElementById("it.feio.android.omninotes:id/gridview_item_picture").isDisplayed();
    }

    private void waitAndClick(String elementId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementId))).click();
    }

    public void goBackToMainActivity() {
        WebElement toolbar = driver.findElementById("it.feio.android.omninotes:id/toolbar");
        WebElement textView = toolbar.findElement(By.className("android.widget.TextView"));
        if (textView != null) {
            if (!"Notes".equals(textView.getText())) {
                goBack();
            }
        } else {
            goBack();
        }
    }

    public void goToCreateChecklistForm() {
        goBackToMainActivity();
        clickPlusButton();
        waitAndClick("it.feio.android.omninotes:id/fab_checklist");
    }

    public void fillCreateChecklistForm(Checklist checklist) {
        driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(checklist.getTitle());

        List <String> tasks = checklist.getTasks();
        for (int j = 0; j < tasks.size(); j++) {
            WebElement taskTextField = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[" + (j + 1) + "]/android.widget.LinearLayout/android.widget.EditText");
            taskTextField.clear();
            taskTextField.sendKeys(tasks.get(j));
        }
    }

    public boolean verifyNoteExits(Note note) {

        List<WebElement> notesList = driver.findElementsById("it.feio.android.omninotes:id/card_layout");

        for (int i = 0; i < notesList.size(); i++) {

            WebElement card = notesList.get(i);

            if (note.getTitle().equals(card.findElement(By.id("it.feio.android.omninotes:id/note_title")).getText())) {
                return true;
            }

        }

        return false;

    }
}

