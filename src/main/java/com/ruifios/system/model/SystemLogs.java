package com.ruifios.system.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.ruifios.server.RuifiosEnv;

/**
 * 系統日誌
 */
@Entity
@Table(name = "t_sys_logs", catalog=RuifiosEnv.DB_NAME)
public class SystemLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="pk_long_gen")
	@TableGenerator(name = "pk_long_gen",  
	    table=IdRecord.TABLE_NAME,  
	    pkColumnName="name",  
	    valueColumnName="maxid",  
	    pkColumnValue="t_sys_logs",  
	    allocationSize=1  
	) 
	private Long id;

	/** 记录时间 */
	@Column
	private Long timestamp;

	@Transient
	private String timestamp_str;
	
	/** 用户id */
	@Column
	private String userid;
	
	/** 用户名 */
	@Column
	private String username;
	
	/** 客户端IP */
	@Column
	private String sourceip;
	
	/** 模块 */
	@Column
	private String module;
	
	/** 操作 */
	@Column
	private String action;
	
	/** 信息 */
	@Column
	private String msg;
	
	/** 结果 */
	@Column
	private Integer result;
	
	/** 原因 */
	@Column
	private String cause;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSourceip() {
		return sourceip;
	}

	public void setSourceip(String sourceip) {
		this.sourceip = sourceip;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getTimestamp_str() {
		return timestamp_str;
	}

	public void setTimestamp_str(String timestamp_str) {
		this.timestamp_str = timestamp_str;
	}
}
