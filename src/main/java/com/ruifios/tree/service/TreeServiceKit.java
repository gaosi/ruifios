package com.ruifios.tree.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruifios.sales.model.ShopsInfo;
import com.ruifios.server.RuifiosEnv;
import com.ruifios.tree.model.ArrayTree;
import com.ruifios.tree.model.StandardTree;
import com.ruifios.tree.model.TreeNode;

/**
 * 树助手
 */
@Service(TreeServiceKit.IOC_NAME)
public class TreeServiceKit
{
	
	public final static String IOC_NAME = "TreeServiceKit";
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private static Logger logger = Logger.getLogger(TreeServiceKit.class);

	public TreeServiceKit() {
		
	}

	/**
	 * 获取所有树节点
	 * @param condition
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<TreeNode> queryNodes(String condition, Class<? extends TreeNode>... classes) {
		List<TreeNode> nodes = new ArrayList<>();
		try {
			if (classes != null){
				for(Class<? extends TreeNode> cls: classes){
					nodes.addAll(RuifiosEnv.dao.query(cls, condition));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return nodes;
	}

	/**
	 * 创建完整树
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StandardTree buildFullTree(String condition, Class<? extends TreeNode>... classes){
		StandardTree tree = new StandardTree();
		tree.setNodes(queryNodes(condition, classes));
		return tree;
	}
	
	/**
	 * 创建标准树
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StandardTree buildStdTree(String condition, Class<? extends TreeNode>... classes){
		StandardTree fulltree = buildFullTree(condition, classes);
		/* 设置过路节点
		Map<String, Boolean> filteredMap = new HashMap<String, Boolean>();
		for(TreeNode node: fulltree.getNodes()){
			filteredMap.put(node.getKey(), Boolean.TRUE);
			String levelcode = node.getLevelcode();
			if (levelcode != null)
                for (String key : levelcode.split("/")) {
                    if (!"0".equals(key) && !filteredMap.containsKey(key))
                        filteredMap.put(key, Boolean.FALSE);
                }
		}
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(TreeNode node: fulltree.getNodes()){
			if(filteredMap.containsKey(node.getKey())){
				node.setPassing(!filteredMap.get(node.getKey()));
				nodes.add(node);
			}
		}
		fulltree.setNodes(nodes);*/
		return fulltree;
	}
	
	/**
	 * 创建数组树
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayTree buildArrayTree(String condition, Class<? extends TreeNode>... classes){
		StandardTree tree = buildStdTree(condition, classes);
		return ArrayTree.from(tree);
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		StandardTree simpleTree = new StandardTree();
		String levelcode = "";
		String levelcode1 = "";
		List<TreeNode> shops = new ArrayList<TreeNode>();
		
		for(int i=1; i<4; i++){
			ShopsInfo node = new ShopsInfo(); 
			node.setId(i);
			node.setName("name"+i);
			levelcode += "/"+i;
			node.setLevelcode(levelcode);
			
			shops.add(node);
			
			ShopsInfo node1 = new ShopsInfo(); 
			node1.setId(i+10);
			node1.setName("name"+(i+10));
			levelcode1 += "/"+(i+10);
			node1.setLevelcode(levelcode1);
			
			shops.add(node1);
		}
		
		simpleTree.setNodes(shops);
		System.out.println("shops: "+mapper.writeValueAsString(shops));  
		
		//System.out.println(mapper.writeValueAsString(from(simpleTree)));  
		
		System.out.println(mapper.writeValueAsString(ArrayTree.from(simpleTree)));
	}
	/*
	public static ArrayTree from(StandardTree simpleTree) throws JsonProcessingException {

		List<TreeNodeWrapper> nodes = simpleTree.getNodes().stream().map(n ->  new TreeNodeWrapper(n)).collect(Collectors.toList());
		System.out.println("nodes: "+mapper.writeValueAsString(nodes));  
		
		Map<String, List<TreeNodeWrapper>> map = nodes.stream().collect(Collectors.groupingBy(n -> n.getParentKey(), Collectors.toList()));
		System.out.println("map: "+mapper.writeValueAsString(map));  
		
		nodes = nodes.stream().peek(n -> {
            if (map.containsKey(n.getKey()))
                n.setChildren(map.get(n.getKey()));
        }).collect(Collectors.toList());
		System.out.println("peek: "+mapper.writeValueAsString(nodes));  
		 
		nodes = nodes.stream().filter(n -> "0".equals(n.getParentKey())).collect(Collectors.toList());
		System.out.println("filter: "+mapper.writeValueAsString(nodes)); 
		
        ArrayTree tree = new ArrayTree();
		tree.setNodes(nodes);
		
        return tree;
	}
	*/
}