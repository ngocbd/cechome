package net.cec.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Member {
	//String id, String avatar
	// facebookid
	@Id
	private String id;
	
	private String avatar;
	@Index
	private String name;

	public Member(String id) {
		this.setId(id);

	}
	public Member() {
		

	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}