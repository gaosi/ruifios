package com.hawkeye.server;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;


public abstract class AbstractController
{
	
	protected static final String SUCCESS = "success";
	
	protected abstract String getModule();
	
	/**
	 * 将提交表单封装为对象
	 * @param request
	 * @param t
	 * eg :
	 * T t = new T();
	 * buildEntity(request, t);
	 * @return
	 */
	protected <T> T buildEntity(HttpServletRequest request, T t)
	{
		try
		{
			Field[] fields = t.getClass().getDeclaredFields();
			for(Field f: fields)
			{
				int mod = f.getModifiers();
				String value = request.getParameter(f.getName());
				if(!StringUtils.isBlank(value) && !Modifier.isFinal(mod) && !Modifier.isStatic(mod) && f.getType() != Date.class)
				{
					PropertyUtils.setProperty(t, f.getName(), value);
				}
			}
			
			return t;
		} 
		catch (Exception e)
		{
			return t;
		}
	}

	/**
	 * 获取 Integer 参数
	 * @param request
	 * @param param
	 * @return
	 */
	protected int getIntParam(HttpServletRequest request, String param)
	{
		try
		{
			String value = request.getParameter(param);
			if(value != null)
			{
				return Integer.parseInt(value);
			}
			return 0;
		}
		catch (Exception e) 
		{
			return 0;
		}
	}

	/**
	 * 获取 Long 参数
	 * @param request
	 * @param param
	 * @return
	 */
	protected long getLongParam(HttpServletRequest request, String param)
	{
		try
		{
			String value = request.getParameter(param);
			if(value != null)
			{
				return Long.parseLong(value);
			}
			return 0;
		} 
		catch (Exception e)
		{
			return 0;
		}
	}
	
	protected void logOk(HttpServletRequest request, String function)
	{
		String status = "成功";		
		
		log(request, getModule(), function, status);
	}
	
	protected void logFail(HttpServletRequest request, String function)
	{
		String status = "失败";
		
		log(request, getModule(), function, status);
	}
	
	protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date parseDate(String str)
	{
		try
		{
			return format.parse(str);
		} catch (ParseException e)
		{
			return null;
		}
	}
	
	private void log(HttpServletRequest request, String module, String function, String status)
	{
//		HttpSession session = request.getSession();
//		Cookie[] cookies = request.getCookies();
		String srcIp = request.getRemoteHost();
		System.out.println(srcIp+" module: "+module+" function: "+function+" status: "+status);
	}
}