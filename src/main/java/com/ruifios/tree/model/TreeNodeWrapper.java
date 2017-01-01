package com.ruifios.tree.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * 树节点包装类
 * 
 * @author ch
 *
 */
public class TreeNodeWrapper extends AbstractStdTreeNode<TreeNodeWrapper> {
	
	@JsonUnwrapped
	private TreeNode node;

	@JsonProperty("")
	public TreeNode getNode() {
		return node;
	}

	public TreeNodeWrapper(TreeNode node) {
		this.node = node;
	}

    @JsonIgnore
    @Override
    public String getKey() {
        return node.getKey();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return node.getName();
    }

    @JsonIgnore
    @Override
    public String getDescription() {
        return node.getDescription();
    }

    @JsonIgnore
    @Override
    public String getLevelcode() {
        return node.getLevelcode();
    }

    @JsonIgnore
    @Override
    public String getParentKey() {
        return node.getParentKey();
    }

    @JsonIgnore
    @Override
    public boolean isPassing() {
        return node.isPassing();
    }

    @JsonIgnore
    @Override
    public boolean isGroup() {
        return node.isGroup();
    }
	
    @Override
    public List<TreeNodeWrapper> getChildren() {
    	return children;
    }
    
    @Override
    public void setChildren(List<TreeNodeWrapper> children) {
    	this.children = children;
    }
    
    @Override
    public TreeNode addChild(TreeNodeWrapper node) {
    	if (this.children == null)
            this.children = new ArrayList<>();
        this.children.add(node);
        return this;
    }
}
