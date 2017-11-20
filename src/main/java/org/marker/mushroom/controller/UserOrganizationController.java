package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.UserOrganizationService;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/admin/userOrganization")
public class UserOrganizationController extends SupportController
{

	@Autowired
	private UserOrganizationService userOrganizationService;

	private static final Logger logger = LoggerFactory.getLogger(UserOrganizationController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();


	public UserOrganizationController()
	{
		this.viewPath = "/admin/userOrganization/";
	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 线下积分展示(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = userOrganizationService.find(currentPageNo, pageSize);

		return page;
	}

}
