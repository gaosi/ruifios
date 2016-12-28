package com.ruifios.tree.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 抽象标准树节点
 * @author ch
 * levelcode
 */
@MappedSuperclass
public abstract class AbstractStdTreeNode<T extends AbstractStdTreeNode<?>> extends AbstractTreeNode implements StdTreeNode {

	@Transient
	protected List<T> children;

	@Override
	public List<T> getChildren() {
		return children;
	}
	
	public void setChildren(List<T> children) {
        this.children = children;
    }
	
	public TreeNode addChild(T node) {
        if (children == null)
            children = new ArrayList<>();
        if (node != null)
            children.add(node);
        return this;
    }
}
