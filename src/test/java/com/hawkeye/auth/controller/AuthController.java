package com.hawkeye.auth.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Map;
import com.hawkeye.auth.data.AuthStatus;
import com.hawkeye.entity.auth.SecUser;
import com.hawkeye.server.AbstractController;
import com.hawkeye.server.RuifiosEnv;

@Controller
@RequestMapping("/auth")
public class AuthController extends AbstractController
{

	@RequestMapping("/index")
	public ModelAndView loginIndex(HttpServletRequest request) 
	{
		ModelAndView view = new ModelAndView("forward:/login.jsp");
		
		logOk(request, "进入登陆页面");
		return view;
	}	

    @ResponseBody
	@RequestMapping("/login")
	public String loginIn(HttpServletRequest request, String username, String psw) 
	{
    	HttpSession session = request.getSession();
		String id = (String)session.getAttribute(AuthStatus.KEY_UID);
		String name = (String)session.getAttribute(AuthStatus.KEY_UNAME);
		
    	if(id==null||name==null||!name.equals(username))
		{
    		AuthStatus status = verifyUser(request, username, psw);
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
		logOk(request, "验证用户名和密码");
		return SUCCESS;
	}

	@RequestMapping("/verifyuser")
	public AuthStatus verifyUser(HttpServletRequest request, String userName, String pswEncode)
	{
		try 
		{
			List<SecUser> list = RuifiosEnv.dao.query("from "+SecUser.class.getSimpleName()+" where name = '"+userName+"'");
			if (list != null && list.size() > 0)
			{
				ServletContext application = request.getServletContext();
				SecUser u = list.get(0);
				if (u.getIslock() != 0)
				{
					boolean flag = u.getLockOpenTime() < System.currentTimeMillis();
					if (flag)
					{
						u.setIslock(0);
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
					session.setAttribute(AuthStatus.KEY_UID, u.getId());
					session.setAttribute(AuthStatus.KEY_UNAME, u.getName());
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

					/*LoginLimit limit = (LoginLimit) dao.query("from " + LoginLimit.class.getSimpleName()).get(0);
					if (limit != null && num > limit.getLimiterrorNum())
					{// 超过限制登录次数用户锁定
						u.setIslock(true);
						Long time = System.currentTimeMillis()
								+ limit.getLimitTime();
						u.setLockOpenTime(time);
						dao.update(u);
						logOk(request, userName + "账号锁定");
						auth.setLockEndTime(time);
						auth.setLocked(true);
						return auth;
					}*/
					auth.setLegalUser(false);
					return auth;
				}
			}
		} catch (Exception e) {
			logFail(request, "用户登录");
			e.printStackTrace();
		}
		
		return null;
	}

    @RequestMapping("/loginsuccess")
    public ModelAndView viewIndex(HttpServletRequest request)
    {
		logOk(request, "用户登陆成功");
    	return new ModelAndView("blank");
    }
    
    @RequestMapping("/logout")
    public ModelAndView logOut(HttpServletRequest request)
    {
		HttpSession session = request.getSession();
		session.removeAttribute(AuthStatus.KEY_UID);
		session.removeAttribute(AuthStatus.KEY_UNAME);
		
		logOk(request, "用户登出成功");
		return new ModelAndView("redirect:/login.jsp");
    }
    
    @ResponseBody
    @RequestMapping("/getMap")
    public String getMap()
    {
		// //地址：http://echarts.baidu.com/doc/example/map.html
		// GsonOption option = new GsonOption();
		// Map map = new Map("Map");
		// //map.mapLocation(new MapLocation(X.left, Y.top, 500));
		// map.selectedMode(SelectedMode.multiple);
		// map.itemStyle().normal().borderWidth(2)
		// .borderColor("lightgreen").color("orange")
		// .label().show(true);
		// map.itemStyle().emphasis()
		// .borderWidth(2).borderColor("#fff").color("#32cd32")
		// .label().show(true)
		// .textStyle().color("#fff");
		// Data data = new Data();
		// map.data(data);
		//
		// option.series(map);
		//
		// 地址：http://echarts.baidu.com/doc/example/map.html
		GsonOption option = new GsonOption();
		Map map = new Map("Map");
	
		map.setName("World Population (2010)");
		map.setMapType("world");
		map.itemStyle().emphasis().label().show(true);
		Data data = new Data();
		map.data(data);
	
		option.series(map);
		return option.toString();
    }

    @Override
    protected String getModule()
    {
    	return "auth";
    }
    
}
