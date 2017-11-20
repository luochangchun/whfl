package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Application_site;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.ApplicationSiteService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/front/applicationSite")
public class ApplicationSiteFrontController extends SupportController
{

	@Autowired
	private ApplicationSiteService applicationSiteService;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationSiteFrontController.class);

	public ApplicationSiteFrontController() {
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(final Application_site application_site, @RequestParam(value = "makeTime") final String makeTime) {
		application_site.setTime(new Date());
		application_site.setCreateTime(new Date());
		application_site.setMakeDate(DateUtils.stringToDate(makeTime, DateStyle.YYYY_MM_DD));

		if (commonDao.save(application_site)) {
			return new ResultMessage(true, "提交成功!");
		} else {
			return new ResultMessage(false, "提交失败!");
		}
	}

	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public Object init() {
		final List<Map<String, Object>> list = applicationSiteService.find();
		return applicationSiteService.init(list);
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
		return applicationSiteService.findInUse();
	}
}
