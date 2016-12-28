package com.ruifios.tree.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数组树
 * @author dysec
 *	<script language="javascript">  
 *	   	var zTreeObj;  
 *	   	// 使用数组array的方式，必须有这个设置  
 *		var setting = {  
 *			data: {  
 *				simpleData: {  
 *             		enable: true,  
 *	            	idKey: "id",  
 *	            	pIdKey: "pId",  
 *	            	rootPId: 0  
 *	        	}  
 *	    	}  
 *		};  
 *		//数组array  
 *		var nodes = [  
 *	    	{name: "父节点1", children: [  
 *	        	{name: "子节点1"},  
 *	        	{name: "子节点2"}  
 *	    	]},  
 *	    	{name: "父节点2", children: [  
 *	        	{name: "子节点21"},  
 *	        	{name: "子节点22"}  
 *	    	]}  
 *		];   
 *	   	$(document).ready(function(){  
 *	      	zTreeObj = $.fn.zTree.init($("#tree"), setting, nodes);  
 *	   	});  
 * 	</script>  
 */
public class ArrayTree extends AbstractZTree
{

	private List<StdTreeNode> nodes;

	public static final ArrayTree from(StandardTree standardtree) {
		ArrayTree tree = new ArrayTree();
		List<? extends TreeNode> list = null;
		if ((list = standardtree.getNodes()) != null) {
			List<TreeNodeWrapper> nodes = new ArrayList<TreeNodeWrapper>();
			for(TreeNode node: list){
				TreeNodeWrapper wrapper = new TreeNodeWrapper(node);
				nodes.add(wrapper);
			}
			
			Map<String, List<TreeNodeWrapper>> map = new HashMap<String, List<TreeNodeWrapper>>();
			for(TreeNodeWrapper wrapper: nodes){
				String key = wrapper.getParentKey();
				List<TreeNodeWrapper> value = map.get(key);
				if(value == null){
					value = new ArrayList<TreeNodeWrapper>();
					map.put(key, value);
				}
				value.add(wrapper);
			}
			
			List<TreeNodeWrapper> newnodes = new ArrayList<TreeNodeWrapper>();
			for(TreeNodeWrapper wrapper: nodes){
				String key = wrapper.getKey();
				if (map.containsKey(key)){
					wrapper.setChildren(map.get(key));
				}
				
				// 筛选组节点
				if("0".equals(wrapper.getParentKey())){
					newnodes.add(wrapper);
				}
			}
			tree.setNodes(newnodes);
		}
		
		return tree;
	}

	@Override
	public boolean isSimpleData() {
		return false;
	}

	@Override
	public List<? extends StdTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<? extends StdTreeNode> nodes) {
		this.nodes = new ArrayList<>();
        this.nodes.addAll(nodes);
	}

	public <T extends StdTreeNode> ArrayTree addNode(T node){
		if (this.nodes == null)
            this.nodes = new ArrayList<>();
		this.nodes.add(node);
        return this;
	}
	
}