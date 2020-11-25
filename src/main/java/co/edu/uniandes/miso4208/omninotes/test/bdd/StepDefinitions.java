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

    protected final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    protected AndroidDriverManager driverManager;
    protected List <Note> notes = new ArrayList <>();

    @Before
    public void setup() {
        driverManager = new AndroidDriverManager();
        driverManager.setup();
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
        notes.add(textNote);

        driverManager.goBack();

    }

    @When("I fill the checklist")
    public void fillChecklistNoteForm() {

        Checklist checklist = PODAM_FACTORY.manufacturePojo(Checklist.class);
        driverManager.fillCreateChecklistForm(checklist);
        notes.add(checklist);

        driverManager.goBack();

    }

    @Then("It should be created")
    public void verifyNoteIsCreated() {
        Assert.assertTrue(driverManager.isEqualToMainMenu(notes));
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