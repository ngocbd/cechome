package net.cec.models;

import java.io.Serializable;

import com.google.gson.Gson;
import com.restfb.types.CategorizedFacebookType;

public class Poster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Poster() {
	}

	public Poster(CategorizedFacebookType from, String picture,
			String full_picture) {
		this.name = from.getName();
		if (picture != null) {
			this.picture = picture;
		} else {
			this.picture = "https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/17265242_1562083670477654_838945771325291209_n.jpg?oh=249157dece1e8bdfafe8653564e06630&oe=5966F9F4";
		}
		this.full_picture = full_picture;
	}

	private String name;

	private String picture;

	private String full_picture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getFull_picture() {
		return full_picture;
	}

	public void setFull_picture(String full_picture) {
		this.full_picture = full_picture;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
