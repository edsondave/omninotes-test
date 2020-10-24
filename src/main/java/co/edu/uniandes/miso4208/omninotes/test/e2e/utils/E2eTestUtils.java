package co.edu.uniandes.miso4208.omninotes.test.e2e.utils;

import co.edu.uniandes.miso4208.omninotes.test.TestConfiguration;
import co.edu.uniandes.miso4208.omninotes.model.Note;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class E2eTestUtils {

    private E2eTestUtils() {
    }

    public static void sortNotes(List <Note> notes) {

        notes.sort(new Comparator <Note>() {

            @Override
            public int compare(Note currentNote, Note otherNote) {
                return currentNote.getTitle().compareToIgnoreCase(otherNote.getTitle());
            }

        });

    }

    public static void assertAllNotesInMainActivity(AndroidDriver<WebElement> driver, List <Note> notes) {
        List<WebElement> notesList = driver.findElementsById("it.feio.android.omninotes:id/card_layout");

        assertEquals(notesList.size(), notes.size());

        for (int i = 0; i < notes.size(); i++) {

            WebElement card = notesList.get(i);
            Note Note = notes.get(i);

            assertEquals(card.findElement(By.id("it.feio.android.omninotes:id/note_title")).getText(),
                    Note.getTitle());
            assertEquals(card.findElement(By.id("it.feio.android.omninotes:id/note_content")).getText(),
                    Note.getContent());

        }
    }

    public static void takeSnapshot(AndroidDriver<WebElement> driver, String filename) {
        File outputDirectory = new File(TestConfiguration.getInstance().getOutputDir(), "screenshots/");
        if (outputDirectory != null) {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            outputDirectory.mkdirs();
            File destinationFile = new File(outputDirectory, filename + ".png");
            screenshotFile.renameTo(destinationFile);
        }
    }

}
