package net.cec.models;

import net.cec.utils.Utilities;

public class Attachments {

	public Attachments() {
	}

	public Attachments(String description, String title, String url, String type, Media media) {
		this.description = description.replaceAll("\"", "\'");
		this.title = title;
		this.type = type;
		this.url = url;
		this.media = media;
	}

	private String description;

	private String title;

	private String url;

	private String type;
	
	private Media media;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		//replace " to ' because when convert to json, " make the description error.
		this.description = description.replaceAll("\"", "\'");
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
	
	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Utilities.GSON.toJson(this);
	}
}
