package co.edu.uniandes.miso4208.omninotes.test.e2e;

import co.edu.uniandes.miso4208.omninotes.model.Checklist;
import co.edu.uniandes.miso4208.omninotes.test.e2e.utils.E2eTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class ChecklistTest extends TestSuite {

    @Test
    public void createCheckList() {

        E2eTestUtils.takeSnapshot(driver, "before-create-check-lists");

        for (int i = 0; i < 5; i++) {

            // Go to create checklist form
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_expand_menu_button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_checklist"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("it.feio.android.omninotes:id/detail_title")));

            Checklist checkList = PODAM_FACTORY.manufacturePojo(Checklist.class);
            driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(checkList.getTitle());

            List <String> tasks = checkList.getTasks();
            for (int j = 0; j < tasks.size(); j++) {
                WebElement taskTextField = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[" + (j + 1) + "]/android.widget.LinearLayout/android.widget.EditText");
                taskTextField.clear();
                taskTextField.sendKeys(tasks.get(j));
            }
            notes.add(checkList);

            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

        }

        E2eTestUtils.takeSnapshot(driver, "after-create-check-lists");
        E2eTestUtils.sortNotes(notes);
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);


    }

    @Test(testName = "Edit checklist", dependsOnMethods = {"createCheckList"})
    public void editChecklistTest() {

        int idx = 2;

        WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
        Checklist checklist = (Checklist) notes.get(idx);

        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/detail_title")));

        WebElement titleTextField = driver.findElementById("it.feio.android.omninotes:id/detail_title");

        assertEquals(titleTextField.getText(), checklist.getTitle());

        E2eTestUtils.takeSnapshot(driver, "before-editing-ckecklist");

        // Edit form
        Checklist newCheckList = PODAM_FACTORY.manufacturePojo(Checklist.class);
        checklist.setTitle(newCheckList.getTitle());
        driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(checklist.getTitle());

        List <String> tasks = checklist.getTasks();
        List <String> newTasks = newCheckList.getTasks();
        for (int i = 0; i < Math.min(tasks.size(), newTasks.size()); i++) {
            tasks.set(i, newTasks.get(i));
            // driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[" + (i + 1) + "]/android.widget.LinearLayout/android.widget.EditText").sendKeys(tasks.get(i));
        }
        assertEquals(tasks, ((Checklist) notes.get(idx)).getTasks());

        E2eTestUtils.takeSnapshot(driver, "after-editing-checklist");

        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

        E2eTestUtils.sortNotes(notes);
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);

    }

    @Test(testName = "Delete checklist", dependsOnMethods = {"createCheckList"})
    public void deleteChecklistTest() {

        int idx = 2;

        E2eTestUtils.takeSnapshot(driver, "before-deleting-checklist");

        WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]")));

        driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"More options\"]").click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout")));
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout").click();

        notes.remove(idx);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

        E2eTestUtils.takeSnapshot(driver, "after-deleting-checklist");
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);

    }

}
