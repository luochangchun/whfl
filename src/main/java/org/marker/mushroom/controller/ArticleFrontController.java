package org.marker.mushroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.dao.IArticleDao;
import org.marker.mushroom.service.impl.ArticleService;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/front/article")
public class ArticleFrontController extends SupportController
{

	// 文章Dao
	@Autowired
	IArticleDao articleDao;

	@Autowired
	ArticleService articleService;

	@Autowired
	CategoryService categoryService;

	private static final Logger logger = LoggerFactory.getLogger(ArticleFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public ArticleFrontController()
	{
		this.viewPath = "/front/article/";

	}

	// 查找地图article
	@ResponseBody
	@RequestMapping(value = "/findMapArticle", method = RequestMethod.GET)
	public Map findMapArticle(@RequestParam("cid") final Integer cid)
	{
		final Map<String, Object> view = new HashMap<String, Object>();
		try
		{
			final List<Map<String, Object>> record = articleService.findMapArticleByCid(cid);
			view.put("article", record);
			view.put("code", 200);
		}
		catch (final Exception e)
		{
			view.put("code", 500);
			view.put("error", e.getMessage());
		}

		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/totalAccess", method = RequestMethod.GET)
	public Map totalAccess()
	{
		final Map<String, Object> view = new HashMap<>();
		try
		{
			view.put("totalAccess", articleService.totalAccess());
			view.put("code", 200);
		}
		catch (final Exception e)
		{
			view.put("code", 500);
			view.put("error", e.getMessage());
		}

		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/todayAccess", method = RequestMethod.GET)
	public Map todayAccess()
	{
		final Map<String, Object> view = new HashMap<>();
		try
		{
			view.put("todayAccess", articleService.todayAccess());
			view.put("code", 200);
		}
		catch (final Exception e)
		{
			view.put("code", 500);
			view.put("error", e.getMessage());
		}

		return view;
	}
	//	// 查找地图文章详情
	//	@ResponseBody
	//	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	//	public Map detail(@RequestParam("id") final Integer id, final HttpServletRequest request)
	//	{
	//		final Map<String, Object> view = new HashMap<String, Object>();
	//		try
	//		{
	//			final Article article = articleService.findArticleById(id);
	//
	//			request.getSession().setAttribute(AppStatic.APP_SESSION_CHANNEL_LAST_PARAM_NAME, id);
	//
	//			view.put("article", article);
	//			view.put("code", 200);
	//		}
	//		catch (final Exception e)
	//		{
	//			view.put("code", 500);
	//			view.put("error", e.getMessage());
	//		}
	//
	//		return view;
	//	}


}
