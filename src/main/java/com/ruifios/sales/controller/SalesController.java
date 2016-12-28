package com.ruifios.sales.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.commons.ListWraper;
import com.ruifios.sales.model.BaseSales;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.server.AbstractController;

@Controller
@RequestMapping("/sales")
public class SalesController extends AbstractController{

	/**
	 * 进入添加销售记录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView loginIndex(HttpServletRequest request) 
	{
		ModelAndView view = new ModelAndView("sales/index");
		
		logOk(request, "进入销售记录页面");
		return view;
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
