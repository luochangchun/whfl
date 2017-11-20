package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Mechanism;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/front/mechanism")
public class MechanismFrontController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(MechanismFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public MechanismFrontController()
	{

	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(final Mechanism mechanism, @RequestParam(value = "foundingDate") final String foundingDate)
	{

		mechanism.setFoundingTime(DateUtils.stringToDate(foundingDate, DateStyle.YYYY_MM_DD));

		if (commonDao.save(mechanism))
		{
			return new ResultMessage(true, "提交成功!");
		}
		else
		{
			return new ResultMessage(false, "提交失败!");
		}
	}
}
