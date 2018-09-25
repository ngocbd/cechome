package net.cec.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Lesson { 
//	editorId = accountId
	@Id
	private long id;
	
	@Index
	private List<Integer> lesson = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public List<Integer> getLesson() {
		return lesson;
	}

	public void setLesson(List<Integer> lesson) {
		this.lesson = lesson;
	}
	
}