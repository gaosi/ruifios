package com.ruifios.tree.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准树
 * @author dysec
 *	<script language="javascript">  
 *	   	var zTreeObj;  
 *	   	// 标准格式  
 *	   	var zNodes =  [  
 *	    	{"id":1, "pId":0, "name":"test1"},  
 *	    	{"id":11, "pId":1, "name":"test11"},  
 *	    	{"id":12, "pId":1, "name":"test12"},  
 *	    	{"id":111, "pId":11, "name":"test111"}  
 *		];  
 *	   	$(document).ready(function(){  
 *	      	zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);  
 *	   	});  
 * 	</script>  
 */
public class StandardTree extends AbstractZTree
{

	private List<TreeNode> nodes;
	
	@Override
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<? extends TreeNode> nodes) {
		this.nodes = new ArrayList<>();
		this.nodes.addAll(nodes);
	}
	
	public <T extends TreeNode> StandardTree addNode(T node) {
		if (this.nodes == null) {
            this.nodes = new ArrayList<>();
        }
		this.nodes.add(node);
		return this;
	}
	
	@Override
	public boolean isSimpleData() {
		return true;
	}

}