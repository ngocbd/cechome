package net.cec.entities;

import com.google.gson.Gson;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import net.cec.models.Attachments;
import net.cec.models.Poster;
import net.cec.utils.Utilities;

@Entity
public class Device { 
//	editorId = accountId
	@Id
	private String id;
	//accountId of the Account
	@Index
	private String accountId;
	//0: iphone, 1: android
	private int deviceType;
	
	private long createdDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
