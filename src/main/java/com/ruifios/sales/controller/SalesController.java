package com.ruifios.sales.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.annotation.FormParam;
import com.ruifios.commons.Pager;
import com.ruifios.sales.model.BaseSales;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.sales.service.SalesService;
import com.ruifios.server.AbstractController;

@Controller
@RequestMapping("/sales")
public class SalesController extends AbstractController{

	@Autowired
	@Qualifier(SalesService.IOC_NAME)
	private SalesService salesservice;
	
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
	
	/**
	 * 添加销售记录
	 * @param request
	 * @param base
	 * @param record
	 * @return
	 */
	@ResponseBody 
	@RequestMapping("/add")
	public String addSalesRecord(HttpServletRequest request, @FormParam("base.") BaseSales base, @FormParam("record.") List<SalesRecord> records) 
	{
		if(base != null && records != null && records.size()>0){
			salesservice.addSalesRecord(base, records);
			return base.getConsumercard();
		}
		return "";
	}

	/**
	 * 获取个人销售记录
	 * @param request
	 * @param pager
	 * @return
	 */
	@ResponseBody 
	@RequestMapping("/getsalesrecord")
	public Pager<SalesRecord> getSalesRecord(HttpServletRequest request, Pager<SalesRecord> pager) 
	{
		String consumercard = request.getParameter("consumercard");
		String condition = " ";
		if(StringUtils.isEmpty(consumercard)){
			condition += "where consumercard = '"+consumercard+"'";
		}
		
		salesservice.getSalesRecord(pager, condition);
		return pager;
	}
	
	@Override
	protected String getModule() {
		return "销售记录";
	}

}
