package net.cec.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class RequestReview {
	@Id
	private String postid;
	
	@Unindex
	private String editorId;
	
	@Unindex
	private String requesterId;
	
	@Unindex
	private long createdDate;
	
	@Unindex
	private long reviewDate;

	//0: request - 1: editing - 2: done
	@Unindex
	private int status;
	
	@Unindex
	private int price;

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public long getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(long reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
