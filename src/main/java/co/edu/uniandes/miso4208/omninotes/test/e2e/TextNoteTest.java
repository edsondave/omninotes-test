package co.edu.uniandes.miso4208.omninotes.test.e2e;

import co.edu.uniandes.miso4208.omninotes.test.e2e.utils.E2eTestUtils;
import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TextNoteTest extends TestSuite {

	@Test()
	public void createTextNotesTest() {

		E2eTestUtils.takeSnapshot(driver, "before-create-text-notes");

		for (int i = 0; i < 5; i++) {

			// Go to create text notes form
			wait.until(ExpectedConditions.elementToBeClickable(By.id("it.feio.android.omninotes:id/fab_expand_menu_button")));
			driver.findElementById("it.feio.android.omninotes:id/fab_expand_menu_button").click();
			driver.findElementById("it.feio.android.omninotes:id/fab_note").click();

			// Fill form
			TextNote textNote = PODAM_FACTORY.manufacturePojo(TextNote.class);
			driver.findElementById("it.feio.android.omninotes:id/detail_title").sendKeys(textNote.getTitle());
			driver.findElementById("it.feio.android.omninotes:id/detail_content").sendKeys(textNote.getContent());
			driver.navigate().back();

			notes.add(textNote);

		}

		E2eTestUtils.takeSnapshot(driver, "after-create-text-notes");
		E2eTestUtils.sortNotes(notes);
		E2eTestUtils.assertAllNotesInMainActivity(driver, notes);
		
	}
	
	@Test(dependsOnMethods = {"createTextNotesTest"})
	public void editTextNoteTest() {

		int idx = 3;

		WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
		TextNote textNote = (TextNote) notes.get(idx);

		card.click();

		WebElement titleTextField = driver.findElementById("it.feio.android.omninotes:id/detail_title");
		WebElement contentTextField = driver.findElementById("it.feio.android.omninotes:id/detail_content");

		assertEquals(titleTextField.getText(), textNote.getTitle());
		assertEquals(contentTextField.getText(), textNote.getContent());

		// Edit form
		TextNote newTextNote = PODAM_FACTORY.manufacturePojo(TextNote.class);
		textNote.setTitle(newTextNote.getTitle());
		textNote.setContent(newTextNote.getContent());

		titleTextField.sendKeys(textNote.getTitle());
		contentTextField.sendKeys(textNote.getContent());
		driver.navigate().back();

		E2eTestUtils.sortNotes(notes);
		E2eTestUtils.assertAllNotesInMainActivity(driver, notes);
		
	}
	
	@Test(dependsOnMethods = {"createTextNotesTest"})
	public void deleteTextNoteTest() {

		int idx = 2;

		WebElement card = driver.findElementsById("it.feio.android.omninotes:id/card_layout").get(idx);
		card.click();
		
		driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"More options\"]").click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout")));
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout").click();

		notes.remove(idx);

		E2eTestUtils.assertAllNotesInMainActivity(driver, notes);
		
	}

}