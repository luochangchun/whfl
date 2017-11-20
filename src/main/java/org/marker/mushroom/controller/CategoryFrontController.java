package org.marker.mushroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.dao.IModelDao;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 分类管理
 * 
 * @author marker
 * 
 */
@Controller
@RequestMapping("/front/category")
public class CategoryFrontController extends SupportController
{

	/** 分类业务对象 */
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private IModelDao modelDao;

	/** 构造方法初始化一些成员变量 */
	public CategoryFrontController()
	{
		this.viewPath = "/themes/flcms/";
	}


	// 查找地图category
	@ResponseBody
	@RequestMapping(value = "/findMapCategory", method = RequestMethod.GET)
	public Map findMapCategory(@RequestParam("pid") final Integer pid)
	{
		final Map<String, Object> view = new HashMap<String, Object>();
		try
		{
			final List<Map<String, Object>> record = categoryService.findMapCategoryById(pid);
			view.put("category", record);
			view.put("code", 200);
		}
		catch (final Exception e)
		{
			view.put("code", 500);
			view.put("error", e.getMessage());
		}

		return view;
	}


}
