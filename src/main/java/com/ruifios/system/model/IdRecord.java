package com.ruifios.system.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ruifios.server.RuifiosEnv;

/**
 * 数据库表Id记录
 */
@Entity
@Table(name=IdRecord.TABLE_NAME, catalog=RuifiosEnv.DB_NAME)
public class IdRecord implements Serializable
{
	public static final String TABLE_NAME = "t_idrecords";
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "maxid")
	private long maxid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMaxid() {
		return maxid;
	}

	public void setMaxid(long maxid) {
		this.maxid = maxid;
	}
	
}

