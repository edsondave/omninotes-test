package co.edu.uniandes.miso4208.omninotes.model;

import java.util.List;

public class Checklist implements Note {

	private String title;
	private List<String> tasks;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		for (String task: tasks) {
			sb.append("◻ ").append(task);
		}
		return sb.toString();
	}

}
