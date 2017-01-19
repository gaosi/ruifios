package com.ruifios.commons;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * 基本时间类型
 * @author ch
 *
 */
@MappedSuperclass
public abstract class BaseTimedObject implements Serializable {
	
	private static final long serialVersionUID = -7803380066528297747L;

	protected long created = System.currentTimeMillis();
	
	protected long modified;

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}
	
}