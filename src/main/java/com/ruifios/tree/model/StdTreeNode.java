package com.ruifios.tree.model;

import java.util.List;

/**
 * 树节点接口
 * @author ch
 * 本类的实现通常应该可以由{@link TreeNode}的对象转换而来，不许从数据库中直接组装 
 */
public interface StdTreeNode extends TreeNode {

    /**
     * 获取所有直系子节点对象
     * <b>通常不会存储在数据库中</b>
     * @return
     */
    public List<? extends StdTreeNode> getChildren();
    
}
