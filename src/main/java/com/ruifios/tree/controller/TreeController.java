package com.ruifios.tree.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruifios.commons.ListWraper;
import com.ruifios.sales.model.BaseSales;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.sales.model.ShopsInfo;
import com.ruifios.server.AbstractController;
import com.ruifios.tree.model.ArrayTree;
import com.ruifios.tree.service.TreeServiceKit;

/**
 * 树管理
 * @author ch
 *
 */
@RestController
@RequestMapping("/tree")
public class TreeController extends AbstractController {

	@Autowired
	@Qualifier(TreeServiceKit.IOC_NAME)
	private TreeServiceKit kit;
	
	/**
	 * 获取商家商品树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getshopsinfo")
	@SuppressWarnings("unchecked")
	public ArrayTree getShopsInfo(HttpServletRequest request) 
	{
		String condition = request.getParameter("condition");
		return kit.buildArrayTree(condition, ShopsInfo.class);
	}	
	
	@ResponseBody
	@RequestMapping("/add")
	public String addSalesRecord(HttpServletRequest request, @Param("base")BaseSales base, @Param("record")ListWraper<SalesRecord> record) 
	{
		
		return "";
	}
	
	@Override
	protected String getModule() {
		return "销售记录";
	}

}
