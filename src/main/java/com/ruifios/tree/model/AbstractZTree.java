package com.ruifios.tree.model;

import java.util.List;

/**
 * 抽象树
 * @author ch
 *
 */
public abstract class AbstractZTree {

	public abstract List<? extends TreeNode> getNodes();

    public abstract boolean isSimpleData();
    
}
