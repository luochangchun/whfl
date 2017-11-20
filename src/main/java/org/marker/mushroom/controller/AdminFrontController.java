package org.marker.mushroom.controller;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.UserLoginLog;
import org.marker.mushroom.beans.UserOrganization;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.dao.IMenuDao;
import org.marker.mushroom.dao.IPermissionDao;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.dao.IUserLoginLogDao;
import org.marker.mushroom.service.impl.UserOrganizationService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.marker.mushroom.utils.GeneratePass;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.qqwryip.IPLocation;
import org.marker.qqwryip.IPTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 后台管理主界面控制器
 * 
 * @author marker
 * 
 */
@Controller
@RequestMapping("/front/user")
public class AdminFrontController extends SupportController
{

	/** 日志记录器 */
	private final Logger logger = LoggerFactory.getLogger(AdminFrontController.class);

	@Autowired
	IUserDao userDao;
	@Autowired
	IUserLoginLogDao userLoginLogDao;
	@Autowired
	IMenuDao menuDao;
	@Autowired
	ServletContext application;

	@Autowired
	IPermissionDao permissionDao;

	@Autowired
	UserOrganizationService userOrganizationService;

	/** 构造方法初始化一些成员变量 */
	public AdminFrontController()
	{
		this.viewPath = "/admin/";
	}


	/** 保存用户 */
	@ResponseBody
	@RequestMapping("/register")
	public Object save(final UserOrganization userOrganization, @RequestParam(value = "foundingDate") final String foundingDate)
	{
		if (userOrganization.getType() == 3)
		{
			userOrganization.setFoundingTime(DateUtils.stringToDate(foundingDate, DateStyle.YYYY_MM_DD));
		}

		if (!userOrganizationService.checkExists(userOrganization.getAccount()))
		{
			return new ResultMessage(true, "此账号已存在，无法注册!");
		}

		if (userOrganizationService.createUser(userOrganization))
		{
			return new ResultMessage(true, "注册成功，请等待管理员审核通过后登陆!");
		}
		else
		{
			return new ResultMessage(false, "注册失败!");
		}
	}


	/**
	 * 登录系统 验证码不区分大小写
	 *
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(final HttpServletRequest request)
	{
		final String username = request.getParameter("name");
		final String password = request.getParameter("pass");
		final String device = request.getParameter("device");
		final HttpSession session = request.getSession();// 如果会话不存在也就创建

		int errorCode = 0;// 登录日志类型

		ResultMessage msg = null;

		String password2 = null;
		try
		{
			password2 = GeneratePass.encode(password);
			final User user = userDao.queryByNameAndPass(username, password2);
			if (user != null && user.getUuid() != null && !user.getUuid().isEmpty())
			{
				if (user.getStatus() == 1)
				{//启用
					userDao.updateLoginTime(user.getId());// 更新登录时间
					session.setAttribute(AppStatic.WEB_APP_SESSION_USER, user);

					msg = new ResultMessage(true, "登录成功!");
				}
				else
				{
					errorCode = 1;
					msg = new ResultMessage(false, "此账号审核中，无法登陆，请联系管理员!");
				}
			}
			else
			{
				errorCode = 1;
				msg = new ResultMessage(false, "用户名或者密码错误!");
			}
		}
		catch (final Exception e)
		{
			errorCode = 1;
			msg = new ResultMessage(false, "系统加密算法异常!");
			log.error("系统加密算法异常!", e);
		}

		// 获取真实IP地址
		final String ip = HttpUtils.getRemoteHost(request);
		// IP归属地获取工具
		final IPTool ipTool = IPTool.getInstance();

		// 记录日志信息
		final UserLoginLog log = new UserLoginLog();
		log.setUsername(username);
		log.setTime(new Date());
		log.setDevice(device);
		log.setInfo(msg.getMessage());
		log.setIp(ip);
		log.setErrorcode(errorCode);
		if (ip != null)
		{
			try
			{
				final IPLocation location = ipTool.getLocation(ip);
				if (location != null)
				{// 如果存在
					log.setArea(location.getCountry());
				}
			}
			catch (final Exception e)
			{
				logger.error("ip={} ", ip, e);
			}
		}

		userLoginLogDao.save(log);

		return msg;
	}

	/**
	 * 注销
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Object logout(final HttpServletRequest request, final HttpServletResponse response)
	{
		final HttpSession session = request.getSession(false);
		if (session != null)
		{
			session.invalidate();
			return new ResultMessage(true, "用户注销成功!");
		}
		return new ResultMessage(false, "用户注销失败!");

	}

	/**
	 * 登录系统 验证码不区分大小写
	 *
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public Object verification(@RequestParam(value = "randcode") final String randcode, final HttpServletRequest request)
	{
		final String randCode = randcode.toLowerCase();//验证码
		final HttpSession session = request.getSession();// 如果会话不存在也就创建
		final Object authCode = session.getAttribute(AppStatic.WEB_APP_AUTH_CODE);

		String sCode = "";

		if (authCode != null)
		{
			sCode = ((String) authCode).toLowerCase();
		}

		if (!sCode.equals(randCode))
		{// 验证码不匹配
			return new ResultMessage(false);
		}
		return new ResultMessage(true);
	}



}
