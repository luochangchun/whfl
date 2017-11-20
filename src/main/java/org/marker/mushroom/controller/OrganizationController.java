package org.marker.mushroom.controller;

import java.util.Map;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.DictionariesService;
import org.marker.mushroom.service.impl.OrganizationService;
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
 * 社会组织
 * 
 * @author marker
 */
@Controller
@RequestMapping("/admin/organization")
public class OrganizationController extends SupportController
{

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private DictionariesService dictionariesService;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();


	public OrganizationController()
	{
		this.viewPath = "/admin/organization/";
	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 社会组织列表(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = organizationService.find(currentPageNo, pageSize);

		return page;
	}

	/**
	 * 线下积分展示(REST)
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "detail");
		final Map<String, Object> organizationMap = organizationService.find(id);
		dictionariesService.convertDictionaries(organizationMap, "kingdom", "organization");
		dictionariesService.convertDictionaries(organizationMap, "demand", "organization");

		view.addObject("organization", organizationMap);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public Object audit(@RequestParam(value = "uid") final Integer uid, @RequestParam(value = "status") final Integer status)
	{
		final User user = new User();
		user.setId(uid);
		user.setStatus(status);

		if (commonDao.update(user))
		{
			String tips = "操作成功！";
			if (status == 1)
			{
				tips = "激活成功！";
			}
			else if (status == 0)
			{
				tips = "冻结成功！";
			}
			return new ResultMessage(true, tips);
		}
		else
		{
			return new ResultMessage(false, "操作失败!");
		}
	}

}
