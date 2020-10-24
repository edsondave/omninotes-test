package co.edu.uniandes.miso4208.omninotes.model;

import co.edu.uniandes.miso4208.omninotes.model.Note;

public class TextNote implements Note {

	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
