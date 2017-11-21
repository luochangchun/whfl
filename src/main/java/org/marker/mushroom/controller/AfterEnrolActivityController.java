package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.EnrolActivityService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 后台申请人员展示
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/enrolactivity")
public class AfterEnrolActivityController extends SupportController{
	
	@Autowired
	private EnrolActivityService enrolActivityService;
	
	protected final static int SIZE = 10;

	public AfterEnrolActivityController() {
		this.viewPath="/admin/enrolactivity/";
	}
	
	//展示已经申请活动的人员
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView managementlist(Page page){
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", enrolActivityService.findByPage(page.getCurrentPageNo(), SIZE));
		return view;
	}
	
	//删除已经入驻公司
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage applydelete(@RequestParam("rid") String rid) {
		enrolActivityService.EnrolActivityDelete(rid);
		return new ResultMessage(true, "删除成功！");
	}
	
}
