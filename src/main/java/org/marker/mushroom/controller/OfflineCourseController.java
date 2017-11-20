package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.OfflineCourseService;
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
@RequestMapping("/admin/offlineCourse")
public class OfflineCourseController extends SupportController
{

	@Autowired
	private OfflineCourseService offlineCourseService;

	@Autowired
	private UserOrganizationService userOrganizationService;

	private static final Logger logger = LoggerFactory.getLogger(OfflineCourseController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();


	public OfflineCourseController()
	{
		this.viewPath = "/admin/offlineCourse/";
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
		final Page page = offlineCourseService.find(currentPageNo, pageSize);

		return page;
	}

	@ResponseBody
	@RequestMapping(value = "/addCourse", method = RequestMethod.GET)
	public Object addCourse(@RequestParam(value = "ids") final String ids,
			@RequestParam(value = "courseName") final String courseName,
			@RequestParam(value = "courseIntegral") final Integer courseIntegral,
			@RequestParam(value = "userId") final Integer userId)
	{

		if (offlineCourseService.addCourse(ids, courseName, courseIntegral, userId))
		{
			return new ResultMessage(true, "线下课程录入成功!");
		}
		else
		{
			return new ResultMessage(false, "线下课程录入失败!");
		}
	}
}
