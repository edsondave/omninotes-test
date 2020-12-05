package co.edu.uniandes.miso4208.omninotes.test.e2e;

import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import co.edu.uniandes.miso4208.omninotes.test.e2e.utils.E2eTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PhotoTest extends TestSuite {

    @Test(testName = "Create photo notes")
    public void createPhotoTest() {

        for (int i = 0; i < 5; i++) {

            // Go to create text notes form
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_expand_menu_button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("it.feio.android.omninotes:id/fab_camera"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("com.android.camera2:id/shutter_button")));

            if (i == 0) {
                E2eTestUtils.takeSnapshot(driver, "creating-taking-photo");
            }

            // Fill form
            TextNote textNote = PODAM_FACTORY.manufacturePojo(TextNote.class);

            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/shutter_button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.camera2:id/done_button"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("it.feio.android.omninotes:id/detail_title")));

            driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(textNote.getTitle());
            driver.findElementById("it.feio.android.omninotes:id/detail_content").sendKeys(textNote.getContent());
            if (i == 0) {
                E2eTestUtils.takeSnapshot(driver, "after-taking-photo");
            }
            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

            notes.add(textNote);

        }

        E2eTestUtils.takeSnapshot(driver, "after-create-photo-notes");
        E2eTestUtils.sortNotes(notes);
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);

    }

    @Test(testName = "Edit photo notes", dependsOnMethods = {"createPhotoTest"})
    public void editPhotoNoteTest() {

        int idx = 2;

        WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
        TextNote textNote = (TextNote) notes.get(idx);

        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/detail_title")));

        WebElement titleTextField = driver.findElementById("it.feio.android.omninotes:id/detail_title");
        WebElement contentTextField = driver.findElementById("it.feio.android.omninotes:id/detail_content");

        assertEquals(titleTextField.getText(), textNote.getTitle());
        assertEquals(contentTextField.getText(), textNote.getContent());

        E2eTestUtils.takeSnapshot(driver, "before-editing-photo-notes");

        // Edit form
        TextNote newTextNote = PODAM_FACTORY.manufacturePojo(TextNote.class);
        textNote.setTitle(newTextNote.getTitle());
        textNote.setContent(newTextNote.getContent());

        titleTextField.sendKeys(textNote.getTitle());
        contentTextField.sendKeys(textNote.getContent());

        E2eTestUtils.takeSnapshot(driver, "after-editing-photo-notes");

        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

        E2eTestUtils.sortNotes(notes);
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);

    }

    @Test(testName = "Delete photo notes", dependsOnMethods = {"createPhotoTest"})
    public void deletePhotoNoteTest() {

        int idx = 2;

        E2eTestUtils.takeSnapshot(driver, "before-deleting-photo-notes");

        WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]")));

        driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"More options\"]").click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout")));
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout").click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));

        notes.remove(idx);

        E2eTestUtils.takeSnapshot(driver, "after-deleting-photo-notes");
        E2eTestUtils.assertAllNotesInMainActivity(driver, notes);

    }

}
