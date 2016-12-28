package com.hawkeye.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hawkeye.auth.data.AuthStatus;

/**
 * 实现HandlerInterceptor接口
 * @author user01
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter //implements HandlerInterceptor
{

    public static String HEADER_NAME_SERVER_HTTP_PROXY_CONTEXT = "HTTP-SERVER-PROXY-CONTEXT";
    
	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
	 * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		try {
			String path = request.getContextPath();
			String url = request.getRequestURI().replace(path, ""); 
			
			//判断是否为资源文件
			if(url.startsWith("/dist")||url.endsWith(".js")||url.endsWith(".css")||url.endsWith(".png") ||url.endsWith(".html") ||url.endsWith(".ico"))
			{
				return true;
			}
			
//		if("fetch-validate-confs".equals(request.getParameter("validate-type")) && handler instanceof HandlerMethod){//加载校验规则
//			HandlerMethod handlerMethod = (HandlerMethod) handler;  
//		    System.err.println("######################################"+handlerMethod.getMethod()); 
//		    Method method = handlerMethod.getMethod();
//		}
			
//		//判断证书是否有效
//		LicenseInfo info = LManager.getInfo();
//		if(info != null && info.isAuth())
//		{//证书有效
				
				//判断用户是否已登录
				HttpSession session = request.getSession();
				String id = (String)session.getAttribute(AuthStatus.KEY_UID);
				String name = (String)session.getAttribute(AuthStatus.KEY_UNAME);
				
				if(id==null||name==null)
				{//用户未登录
					//进入系统、登陆页面、用户登陆/取证
					if(url.equals("/"))
					{
						response.sendRedirect(path+"/login.jsp");
						return false;
					}
					else if(!url.equals("/auth/index") && !url.equals("/auth/login"))
					{
						// 进入登陆页面
						request.getRequestDispatcher("/login.jsp").forward(request, response); 
						return false;
					}
				}
				else if(url.equals("/login.jsp"))
				{
					response.sendRedirect(path+"/auth/loginsuccess");
					return false;
				}
//		}
//		else if(!"/auth/license".equals(url) && !"/auth/isRegistered".equals(url) && !"/config/uploadLicens".equals(url))
//		{//证书无效且不进入证书导入页面
//			response.sendRedirect(path+"/auth/license");
//			return false;
//		}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	* 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
	* 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
	* 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
	* 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	* 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception
	{
		String proxyContext = request.getHeader(HEADER_NAME_SERVER_HTTP_PROXY_CONTEXT);
		if (proxyContext != null)
			request.setAttribute("base", proxyContext);
	}
	
	/**
	* 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
	* 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception
	{
		
	}
	
}
