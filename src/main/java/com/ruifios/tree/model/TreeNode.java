package com.ruifios.tree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 树节点接口
 * @author ch
 *
 */
public interface TreeNode {

    /**
     * 获取树节点唯一标识符
     * 为了规避节点ID既可能是整数型也可能是字符串型给接口定义带来不便，使用key作为标识符名称，并统一使用字符串型表示
     * 在具体实现中，应将实际的ID值转换为字符串型后通过本方法返回
     * @return
     */
    public String getKey();

    /**
     * 名称
     * @return
     */
    public String getName();

    /**
     * 描述
     * @return
     */
    public String getDescription();

    /**
     * 是否为路过节点，即不具有权限，但为了保证树的正常显示而存在的节点<br/>
     * 不做数据库存储
     * @return
     */
    public boolean isPassing();

    /**
     * 设置路过状态
     * @param passing
     */
    public void setPassing(boolean passing);

    /**
     * 获取节点级次码
     * <b>每个具体树节点实现类负责实现，在最后时刻确定级次码的存放位置：数据库同一张表中/外键方式存储在另一张表中/多对多关联关系等</b>
     * @return
     */
    public String getLevelcode();

    /**
     * 冗余接口，方便界面使用，直接调用{@link #getLevelcode()}截断获得即可
     * @return
     */
    public String getParentKey();

    /**
     * 是否为组节点
     * 当使用转换为JSON时，属性名称为isParent(方便zTree直接使用)
     * @return
     */
    @JsonProperty("isParent")
    public boolean isGroup();
    
}
