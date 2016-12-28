package com.ruifios.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ruifios.auth.model.AuthAssistant;
import com.ruifios.system.engine.SysLogServiceEngine;
import com.ruifios.system.model.SystemLogs;

/**
 * 抽象 Controller 记录日志
 * @author ch
 * 
 * 	请求工具类 org.springframework.web.bind.ServletRequestUtils
 *  public static Integer getIntParameter(ServletRequest request, String name) --> 取请求参数的整数值：
 *  public static int getIntParameter(ServletRequest request, String name, int defaultVal) --> 单个值
 *  public static int[] getIntParameters(ServletRequest request, String name) --> 数组
 *  还有譬如long、float、double、boolean、String的相关处理方法。
 * 
 * 	org.springframework.web.context.support.WebApplicationContextUtils 工具类可以方便地获取 WebApplicationContext 对象。
 * 	WebApplicationContext wac = (WebApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
 *  WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
 *  
 *  org.springframework.web.util.WebUtils 是一个非常好用的工具类，它对很多 Servlet API 提供了易用的代理方法，降低了访问 Servlet API 的复杂度，可以将其看成是常用 Servlet API 方法的门面类。
 *  Cookie getCookie(HttpServletRequest request, String name)	获取 HttpServletRequest 中特定名字的 Cookie 对象。如果您需要创建 Cookie， Spring 也提供了一个方便的 CookieGenerator 工具类；
 *  Object getSessionAttribute(HttpServletRequest request, String name)	获取 HttpSession 特定属性名的对象，否则您必须通过 request.getHttpSession.getAttribute(name) 完成相同的操作；
 *  Object getRequiredSessionAttribute(HttpServletRequest request, String name)	和上一个方法类似，只不过强制要求 HttpSession 中拥有指定的属性，否则抛出异常；
 *  String getSessionId(HttpServletRequest request)	获取 Session ID 的值；
 *  void exposeRequestAttributes(ServletRequest request, Map attributes)	将 Map 元素添加到 ServletRequest 的属性列表中，当请求被导向（forward）到下一个处理程序时，这些请求属性就可以被访问到了；
 *  String getRealPath(ServletContext servletContext, String path)	获取相对路径对应文件系统的真实文件路径；
 *  File getTempDir(ServletContext servletContext)	获取 ServletContex 对应的临时文件地址，它以 File 对象的形式返回。
 *  
 *  WebAppRootListener 监听器配置,负责将 Web 应用根目录以 webAppRootKey 上下文参数指定的属性名添加到系统参数中
 *  <listener>
 *  	<listener-class>org.springframework.web.util.WebAppRootListener</listener-class>
 *  </listener> 
 *  
 *  Introspector 缓存清除监听器
 *  <listener> 
 *  	<listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>
 *  </listener>
 *  
 *  请求跟踪日志过滤器
 *  org.springframework.web.filter.CommonsRequestLoggingFilter
 *  org.springframework.web.filter.Log4jNestedDiagnosticContextFilter	该过滤器将请求的 URI 记录到 Common 日志中（如通过 Log4J 指定的日志文件）；
 *  org.springframework.web.filter.ServletContextRequestLoggingFilter	该过滤器将请求的 URI 记录到 ServletContext 日志中。
 *  
 *  中文乱码过滤器
 *  org.springframework.web.filter.CharacterEncodingFilter				编码方式(encoding: UTF-8) 强制进行编码转换(forceEncoding: true)
 *  
 *  Spring提供了FormattingConversionService和DefaultFormattingConversionService来完成对象的解析和格式化。Spring内置的几种Formatter SPI如下：
 *  名称					功能
 *  NumberFormatter		实现Number与String之间的解析与格式化
 *  CurrencyFormatter	实现Number与String之间的解析与格式化（带货币符号）
 *  PercentFormatter	实现Number与String之间的解析与格式化（带百分数符号）
 *  DateFormatter		实现Date与String之间的解析与格式化
 *  NumberFormatAnnotationFormatterFactory			@NumberFormat注解，实现Number与String之间的解析与格式化，可以通过指定style来指示要转换的格式（Style.Number/Style.Currency/Style.Percent）,当然也可以指定pattern（如pattern=“#.##”(保留2位小数) ），这样pattern指定的格式会覆盖掉Style指定的格式
 *  JodaDateTimeFormatAnnotationFormatterFactory	@DateTimeFormat注解，实现日期类型与String之间的解析与格式化这里的日期类型包括Date、Calendar、Long以及Joda的日期类型。必须在项目中添加Joda-Time包
 *  
 */
public abstract class AbstractController
{
	protected abstract String getModule();
	
	//protected static final SessionManager sessionManager = new SessionManager();
	
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
			return format.parse(str.replaceAll("/", "-"));
		} catch (ParseException e)
		{
			return null;
		}
	}
	
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
     * 获取本地资源文件内容
     * key，key中{0}、{1}等对应的值，默认值和Locale。
     * WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
     */
	public String getMessage(String key, Object[] args, HttpServletRequest request)
    {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());  
		return applicationContext.getMessage(key, args, locale);
    }
	
	/**
	 * 获取访问服务器的URL
	 * @param request
	 * @return
	 *  eg: http://127.0.0.1:8080/
	 */
	protected String getBaseUrl(HttpServletRequest request)
	{
		return getBaseAddr(request) + request.getContextPath();
	}
	
	/**
	 * 获取原始访问的URL
	 * @param request
	 * @return
	 *  eg: http://127.0.0.1:8080/login.jsp
	 */
	protected String getOriginalUrl(HttpServletRequest request)
	{
		return getBaseAddr(request) + request.getServletPath();
	}
	
	/**
	 * 获取服务器访问协议、域名（IP）、端口
	 * @param request
	 * @return
	 *  eg: http://127.0.0.1:8080
	 */
	private String getBaseAddr(HttpServletRequest request)
	{
		return request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort();
	}
	
	private void log(HttpServletRequest request, String module, String function, String status)
	{
		HttpSession session = request.getSession();
		
		String srcIp = request.getRemoteHost();
		
		String userId = (String)session.getAttribute(AuthAssistant.Key_UID);		
		String userName = (String)session.getAttribute(AuthAssistant.Key_UNAME);		
		
		Date date = new Date();
		SystemLogs log = new SystemLogs();
		log.setTimestamp(date.getTime());
		//log.setTimestamp_str(format.format(date));
		log.setUserid(userId);
		log.setUsername(userName);
		log.setSourceip(srcIp);
		log.setModule(module);
		log.setAction(function);
		log.setMsg(status);
		
		SysLogServiceEngine.getInstance().addRecord(log);
	}
}
