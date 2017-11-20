package org.marker.mushroom.controller;

import java.util.Date;

import org.marker.mushroom.beans.Application_site;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.ApplicationSiteService;
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
@RequestMapping("/admin/applicationSite")
public class ApplicationSiteController extends SupportController
{

	@Autowired
	private ApplicationSiteService applicationSiteService;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationSiteController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();


	public ApplicationSiteController()
	{
		this.viewPath = "/admin/applicationSite/";
	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 志愿者个人列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = applicationSiteService.find(currentPageNo, pageSize);

		return page;
	}

	@ResponseBody
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public Object audit(@RequestParam(value = "aid") final Integer aid, @RequestParam(value = "status") final Integer status,
			@RequestParam(value = "audit") final Integer audit)
	{
		final Application_site application_site = new Application_site();
		application_site.setId(aid);
		application_site.setStatus(status);
		application_site.setAudit(audit);
		application_site.setAuditDate(new Date());

		if (commonDao.update(application_site))
		{
			return new ResultMessage(true, "更新成功!");
		}
		else
		{
			return new ResultMessage(false, "更新失败!");
		}
	}
}
