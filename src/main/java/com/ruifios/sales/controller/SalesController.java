package com.ruifios.sales.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.annotation.FormParam;
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
	public String addSalesRecord(HttpServletRequest request, @FormParam("base.") BaseSales base, @FormParam("record.") List<SalesRecord> record) 
	{
		if(record != null && record.size()>0){
			for(SalesRecord r : record){
				System.out.println(r.getCommodityname());
			}
			throw new IllegalStateException("{success: 0}");
		}
		return "success";
	}
	
	@Override
	protected String getModule() {
		return "销售记录";
	}

}
