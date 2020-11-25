package co.edu.uniandes.miso4208.omninotes.test.common.commands;

import co.edu.uniandes.miso4208.omninotes.test.common.AndroidDriverManager;

public class MainActivityCommands {

    private static final String PLUS_BUTTON_ID = "it.feio.android.omninotes:id/fab_expand_menu_button";
    private static final String TEXT_NOTE_OPTION_ID = "it.feio.android.omninotes:id/fab_note";
    private AndroidDriverManager driverManager;

    public MainActivityCommands(AndroidDriverManager driverManager) {
        this.driverManager = driverManager;
    }

    public void goToCreateTextNoteForm() {
        driverManager.waitAndClick(PLUS_BUTTON_ID);
        driverManager.waitAndClick(TEXT_NOTE_OPTION_ID);
        driverManager.waitVisibility("it.feio.android.omninotes:id/detail_tile_card");
    }

}
