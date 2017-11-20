package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.MechanismService;
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
@RequestMapping("/admin/mechanism")
public class MechanismController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(MechanismController.class);

	@Autowired
	private MechanismService mechanismService;
	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public MechanismController()
	{
		this.viewPath = "/admin/mechanism/";

	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 志愿者个人列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = mechanismService.find(currentPageNo, pageSize);

		return page;
	}

	//	//编辑文章
	//	@RequestMapping("/detail")
	//	public ModelAndView detail(@RequestParam("id") final int id)
	//	{
	//		final ModelAndView view = new ModelAndView(this.viewPath + "detail");
	//		final Map<String, Object> donationMap = commonDao.findById(Donation.class, id);
	//		final List<Map<String, Object>> imgSrcList = new ArrayList<>();
	//		if (!donationMap.isEmpty() && donationMap.containsKey("imgSrc"))
	//		{
	//			final String[] imgSrcs = donationMap.get("imgSrc").toString().split(";");
	//			for (final String imgSrc : imgSrcs)
	//			{
	//				final Map<String, Object> tmpMap = new HashMap<>();
	//				tmpMap.put("imgSrc", imgSrc);
	//
	//				imgSrcList.add(tmpMap);
	//			}
	//		}
	//		view.addObject("donation", donationMap);
	//		view.addObject("imgSrcList", imgSrcList);
	//
	//		return view;
	//	}

}
