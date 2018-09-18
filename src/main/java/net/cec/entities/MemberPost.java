package net.cec.entities;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import net.cec.models.Attachments;
import net.cec.models.Poster;
import net.cec.task.handler.GetPostContent;
import net.cec.utils.Utilities;

@Entity
public class MemberPost {

	static Logger log = Logger.getLogger(MemberPost.class.getName()); 
	// String id, String attachments, String type, String content, Long createDate, String featuredImage, Long lastupdate, String permalink, String picture, String poster, String posterId 
	@Id
	private String id;
	
	@Unindex
	private String attachments = null;
	
	@Unindex
	private String type;
	
	@Unindex
	private String content;
	
	@Index
	private Long createdDate;

	@Index
	private Long lastUpdate;
	
	@Unindex
	private String permalink;
	
	@Unindex
	private String picture;

	@Index
	private String posterId;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Attachments getAttachments() {
		if(this.attachments == null || this.attachments.isEmpty()){
			return null;
		}
		try {
			log.warning("Attachments: " + Utilities.GSON.fromJson(this.attachments, Attachments.class));
			return Utilities.GSON.fromJson(this.attachments, Attachments.class);
		} catch (Exception e) {
			// TODO: handle exception
			log.warning("Error: "+this.attachments);
			return null;
		}
		
	}

	public void setAttachments(String attachments) {
		
		this.attachments = attachments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
}
