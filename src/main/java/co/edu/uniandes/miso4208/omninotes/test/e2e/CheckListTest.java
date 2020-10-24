package co.edu.uniandes.miso4208.omninotes.test.e2e;

import co.edu.uniandes.miso4208.omninotes.model.CheckList;
import co.edu.uniandes.miso4208.omninotes.test.e2e.utils.E2eTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckListTest extends TestSuite {

    @Test
    public void createCheckList() {

        E2eTestUtils.takeSnapshot(driver, "before-create-check-lists");

        for (int i = 0; i < 5; i++) {

            // Go to create checklist form
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_expand_menu_button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_checklist"))).click();

            CheckList checkList = PODAM_FACTORY.manufacturePojo(CheckList.class);
            driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(checkList.getTitle());

            List <String> tasks = checkList.getTasks();
            for (int j = 0; j < tasks.size(); j++) {
                WebElement taskTextField = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[" + (j + 1) + "]/android.widget.LinearLayout/android.widget.EditText");
                taskTextField.clear();
                taskTextField.sendKeys(tasks.get(j));
            }
            notes.add(checkList);

            driver.navigate().back();

        }

        E2eTestUtils.takeSnapshot(driver, "after-create-check-lists");
        E2eTestUtils.sortNotes(notes);
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);


    }

    // @Test(dependsOnMethods = {"createCheckList"})
    public void editCheckListText() {

        driver.findElementById("it.feio.android.omninotes:id/note_content").click();

        CheckList checkList = PODAM_FACTORY.manufacturePojo(CheckList.class);
        driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(checkList.getTitle());

        List <String> tasks = checkList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[" + (i + 1) + "]/android.widget.LinearLayout/android.widget.EditText").sendKeys(tasks.get(i));
        }

        driver.navigate().back();

        Assert.assertEquals(driver.findElementById("it.feio.android.omninotes:id/note_title").getText(), checkList.getTitle());

    }

    // @Test(dependsOnMethods = {"editCheckList"})
    public void deleteCheckListTest() throws InterruptedException {

        driver.findElementById("it.feio.android.omninotes:id/note_content").click();

        driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"More options\"]").click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout")));
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout").click();

    }

}
