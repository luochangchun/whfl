package org.marker.mushroom.controller;

import org.marker.mushroom.beans.EnrolActivity;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.EnrolActivityService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 *申请活动
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/front/enrolactivity")
public class EnrolActivityController extends SupportController{
	
	@Autowired
	private EnrolActivityService enrolActivityService;

	public EnrolActivityController() {
		this.viewPath = "/front/enrolactivity/";
	}
	
	//申请入孵页面
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addview() {
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		return view;
	}
	
	//申请入孵操作
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage insert(@RequestBody EnrolActivity enrolActivity) throws Exception {
		enrolActivityService.createEnrolActivity(enrolActivity);
		return new ResultMessage(true, "申请成功!");
	}
}
