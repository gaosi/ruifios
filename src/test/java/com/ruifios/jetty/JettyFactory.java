package com.ruifios.jetty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyFactory {

	private static final String DEFAULT_WEBAPP_PATH = "WebContent";
	
	private static final String WINDOWS_WEBDEFAULT_PATH = "jetty/webdefault-windows.xml";

	private static final String TOMCAT_KEYDEFAULT_PATH = JettyFactory.class.getResource("/")+"tomcat.keystore";
	
	/**
	 * 创建用于开发运行调试的Jetty Server, 以src/main/webapp为Web应用目录
	 */
	public static Server createServerInSource(int port, String contextPath) {
		Server server = new Server();
		// 设置在JVM退出时关闭Jetty的钩子。
		server.setStopAtShutdown(true);

		//SelectChannelConnector connector = new SelectChannelConnector();
		HttpConfiguration config = new HttpConfiguration();
		ServerConnector connector = new ServerConnector(server,new HttpConnectionFactory(config));
		connector.setReuseAddress(true);
		connector.setIdleTimeout(30000);
		connector.setPort(port);
		// 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
		// 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
		webContext.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);
		server.setHandler(webContext);

		return server;
	}

	/**
	 * 添加HTTPS连接
	 */
	public static void addConnector(Server server, int port) 
	{
		SslContextFactory sslContextFactory = new SslContextFactory();
		
		sslContextFactory.setKeyStorePath(TOMCAT_KEYDEFAULT_PATH);
		sslContextFactory.setKeyStorePassword("ruifioscloud");
		sslContextFactory.setKeyManagerPassword("ruifioscloud");
		 
		HttpConfiguration https_config = new HttpConfiguration();
		https_config.setSecureScheme("https");
		https_config.setSecurePort(port);
		https_config.setOutputBufferSize(32768);
		https_config.addCustomizer(new SecureRequestCustomizer());
		
		ServerConnector httpsConnector = new ServerConnector(server,
		        new SslConnectionFactory(sslContextFactory,"http/1.1"),
		        new HttpConnectionFactory(https_config));
		httpsConnector.setPort(port);
		httpsConnector.setIdleTimeout(500000);
		
		server.addConnector(httpsConnector);
	}
	
	/**
	 * 设置除jstl-*.jar外其他含tld文件的jar包的名称
	 * jar名称不需要版本号，如sitemesh, shiro-web
	 */
	public static void setTldJarNames(Server server, String... jarNames) {
		WebAppContext context = (WebAppContext) server.getHandler();
		List<String> jarNameExprssions = new ArrayList<>();
		jarNameExprssions.add(".*/jstl-[^/]*\\.jar$");
		jarNameExprssions.add(".*/.*taglibs[^/]*\\.jar$");
		for (String jarName : jarNames) {
			jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
		}

		context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				StringUtils.join(jarNameExprssions, '|'));

	}

	/**
	 * 快速重新启动application，重载target/classes与target/test-classes
	 */
	public static void reloadContext(Server server) throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();

		context.stop();

		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);

		context.start();

		System.err.println("[INFO] Application reloaded");
	}
}