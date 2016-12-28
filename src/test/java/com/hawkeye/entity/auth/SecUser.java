package com.hawkeye.entity.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户信息
 */
@Entity
@Table(name = "sec_user")
public class SecUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一编号
	 */
	@Id
	@Column(unique = true, length = 36, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	/**
	 * 登录名,唯一
	 */
	@Column(name = "name", length = 64, columnDefinition=" varchar(64) not null comment '登录名'")
	private String name;

	/**
	 * 用户角色
	 */
	@Column(name = "role", length = 64)
	private String role;

	/**
	 * 真实姓名
	 */
	@Column(name = "realname", length = 64)
	private String realName;

	/**
	 * 密码,加密存储
	 */
	@Column(name = "password", length = 256)
	private String password;

	/**
	 * 邮件,可为空
	 */
	@Column(name = "email", length = 64)
	private String email;

	/**
	 * 电话
	 */
	@Column(name = "phone", length = 32)
	private String phone;

	/**
	 * 手机
	 */
	@Column(name = "mobile", length = 32)
	private String mobile;

	/**
	 * 创建时间
	 */
	@Column(name = "createdtime", length = 20)
	public Long createdTime;

	/**
	 * 最后一次登录时间
	 */
	@Column(name = "lastlogintime", length = 20)
	private Long lastLoginTime;

	/**
	 * 超时时间(分钟)
	 */
	@Column(name = "timeout", length = 20)
	private Integer timeout;

	/**
	 * 描述
	 */
	@Column(name = "comment", length = 128)
	private String comment;

	/**
	 * 用户锁定状态 0：锁定 1：未锁
	 */
	@Column(name = "islock")
	private Integer islock = 1;

	/**
	 * 解锁时间
	 */
	@Column(name = "lockopentime", length = 20)
	private Long lockOpenTime;

	/**
	 * IP范围(数据权限)
	 */
	@Column(name = "iprange", length = 255)
	private String ipRange;

	/**
	 * 保留字段
	 */
	@Column(name = "reserve", length = 64)
	private String reserve;

	public SecUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
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

	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public Long getLockOpenTime() {
		return lockOpenTime;
	}

	public void setLockOpenTime(Long lockOpenTime) {
		this.lockOpenTime = lockOpenTime;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
