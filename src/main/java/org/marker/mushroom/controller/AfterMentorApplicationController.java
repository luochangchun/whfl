package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Category;
import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.impl.MentorApplicationService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 导师申后台查看
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/mentorapplication")
public class AfterMentorApplicationController extends SupportController{

	@Autowired
	private MentorApplicationService mentorApplicationService;
	
	protected final static int SIZE = 10;
	
	public AfterMentorApplicationController() {
		this.viewPath="/admin/mentorapplication/";
	}
	
	//展示已经申请活动的人员
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView managementlist(Page page){
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", mentorApplicationService.findByPage(page.getCurrentPageNo(), SIZE));
		return view;
	}
	
	//删除已经入驻公司
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage applydelete(@RequestParam("rid") String rid) {
		boolean message = mentorApplicationService.MentorApplicationDelete(rid);
		if(message==true){
			return new ResultMessage(true, "删除成功！");
		}else{
			return new ResultMessage(false, "删除失败！");
		}
		
	}
	
	//查看详情
	@RequestMapping("/tenancy/{id}/detail")
	public ModelAndView tenancylistview(@PathVariable("id") int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "tenancy/detail");
		view.addObject("tenancy", mentorApplicationService.tenancyDetail(id));
		return view;
	}
	
}
