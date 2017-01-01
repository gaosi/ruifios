package com.hawkeye.server;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ruifios.dao.Dao;

public class Env implements ApplicationContextAware, ServletContextListener
{
	private Logger log = Logger.getLogger(Env.class);
	 
	//建议直接使用 SystemUtils.IS_OS_LINUX
	//public static final Boolean linux = SystemUtils.IS_OS_LINUX;
	
	public static ApplicationContext context = null;
	public static Dao dao = null;
	public static final String APPREALPATH = "/WEB-INF/";
	public static final String PATH_WEB = "web";
	public static final String PATH_WEBINF = "webinf";
	public static final String PATH_CONF = "config";
	public static final String PATH_MESSAGE = "message";
	private static final Map<String, Path> PATHS = new HashMap<String, Path>();
	
	public static String Env_RootDir = "ruifios.root";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) 
	{
		Path apprealpath = new File(getAppRoot(contextEvent.getServletContext())).toPath();
		Path arg = apprealpath.resolve(APPREALPATH);
		
		PATHS.put("web", apprealpath);
		PATHS.put("webinf", arg);
		PATHS.put("config", arg.resolve("config"));
		PATHS.put("message", arg.resolve("message"));
		
		String path = System.getProperty("user.dir");
		System.setProperty(Env_RootDir, path);
		
		Thread gc = new Thread()
		{
			@Override
			public void run()
			{
				while(true)
				{
					try
					{
						Thread.sleep(5*60*1000);
					}
					catch (InterruptedException e)
					{
						log.error("GC error: ", e);
					}
					
					System.gc();
				}
			}
		};
		gc.setName("GC Thread");
		gc.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException 
	{
		context = appContext;
		//Env.dao = (Dao)context.getBean("mysqlDynamicdao");
		dao = (Dao)context.getBean("dao");
	}

	public String getAppRoot(ServletContext servletcontext) 
	{
		String webinf = servletcontext.getRealPath(APPREALPATH);
		if (webinf == null) {
			log.info("/WEB-INF/ not Found?!");
			return "";
		}
		String root = servletcontext.getRealPath("/").replace('\\', '/');
		if (root.endsWith("/"))
			return root.substring(0, root.length() - 1);
		if (root.endsWith("/."))
			return root.substring(0, root.length() - 2);
		return root;
	}

	public static Path paths(String arg) {
		return (Path) PATHS.get(arg);
	}
}
