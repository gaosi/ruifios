package com.hawkeye.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类
 * @author 陈华
 *
 */
public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static LocaleResource getLocaleResource(HttpServletRequest request)
	{
		String lang = request.getParameter("lang");
		if (lang == null) 
		{
			lang = (String)request.getSession().getAttribute("lang");
			if (lang == null) 
			{
				lang = "en";
				Enumeration<String> enu = request.getHeaderNames();
				while (enu.hasMoreElements()) 
				{
					String str = (String)enu.nextElement();
					if (str.contains("language")) 
					{
						logger.info(str + " : " + request.getHeader(str));
						if (request.getHeader(str).contains("zh")) 
						{
							lang = "zh";
							break;
						}
						if (request.getHeader(str).contains("ja")) 
						{
							lang = "ja";
							break;
						}
					}
				}
				request.getSession().setAttribute("lang", lang);
			}
		}
		else
		{
			request.getSession().setAttribute("lang", lang);
		}
		
		if ((lang.equals("zh")) || (lang.equals("zh_CN")))
		{
			return Constant.resZH;
		}
		if (lang.equals("ja")) 
		{
			return Constant.resJA;
		}
		return Constant.resEN;
	}
	
}
