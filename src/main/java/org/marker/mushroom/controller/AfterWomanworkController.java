package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.WomanworkService;
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
 * 后台女创查看
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/womanwork")
public class AfterWomanworkController extends SupportController{

	@Autowired
	private WomanworkService womanworkService;
	
	protected final static int SIZE = 10;
	
	public AfterWomanworkController() {
		this.viewPath="/admin/womanwork/";
	}
	
	//展示已经申请活动的人员
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView managementlist(Page page){
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", womanworkService.findByPage(page.getCurrentPageNo(), SIZE));
		return view;
	}
	
	//删除已经入驻公司
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage applydelete(@RequestParam("rid") String rid) {
		boolean womanworkDelete = womanworkService.WomanworkDelete(rid);
		if(womanworkDelete==true){
			return new ResultMessage(true, "删除成功！");
		}else{
			return new ResultMessage(false, "删除失败！");
		}
	}
	
	//查看详情
	@RequestMapping("/tenancy/{id}/detail")
	public ModelAndView tenancylistview(@PathVariable("id") int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "tenancy/detail");
		view.addObject("tenancy", womanworkService.tenancyDetail(id));
		return view;
	}
}
