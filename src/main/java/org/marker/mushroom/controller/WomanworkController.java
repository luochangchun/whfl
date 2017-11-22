package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.Womanwork;
import org.marker.mushroom.service.impl.WomanworkService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 女性创业申请表单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/front/womanwork")
public class WomanworkController extends SupportController{
	
	@Autowired
	private WomanworkService womanworkService;

	public WomanworkController() {
		this.viewPath="/front/womanwork/";
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
	public ResultMessage insert(@RequestBody Womanwork womanwork) throws Exception {
		ResultMessage createWomanwork = womanworkService.createWomanwork(womanwork);
		return createWomanwork;
	}
}
