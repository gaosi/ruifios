package com.ruifios.server;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import com.ruifios.dao.Dao;
import com.ruifios.system.engine.SysLogServiceEngine;

/**
 * 系统启动加载管理
 * 
 * org.apache.commons.lang3.StringUtils
 */
public class RuifiosEnv implements ApplicationContextAware, ServletContextAware
{
	
	private Logger logger = Logger.getLogger(RuifiosEnv.class);

	/**
	 * JSON转换工具类
	private static ObjectMapper mapper = new ObjectMapper();O
	 */
	
	/**
	 * Spring 容器
	 */
	public static ApplicationContext context = null;
	
	/**
	 * dao
	 */
	public static Dao dao = null;

	/**
	 * 数据库名称
	 */
	public static final String DB_NAME = "ruifios";
	
	/**
	 * web|war实际绝对路径
	 */
	public static String WEBWAR_DIR = "web.dir";

	/**
	 * WEB-INF实际绝对路径
	 */
	public static String WEBINF_DIR = "web.inf";
	
	/**
	 * tomcat实际绝对路径
	 */
	public static String TOMCAT_DIR = "tomcat.dir";
	
	/**
	 * 保存可定制的系统变量，类型不限
	 */
	private static Map<String, Object> ctx = Collections.synchronizedMap(new LinkedHashMap<String, Object>());
	
	@Override
	public void setServletContext(ServletContext servletcontext) {
		try {
			Path appRootPath = new File(getAppRoot(servletcontext)).toPath(); 
			Path path = appRootPath.normalize();
			ctx.put(WEBWAR_DIR, path);
			path = path.resolve("WEB-INF");
			ctx.put(WEBINF_DIR, path);
			
			Path serviceRealPath = new File(System.getProperty("java.io.tmpdir")).toPath();
			ctx.put(TOMCAT_DIR, serviceRealPath);
			logger.info("ServletContext ready !!!");
		} catch (Exception e) {
			logger.warn("ServletContext init error: ", e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		context = applicationcontext;
		// 获取DAO
		RuifiosEnv.dao = (Dao)context.getBean("dao");
		logger.info("ApplicationContext ready !!!");
		
		// 啟動日志記錄引擎
		SysLogServiceEngine.getInstance().start();
	}
	
	/**
	 * 获取应用根目录
	 * @param context
	 * @return
	 */
    private String getAppRoot(ServletContext context) {
        String webinf = context.getRealPath("/WEB-INF/");
        if (webinf == null) {
        	logger.info("/WEB-INF/ not Found?!");
            return "";
        }
        String root = context.getRealPath("/").replace('\\', '/');
        if (root.endsWith("/"))
            return root.substring(0, root.length() - 1);
        else if (root.endsWith("/."))
            return root.substring(0, root.length() - 2);
        return root;
    }

	/**
	 * 获取绝对路径
	 * @param path
	 * @return
	 */
	public static Path getAbsolutePath(String path)
	{
		return (Path)ctx.get(path);
	}

	/**
	 * 获取相对路径
	 * @param path
	 *  绝对路径 web、tomcat、web-inf
	 * @param name
	 * 	相对目录名称
	 * @return
	 */
	public static Path getServiceRealPath(String path, String name) {
		Path absolutepath = getAbsolutePath(path);
		return absolutepath.resolve(name);
	}
	
	/**
	 * 获取资源文件内容
	 */
	public static String getMessage(String key) {
		return getMessage(key, Locale.getDefault());
	}

	/**
	 * 获取资源文件内容
	 */
	public static String getMessage(String key, Locale local) {
		return getMessage(key, null, local);
	}

	/**
	 * 获取资源文件内容
	 */
	public static String getMessage(String key, Object[] objs, Locale local) {
		return context.getMessage(key, objs, local);
	}

}
