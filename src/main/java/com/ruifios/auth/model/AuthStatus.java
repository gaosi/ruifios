package com.ruifios.auth.model;

import java.io.Serializable;

/**
 * 用户登录状态
 */
public class AuthStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String userName;
	
	private String realName;

	private boolean isLocked = false;
	
	private long lockEndTime = 0;

	private boolean legalUser = true;

	public AuthStatus() {

	}

	public AuthStatus(User user) {
		this.userId = user.getId();
		this.userName = user.getName();
		this.realName = user.getRealName();
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public long getLockEndTime() {
		return lockEndTime;
	}

	public void setLockEndTime(long lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public boolean isLegalUser() {
		return legalUser;
	}

	public void setLegalUser(boolean legalUser) {
		this.legalUser = legalUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}