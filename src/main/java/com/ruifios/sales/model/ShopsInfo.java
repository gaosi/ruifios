package com.ruifios.sales.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ruifios.tree.model.AbstractTreeNode;

/**
 * 商家信息
 * @author ch
 *
 */
@Entity
@Table(name = "t_shopsinfo")
public class ShopsInfo extends AbstractTreeNode implements Serializable {

	private static final long serialVersionUID = 4484458537676157703L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/*@Override
	public String getKey() {
		return String.valueOf(id);
	}*/
	
}
