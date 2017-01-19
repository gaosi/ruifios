package com.ruifios.sales.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.commons.Pager;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.sales.service.SalesService;
import com.ruifios.server.AbstractController;

/**
 * 销售记录查询统计
 * @author ch
 *
 */
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
	public ModelAndView main(HttpServletRequest request) 
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
			condition += " and consumercard like '%"+consumercard+"%'";
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
	
	/**
	 * 销售记录统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/statistical")
	public ModelAndView statistical(HttpServletRequest request) 
	{
		ModelAndView view = new ModelAndView("sales/statistical");
		
		logOk(request, "进入销售统计页面");
		return view;
	}	
	
	
	/**
	 * 30天销量和商铺销售趋势
	 * option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    dataZoom : {
		        show : true,
		        realtime: true,
		        start : 22,
		        end : 30
		    },
		    calculable : true,
		    legend: {
		        data:['蒸发量','降水量','平均温度']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '水量',
		            axisLabel : {
		                formatter: '{value} ml'
		            }
		        },
		        {
		            type : 'value',
		            name : '温度',
		            axisLabel : {
		                formatter: '{value} °C'
		            }
		        }
		    ],
		    series : [
		
		        {
		            name:'蒸发量',
		            type:'bar',
		            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
		        },
		        {
		            name:'降水量',
		            type:'bar',
		            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
		        },
		        {
		            name:'平均温度',
		            type:'line',
		            yAxisIndex: 1,
		            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
		        }
		    ]
		};
                    
	 */
	@ResponseBody 
	@RequestMapping("/getstatistical")
	public ModelMap getstatistical(HttpServletRequest request, ModelMap map) {
		
		return map;
	}
	
	@Override
	protected String getModule() {
		return "销售记录";
	}

}
