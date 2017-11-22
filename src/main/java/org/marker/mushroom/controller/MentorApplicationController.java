package org.marker.mushroom.controller;

import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.MentorApplicationService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 导师申请表单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/front/mentorapplication")
public class MentorApplicationController extends SupportController{

	@Autowired
	private MentorApplicationService mentorApplicationService;
	
	public MentorApplicationController() {
		this.viewPath="/front/mentorapplication/";
	}
	
	//申请页面
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addview() {
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		return view;
	}
	
	//申请操作
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage insert(@RequestBody MentorApplication mentorApplication) throws Exception {
		 ResultMessage createMentorApplication = mentorApplicationService.createMentorApplication(mentorApplication);
		return createMentorApplication;
	}
}
