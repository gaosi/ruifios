package com.ruifios.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.ruifios.server.RuifiosEnv;

/**
 * 用户信息
 */
@Entity
@Table(name = "t_user", catalog=RuifiosEnv.DB_NAME)
public class User implements Serializable
{
	
	private static final long serialVersionUID = -593019564382543066L;

	/**
	 * 唯一编号
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	private String id;
	
	/**
	 * 登录名,唯一
	 */
	@NotNull
	@Column(length=64)
	private String name;
	
	/**
	 * 用户角色
	 */
	@Column
	private String role;
	
	/**
	 * 真实姓名
	 */
	@Column(length=64)
	private String realName;
	
	/**
	 * 密码,加密存储
	 */
	@Column(length=256)
	private String password;
	
	/**
	 * 邮件,可为空
	 */
	@Column(length=64)
	private String email;
	
	/**
	 * 电话
	 */
	@Column(length=32)
	private String phone;
	
	/**
	 * 手机
	 */
	@Column(length=32)
	private String mobile;
	
		
	/**
	 * 创建时间
	 */
	@Column
	public Long createdTime;
	
	/**
	 * 最后一次登录时间
	 */
	@Column
	private Long lastLoginTime;
	
	/**
	 * 超时时间(分钟)
	 */
	@Column
	private Integer timeout;
		
	/**
	 * 描述
	 */
	@Column(length=128)
	private String comment;
	
	/**
	 * 用户锁定状态
	 *  0：锁定
	 *  1：未锁
	 */
	@Column
	private Boolean islock;
	
	
	/**
	 * 解锁时间
	 */
	@Column
	private Long lockOpenTime;
	
	public User()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}


	public Long getCreatedTime()
	{
		return createdTime;
	}

	public void setCreatedTime(Long createdTime)
	{
		this.createdTime = createdTime;
	}


	public Long getLastLoginTime()
	{
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Boolean getIslock() {
		return islock;
	}

	public void setIslock(Boolean islock) {
		this.islock = islock;
	}

	public Long getLockOpenTime() {
		return lockOpenTime;
	}

	public void setLockOpenTime(Long lockOpenTime) {
		this.lockOpenTime = lockOpenTime;
	}

}
