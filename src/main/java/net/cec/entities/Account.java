package net.cec.entities;

import com.google.gson.annotations.Since;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
@Cache
@Entity
public class Account {

	@Since(1.0)
	@Id
	private long id;

	@Since(1.0)
	@Index
	private String permission;

	@Unindex
	@Since(1.0)
	private String name;

	@Since(2.0)
	@Unindex
	private String realName;

	@Since(2.0)
	@Index
	private String email;

	@Since(2.0)
	@Unindex
	private String phone;

	@Since(2.0)
	@Unindex
	private String phone_code;

	@Since(2.0)
	@Unindex
	private String address;

	@Since(3.0)
	@Unindex
	private String accessToken;

	@Since(3.0)
	@Unindex
	private String longLiveToken;

	@Since(2.0)
	@Index
	private long joinTime;

	@Since(2.0)
	@Index
	private long lastLogin;

	@Since(1.0)
	@Unindex
	private String avatar;

	@Since(2.0)
	@Unindex
	private String gender;

	@Since(1.0)
	@Index
	private int post;

	@Since(1.0)
	@Index
	private int like;

	@Since(1.0)
	@Index
	private int comment;

	@Since(1.0)
	@Index
	private int share;

	@Since(2.0)
	@Index
	private boolean isGroupMember;

	@Since(1.0)
	@Index
	private int scores;

	@Since(2.0)
	@Index
	private int money;

	@Since(2.0)
	@Unindex
	private long birthday;

	@Since(2.0)
	@Index
	private String userClass;

	@Since(1.0)
	@Index
	private int level;

	@Since(1.0)
	@Index
	private int dedication;

	@Since(1.0)
	@Index
	private int daily;

	@Since(1.0)
	@Index
	private int longestDaily;

	// user tu gioi thieu ve ban than
	@Since(1.0)
	@Unindex
	private String introduce;

	// khen thuong do admin set
	@Since(1.0)
	@Unindex
	private String felicitation;

	@Since(2.0)
	@Unindex
	private String badge;
	
	@Since(2.0)
	@Index
	private String fbId;
	
	@Since(2.0)
	@Unindex
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
