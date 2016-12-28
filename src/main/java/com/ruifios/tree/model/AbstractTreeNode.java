package com.ruifios.tree.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 抽象树节点
 * @author ch
 * levelcode
 */
@MappedSuperclass
public abstract class AbstractTreeNode implements TreeNode {
	
	@Column(name = "name")
	protected String name;
	
	@Transient
	protected String description;

	@Column(name = "levelcode")
	protected String levelcode = "";
	
	@Transient
	private boolean passing;

	@Override
	public String getKey() {
		String levelcode = getLevelcode();
		if (levelcode == null || levelcode.trim().length() == 0)
            return "0";
		if (levelcode.endsWith("/"))
            levelcode = levelcode.substring(0, levelcode.length() - 1);
        int index = levelcode.lastIndexOf("/");
        return index>-1 ? levelcode.substring(index+1) : "0";
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void getDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isPassing() {
		return passing;
    }

	@Override
    public void setPassing(boolean passing) {
        this.passing = passing;
    }

	@Override
	public String getLevelcode() {
		return levelcode;
    }

    public void setLevelcode(String levelcode) {
        this.levelcode = levelcode;
	}

	@Override
	public String getParentKey() {
		String levelcode = getLevelcode();
        if (levelcode == null || levelcode.trim().length() == 0)
            return "0";
        if (levelcode.endsWith("/"))
            levelcode = levelcode.substring(0, levelcode.length() - 1);
        String key = getKey();
        if (levelcode.endsWith("/" + key))
            levelcode = levelcode.substring(0, levelcode.length() - key.length() - 1);
        int index = levelcode.lastIndexOf("/");
        
		return index>-1 ? levelcode.substring(index + 1) : "0";
	}

	@Override
	public boolean isGroup() {
		return false;
	}

}
