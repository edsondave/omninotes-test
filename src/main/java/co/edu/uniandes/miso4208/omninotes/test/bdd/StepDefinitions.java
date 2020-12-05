package co.edu.uniandes.miso4208.omninotes.test.bdd;

import co.edu.uniandes.miso4208.omninotes.model.Checklist;
import co.edu.uniandes.miso4208.omninotes.model.Note;
import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import co.edu.uniandes.miso4208.omninotes.test.common.AndroidDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {

    private final String DEVICE_NAME = "Android Emulator";
    private final String AUTOMATION_NAME = "UiAutomator2";
    private final String APP_WAIT_ACTIVITY = "it.feio.android.omninotes.intro.IntroActivity";
    protected final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    protected AndroidDriverManager driverManager;
    protected Note note;

    @Before
    public void setup() {
        driverManager = AndroidDriverManager.getInstance();
    }

    @Given("I go to create text note option")
    public void goToCreateTextNoteForm() {
        driverManager.goBackToMainActivity();
        driverManager.goToCreateTextNoteForm();
    }

    @Given("I go to create checklist option")
    public void goToCreateChecklistForm() {
        driverManager.goBackToMainActivity();
        driverManager.goToCreateChecklistForm();
    }

    @When("I fill the text note")
    public void fillCreateTextNoteForm() {

        TextNote textNote = PODAM_FACTORY.manufacturePojo(TextNote.class);
        driverManager.fillCreateTextNoteForm(textNote);
        note = textNote;

        driverManager.goBack();

    }

    @When("I fill the checklist")
    public void fillChecklistNoteForm() {

        Checklist checklist = PODAM_FACTORY.manufacturePojo(Checklist.class);
        driverManager.fillCreateChecklistForm(checklist);
        note = checklist;

        driverManager.goBack();

    }

    @Then("It should be created")
    public void verifyNoteIsCreated() {
        Assert.assertTrue(driverManager.verifyNoteExits(note));
    }

    @When("I attach a photo")
    public void attachPicture() {
        driverManager.attachPicture();
    }

    @Then("The photo should be attached to the note")
    public void verifyPicture() {
        Assert.assertTrue(driverManager.isPictureDisplayed());
    }

    @Then("The photo should be removed.")
    public void verfifyPictureRemoved() {
        Assert.assertFalse(driverManager.isPictureDisplayed());
    }

}