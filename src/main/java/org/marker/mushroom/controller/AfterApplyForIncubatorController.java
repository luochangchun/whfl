package org.marker.mushroom.controller;


import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.ApplyForIncubatorService;
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
 * 后台申请入驻孵化器列表展示
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/applyincubator")
public class AfterApplyForIncubatorController extends SupportController{

	@Autowired
	private ApplyForIncubatorService applyForIncubatorService;
	
	protected final static int SIZE = 10;
	
	public AfterApplyForIncubatorController() {
		this.viewPath="/admin/applyincubator/";
	}
	
	//展示已经入驻孵化器的公司
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView managementlist(Page page){
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", applyForIncubatorService.findByPage(page.getCurrentPageNo(), SIZE));
		return view;
	}
	
	//删除已经入驻公司
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage applydelete(@RequestParam("rid") String rid) {
		applyForIncubatorService.afterApplyForIncubatorDelete(rid);
		return new ResultMessage(true, "删除成功！");
	}
	
	//查看详情
	@RequestMapping("/tenancy/{id}/detail")
	public ModelAndView tenancylistview(@PathVariable("id") int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "tenancy/detail");
		view.addObject("tenancy", applyForIncubatorService.tenancyDetail(id));
		return view;
	}
}
