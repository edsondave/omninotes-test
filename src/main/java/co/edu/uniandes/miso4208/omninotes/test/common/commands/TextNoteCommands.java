package co.edu.uniandes.miso4208.omninotes.test.common.commands;

import co.edu.uniandes.miso4208.omninotes.model.TextNote;
import co.edu.uniandes.miso4208.omninotes.test.common.AndroidDriverManager;
import co.edu.uniandes.miso4208.omninotes.test.e2e.utils.E2eTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TextNoteCommands {

    private AndroidDriverManager driverManager;
    private static final String NOTE_TITLE_FIELD_ID = "it.feio.android.omninotes:id/detail_title";
    private static final String NOTE_CONTENT_FIELD_ID = "it.feio.android.omninotes:id/detail_content";
    protected final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    public TextNoteCommands(AndroidDriverManager driverManager) {
        this.driverManager = driverManager;
    }

    public void fillTextNote() {

        TextNote textNote = PODAM_FACTORY.manufacturePojo(TextNote.class);

        driverManager.sendKeys(NOTE_TITLE_FIELD_ID, textNote.getTitle());
        driverManager.sendKeys(NOTE_CONTENT_FIELD_ID, textNote.getContent());

    }

}
