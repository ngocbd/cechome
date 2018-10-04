package net.cec.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
@Cache
@Entity
public class RequestReview {
	@Id
	private String postid;
	
	@Unindex
	private String reviewPostId;
	//account id
	@Unindex
	private String editorAccountId;
	
	//messenger id
	@Unindex
	private String editorMessengerId;
		
	//accountId
	@Unindex
	private String requesterAccountId;
	
	//messenger
	@Unindex
	private String requesterMessengerId;
	
	@Unindex
	private long createdDate;
	
	@Unindex
	private long reviewDate;

	//0: request - 1: editing - 2: done
	@Index
	private int status;
	
	@Unindex
	private int price;
	
	

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getReviewPostId() {
		return reviewPostId;
	}

	public void setReviewPostId(String reviewPostId) {
		this.reviewPostId = reviewPostId;
	}

	public String getEditorAccountId() {
		return editorAccountId;
	}

	public void setEditorAccountId(String editorAccountId) {
		this.editorAccountId = editorAccountId;
	}

	public String getEditorMessengerId() {
		return editorMessengerId;
	}

	public void setEditorMessengerId(String editorMessengerId) {
		this.editorMessengerId = editorMessengerId;
	}

	public String getRequesterAccountId() {
		return requesterAccountId;
	}

	public void setRequesterAccountId(String requesterAccountId) {
		this.requesterAccountId = requesterAccountId;
	}

	public String getRequesterMessengerId() {
		return requesterMessengerId;
	}

	public void setRequesterMessengerId(String requesterMessengerId) {
		this.requesterMessengerId = requesterMessengerId;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public long getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(long reviewDate) {
		this.reviewDate = reviewDate;
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