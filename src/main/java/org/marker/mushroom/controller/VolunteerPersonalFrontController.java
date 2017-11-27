package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.Volunteer_Personal;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/front/volunteer/personal")
public class VolunteerPersonalFrontController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(VolunteerPersonalFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public VolunteerPersonalFrontController()
	{

	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(final Volunteer_Personal volunteer_personal)
	{
		if (commonDao.save(volunteer_personal))
		{
			return new ResultMessage(true, "提交成功!");
		}
		else
		{
			return new ResultMessage(false, "提交失败!");
		}
	}
}