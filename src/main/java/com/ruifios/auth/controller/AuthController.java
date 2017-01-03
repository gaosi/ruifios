package com.ruifios.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruifios.auth.model.AuthAssistant;
import com.ruifios.auth.model.AuthStatus;
import com.ruifios.auth.model.User;
import com.ruifios.commons.Pager;
import com.ruifios.sales.service.SalesService;
import com.ruifios.server.AbstractController;
import com.ruifios.server.RuifiosEnv;

@Controller
@RequestMapping("/auth")
public class AuthController extends AbstractController
{
	
	@Autowired
	@Qualifier(SalesService.IOC_NAME)
	private SalesService salesservice;
	
	/**
	 * 进入登陆页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView loginIndex(HttpServletRequest request) 
	{
		ModelAndView view = new ModelAndView("redirect:/index.jsp");
		view.addObject("errorMsg", request.getParameter("errorMsg"));
		logOk(request, "进入登录页面");
		return view;
	}	

	/**
	 * 登陆验证用户名和密码
	 * @param request
	 * @param username
	 * @param psw
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public String loginIn(HttpServletRequest request, String username, String pswd) 
	{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute(AuthAssistant.Key_UID);
		String name = (String)session.getAttribute(AuthAssistant.Key_UNAME);
		
		if(id==null||name==null||!name.equals(username))
		{
			AuthStatus status = verifyUser(request, username, pswd);
			if(status==null)
			{
				return "用户不存在！";
			}
			else if(!status.isLegalUser())
			{
				return "用户名或密码有误！";
			}
			else if(status.isLocked())
			{
				return "用户已锁定，"+ (status.getLockEndTime() - System.currentTimeMillis() ) / 1000 / 60  +"分钟后自动解锁或联系管理员解锁";
			}
		}
		
		return "success";
	}

	/**
	 * 用户名密码正确，进入系统
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginsuccess")
	public ModelAndView getIndexPageByUserName(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String uri = (String)session.getAttribute(AuthAssistant.Key_URI);
		if(StringUtils.isEmpty(uri))
		{
			uri = "/sales/index";
		}
			
		return new ModelAndView("redirect:"+uri);
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AuthAssistant.Key_UID);
		session.removeAttribute(AuthAssistant.Key_UNAME);
		return new ModelAndView("redirect:/auth/index");
	}

	@RequestMapping("/verifyUser")
	public AuthStatus verifyUser(HttpServletRequest request, String userName, String pswEncode)
	{
		List<User> list = RuifiosEnv.dao.query("from " + User.class.getSimpleName() + " where name='" + userName + "'");
		if (list != null && list.size() > 0)
		{
			HttpSession application = request.getSession();
			User u = list.get(0);
			if (u.getIslock() != null && u.getIslock())
			{
				boolean flag = u.getLockOpenTime() < System.currentTimeMillis();
				if (flag)
				{
					u.setIslock(false);
					u.setLockOpenTime(0l);
					RuifiosEnv.dao.update(u);
					logOk(request, userName + "账号解锁");
					application.removeAttribute(userName);
				}
				else
				{
					AuthStatus as = new AuthStatus();
					String lockDate = format.format(new Date(u.getLockOpenTime()));
							
					as.setUserName(lockDate);
					as.setRealName("账号已锁定");
					as.setLocked(true);
					as.setLockEndTime(u.getLockOpenTime());
					return as;
				}
			}

			if (BCrypt.checkpw(pswEncode, u.getPassword()))
			{
				HttpSession session = request.getSession();
				session.setAttribute(AuthAssistant.Key_UID, u.getId());
				session.setAttribute(AuthAssistant.Key_UNAME, u.getName());
				logOk(request, "用户登录");
				
				u.setLastLoginTime(System.currentTimeMillis());
				RuifiosEnv.dao.update(u);// 修改用户的最近登录时间
				
				//String srcIp = request.getRemoteHost();
				//sessionManager.refreshSession(srcIp, userName, "");

				return new AuthStatus(u);
			}
			else
			{
				AuthStatus auth = new AuthStatus();
				Integer num = (Integer) application.getAttribute(userName);
				if (num == null)
				{
					num = 0;
				}
				application.setAttribute(userName, ++num);
				logFail(request, userName + "密码错误：" + num);

				//LoginLimit limit = (LoginLimit) dao.query("from " + LoginLimit.class.getSimpleName()).get(0);
				if (num > 3)
				{// 超过限制登录次数用户锁定
					u.setIslock(true);
					Long time = System.currentTimeMillis() + 30*DateUtils.MILLIS_PER_MINUTE;
					u.setLockOpenTime(time);
					RuifiosEnv.dao.update(u);
					logOk(request, userName + "账号锁定");
					auth.setLockEndTime(time);
					auth.setLocked(true);
					return auth;
				}
				auth.setLegalUser(false);
				return auth;
			}
		}
		logFail(request, "用户登录");
		return null;
	}

	/**
	 * 获取用户信息
	 * @param request
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getuserlist")
	public Pager<AuthStatus> getUserList(HttpServletRequest request, Pager<AuthStatus> pager)
	{
		try 
		{
			String condition = ""; 
			//if(!"".equals(condition)){
				List<User> users = RuifiosEnv.dao.query("from " + User.class.getSimpleName()+condition, pager.getCurrentPage(), pager.getPageSize());
				long records = RuifiosEnv.dao.getCount(User.class, condition);
				List<AuthStatus> data = new ArrayList<AuthStatus>();
				for(User user: users){
					AuthStatus as = new AuthStatus(user);
					double consum = salesservice.getSalesSum(user.getName());
					as.setUserPhone(user.getPhone());
					as.setConsum(consum);
					data.add(as);
				}
				
				pager.setRecords(records);
				pager.setData(data);
			//}
				
			
			logOk(request, "获取用户信息");
			return pager;
		} catch (Exception e) {
			logFail(request, "获取用户信息");
			return pager;
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param user
	 * @param oldPswEncode
	 * @param newPswEncode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changePsw")
	public Boolean changePsw(HttpServletRequest request, String user, String oldPswEncode,
			String newPswEncode)
	{
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute(AuthAssistant.Key_UID);
		
		User u = RuifiosEnv.dao.queryById(User.class, id);
		if(BCrypt.checkpw(oldPswEncode, u.getPassword()))
		{
			String hashed = BCrypt.hashpw(newPswEncode, BCrypt.gensalt());
			//修改密码
			u.setPassword(hashed);
			RuifiosEnv.dao.update(u);
			logOk(request, "修改密码");
			return true;
		}
		else
		{
			//密码修改失败
			logFail(request, "修改密码");
			return false;
		}
	}

	/**
	 * 根据登录用户ID查询用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLoginUser")
	public User getLoginUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute(AuthAssistant.Key_UID);
		
		try
		{
			User user = RuifiosEnv.dao.queryById(User.class, id);
			logOk(request, "获取当前用户信息");
			return user;
		}
		catch (Exception e) 
		{
			logFail(request, "获取当前用户信息");
			return null;
		}
	}
	
	/**
	 * 添加或修改用户
	 * @param resq
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrupdateUser")
	public String addOrupdateUser(HttpServletRequest resq, User user)
	{
		try 
		{
			// 更改用户
			if(user.getId() != null && !"".equals(user.getId())){
				User bean = RuifiosEnv.dao.queryById(User.class, user.getId());
				user.setPassword(bean.getPassword());
				user.setCreatedTime(bean.getCreatedTime());
				user.setTimeout(bean.getTimeout());
				user.setLastLoginTime(bean.getLastLoginTime());
				user.setIslock(bean.getIslock());
				user.setLockOpenTime(bean.getLockOpenTime());
				//user.setIpRange(bean.getIpRange());
				RuifiosEnv.dao.update(user);
				logOk(resq, "修改用户信息");
			}
			else
			{
				RuifiosEnv.dao.insert(user);
				logOk(resq, "添加用户信息");
			}
			
			return "success";
		} catch (Exception e) {
			logFail(resq, "配置用户");
			return "error";
		}
	}
	
	/**
	 * 登陆超时时间
	 */
	public static final int TimeOut = 30*60*1000;
	
	@RequestMapping("/nop")
	public Boolean nop(HttpServletRequest request)
	{
		// HttpSession session = request.getSession();
		
		// String srcIp = request.getRemoteHost();
		
		// String userId = (String)session.getAttribute(AuthAssistant.Key_UID);		
		// String userName = (String)session.getAttribute(AuthAssistant.Key_UNAME);	
		
		//long t = sessionManager.getLastInvokeTime(srcIp, userName);
		//if(System.currentTimeMillis()-t>TimeOut) {
		//	return false;
		//}
		return true;
	}
	
	@RequestMapping("/license")
	public ModelAndView importLicense(HttpServletRequest request) {
		logOk(request, "进入导入证书页面");
		return new ModelAndView("state/license");
	}

	@Override
	protected String getModule()
	{
		return "权限";
	}
	
}
