package net.cec.entities;

import com.google.gson.annotations.Since;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class Account {

	
	@Id
	private long id;

	
	@Index
	private String permission;

	@Unindex
	private String name;

	
	@Unindex
	private String realName;

	
	@Index
	private String email;

	
	@Unindex
	private String phone;

	
	@Unindex
	private String phone_code;

	
	@Unindex
	private String address;

	
	@Unindex
	private String accessToken;

	
	@Unindex
	private String longLiveToken;

	@Index
	private long joinTime;

	
	@Index
	private long lastLogin;

	@Since(1.0)
	@Unindex
	private String avatar;

	
	@Unindex
	private String gender;

	
	@Index
	private int post;

	
	@Index
	private int like;

	@Since(1.0)
	@Index
	private int comment;

	
	@Index
	private int share;

	
	@Index
	private boolean isGroupMember;

	
	@Index
	private int scores;

	
	@Index
	private int money;

	
	@Unindex
	private long birthday;

	
	@Index
	private String userClass;

	
	@Index
	private int level;

	
	@Index
	private int dedication;

	
	@Index
	private int daily;

	@Since(1.0)
	@Index
	private int longestDaily;

	// user tu gioi thieu ve ban than
	@Unindex
	private String introduce;

	// khen thuong do admin set
	@Unindex
	private String felicitation;

	@Unindex
	private String badge;

	@Index
	private String fbId;
	
	@Index
	private String messengerId;

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public int getDedication() {
		return dedication;
	}

	public void setDedication(int dedication) {
		this.dedication = dedication;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public boolean isGroupMember() {
		return isGroupMember;
	}

	public void setGroupMember(boolean isGroupMember) {
		this.isGroupMember = isGroupMember;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getFelicitation() {
		return felicitation;
	}

	public void setFelicitation(String felicitation) {
		this.felicitation = felicitation;
	}

	public int getLongestDaily() {
		return longestDaily;
	}

	public void setLongestDaily(int longestDaily) {
		this.longestDaily = longestDaily;
	}

	public String getLongLiveToken() {
		return longLiveToken;
	}

	public void setLongLiveToken(String longLiveToken) {
		this.longLiveToken = longLiveToken;
	}

	public String getPhone_code() {
		return phone_code;
	}

	public void setPhone_code(String phone_code) {
		this.phone_code = phone_code;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getMessengerId() {
		return messengerId;
	}

	public void setMessengerId(String messengerId) {
		this.messengerId = messengerId;
	}

}
