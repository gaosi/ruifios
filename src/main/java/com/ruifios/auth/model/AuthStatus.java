package com.ruifios.auth.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户登录状态
 */
public class AuthStatus implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("id")
	protected String userId;
	
	protected String userName;
	
	protected String realName;
	
	protected String userPhone;
	
	protected double consum;

	protected boolean isLocked = false;
	
	protected long lockEndTime = 0;

	protected boolean legalUser = true;

	public AuthStatus() {

	}

	public AuthStatus(User user) {
		this.userId = user.getId();
		this.userName = user.getName();
		this.realName = user.getRealName();
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public double getConsum() {
		return consum;
	}

	public void setConsum(double consum) {
		this.consum = consum;
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

}