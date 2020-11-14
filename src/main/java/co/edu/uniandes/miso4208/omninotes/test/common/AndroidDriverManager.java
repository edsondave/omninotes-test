package co.edu.uniandes.miso4208.omninotes.test.common;

import co.edu.uniandes.miso4208.omninotes.model.Checklist;
import co.edu.uniandes.miso4208.omninotes.model.Note;
import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import co.edu.uniandes.miso4208.omninotes.test.TestConfiguration;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/shutter_button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/done_button"))).click();
    }

    public boolean isPictureDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("it.feio.android.omninotes:id/menu_attachment")));
        return driver.findElementById("it.feio.android.omninotes:id/gridview_item_picture").isDisplayed();
    }

    private void waitAndClick(String elementId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementId))).click();
    }

    public boolean isEqualToMainMenu(List<Note> notes) {
        List<WebElement> notesList = driver.findElementsById("it.feio.android.omninotes:id/card_layout");

        if (notesList.size() != notes.size()) {
            return false;
        }

        for (int i = 0; i < notes.size(); i++) {

            WebElement card = notesList.get(i);
            Note note = notes.get(i);

            if(!note.getTitle().equals(
                    card.findElement(By.id("it.feio.android.omninotes:id/note_title")).getText())) {
                return false;
            }

            if(!note.getContent().equals(
                    card.findElement(By.id("it.feio.android.omninotes:id/note_content")).getText())) {
                return false;
            }

        }
        return true;
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
}

