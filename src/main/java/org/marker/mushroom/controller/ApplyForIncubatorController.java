package org.marker.mushroom.controller;


import org.marker.mushroom.beans.ApplyForIncubator;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.ApplyForIncubatorService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 申请入驻孵化器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/front/applyincubator")
public class ApplyForIncubatorController extends SupportController{

	
	@Autowired
	private ApplyForIncubatorService applyForIncubatorService;
	
	public ApplyForIncubatorController() {
		this.viewPath = "/front/applyincubator/";
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
	public ResultMessage insert(@RequestBody ApplyForIncubator applyForIncubator) throws Exception {
		applyForIncubatorService.createApplyIncubator(applyForIncubator);
		return new ResultMessage(true, "申请成功！");
	}
}
