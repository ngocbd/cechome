package net.cec.models;

import net.cec.utils.Utilities;

public class Attachments {

	public Attachments() {
	}

	public Attachments(String description, String title, String url, String type) {
		this.description = description;
		this.title = title;
		this.type = type;
		this.url = url;
	}

	private String description;

	private String title;

	private String url;

	private String type;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Utilities.GSON.toJson(this);
	}
}
