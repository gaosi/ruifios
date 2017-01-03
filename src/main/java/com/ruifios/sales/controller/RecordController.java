package com.ruifios.sales.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.commons.Pager;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.sales.service.SalesService;
import com.ruifios.server.AbstractController;

@Controller
@RequestMapping("/record")
public class RecordController extends AbstractController{

	@Autowired
	@Qualifier(SalesService.IOC_NAME)
	private SalesService salesservice;
	
	/**
	 * 进入添加销售记录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView loginIndex(HttpServletRequest request) 
	{
		ModelAndView view = new ModelAndView("sales/main");
		
		logOk(request, "进入销售记录页面");
		return view;
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
		String merchantname = request.getParameter("merchantname");
		String commodityname = request.getParameter("commodityname");
		String condition = "";
		if(!StringUtils.isEmpty(consumercard)){
			condition += " and consumercard = '"+consumercard+"'";
		}
		if(!StringUtils.isEmpty(merchantname)){
			condition += " and merchantname = '"+merchantname+"'";
		}
		if(!StringUtils.isEmpty(commodityname)){
			condition += " and commodityname = '"+commodityname+"'";
		}
		if(!StringUtils.isEmpty(condition)){
			condition = condition.replaceFirst("and", " where ");
		}
		
		salesservice.getSalesRecord(pager, condition);
		return pager;
	}
	
	@Override
	protected String getModule() {
		return "销售记录";
	}

}
