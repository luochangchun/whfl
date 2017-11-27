package org.marker.mushroom.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.markdown4j.Markdown4jProcessor;
import org.marker.mushroom.beans.Article;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.mushroom.dao.IArticleDao;
import org.marker.mushroom.service.impl.ArticleService;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.marker.mushroom.utils.ExcelUtils;
import org.marker.mushroom.utils.GenerateUUID;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.urlrewrite.URLRewriteEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController extends SupportController
{

	// 文章Dao
	@Autowired
	IArticleDao articleDao;

	@Autowired
	ArticleService articleService;

	@Autowired
	CategoryService categoryService;

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public ArticleController()
	{
		this.viewPath = "/admin/article/";

	}

	//发布文章
	@RequestMapping("/add")
	public ModelAndView add()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}

	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("article", commonDao.findById(Article.class, id));
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}


	/**
	 * 持久化文章操作
	 * 
	 * @param article
	 * @param cid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(final Article article, @RequestParam("cid") final int cid)
	{
		article.setTime(new Date());
		article.setCreateTime(new Date());
		article.setCid(cid);// 这里是因为不能注入bean里

		String msg = "";
		if (article.getStatus() == 1)
		{
			msg = "发布";
		}
		else
		{
			msg = "保存草稿";
		}
		if (article.getType() == 1)
		{// marker
			try
			{
				final String orginalText = article.getContent();
				article.setOrginal(orginalText);

				final String html = new Markdown4jProcessor().process(orginalText);
				article.setContent(html);

			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		if (commonDao.save(article))
		{
			return new ResultMessage(true, msg + "成功!");
		}
		else
		{
			return new ResultMessage(false, msg + "失败!");
		}
	}


	//保存
	@ResponseBody
	@RequestMapping("/update")
	public Object update(@ModelAttribute("article") final Article article)
	{
		article.setTime(new Date());

		if (article.getStatus() == 1 || article.getStatus() == 3)
		{
			article.setAuditTime(new Date());
		}

		if (article.getType() == 1)
		{// marker
			try
			{
				final String orginalText = article.getContent();
				article.setOrginal(orginalText);

				final String html = new Markdown4jProcessor().process(orginalText);
				article.setContent(html);

			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		if (articleDao.update(article))
		{
			//如果是文章发布就需要校验一下是否要加积分
			if (article.getStatus() == 1)
			{
				articleService.addPoints(article);

			}
			return new ResultMessage(true, "更新成功!");
		}
		else
		{
			return new ResultMessage(false, "更新失败!");
		}
	}

	//保存
	@ResponseBody
	@RequestMapping("/topArticle")
	public Object topArticle(@RequestParam("rid") final String rid, @RequestParam("level") final String level)
	{

		if (articleDao.topArticle(rid, level))
		{
			return new ResultMessage(true, "置顶成功!");
		}
		else
		{
			return new ResultMessage(false, "置顶失败!");
		}
	}

	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") final String rid)
	{
		final boolean status = commonDao.deleteByIds(Article.class, rid);
		if (status)
		{
			return new ResultMessage(true, "删除成功!");
		}
		else
		{
			return new ResultMessage(false, "删除失败!");
		}
	}


	//发布文章
	@RequestMapping("/list")
	public ModelAndView listview(final HttpServletRequest request)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}

	//审核文章列表
	@RequestMapping("/auditList")
	public ModelAndView auditList()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "audit/list");
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}


	//编辑文章
	@RequestMapping("/auditEdit")
	public ModelAndView auditEdit(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "audit/edit");
		view.addObject("article", commonDao.findById(Article.class, id));
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}

	//审核文章列表
	@RequestMapping("/auditView")
	public ModelAndView auditView()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "me/list");
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}


	//编辑文章
	@RequestMapping("/auditModify")
	public ModelAndView auditModify(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "me/edit");
		view.addObject("article", commonDao.findById(Article.class, id));
		view.addObject("categorys", categoryService.list("article"));
		return view;
	}

	/**
	 * 文章列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/myAuditList", method = RequestMethod.GET)
	@ResponseBody
	public Object myAuditList(final HttpServletRequest request, final ModelMap model,
			@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("cid") final String cid,
			@RequestParam("status") final String status, @RequestParam("keyword") final String keyword,
			@RequestParam("pageSize") final int pageSize)
	{
		final HttpSession session = request.getSession();
		final String usercategory = (String) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY);
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("usercategory", usercategory);

		final Page page = articleService.getMyAuditForPage(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData())
		{
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
	}

	/**
	 * 文章列表接口(REST)
	 * 
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object list(final HttpServletRequest request, final ModelMap model,
			@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("cid") final String cid,
			@RequestParam("status") final String status, @RequestParam("keyword") final String keyword,
			@RequestParam("pageSize") final int pageSize, @RequestParam(value = "audit", defaultValue = "") final String audit

	)
	{
		final HttpSession session = request.getSession();
		final String usercategory = (String) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY);
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("groupid", groupId);
		params.put("audit", audit);
		params.put("usercategory", usercategory);
		final Page page = articleService.find(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData())
		{
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
	}

	/**
	 * 持久化文章操作
	 * 
	 * @param imgSrc
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public Object upload(@RequestParam(value = "imgSrc", required = false) final String imgSrc, final HttpServletRequest request)
	{
		//上传文件
		if (!StringUtils.isEmpty(imgSrc))
		{
			try
			{
				final SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHH");
				//构建图片保存的目录
				String webAppPath = request.getSession().getServletContext().getRealPath("");
				//				System.out.println("webAppPath--old_" + webAppPath);
				final int index = webAppPath.lastIndexOf("/flcms") > 0 ? webAppPath.lastIndexOf("/flcms") : 0;
				webAppPath = webAppPath.substring(0, index);
				//				System.out.println("webAppPath--new_" + webAppPath);

				final String imgRealPathDir = webAppPath + "/" + syscfg.getRelativePath() + dateformat.format(new Date());
				//System.out.println("imgRealPathDir====" + imgRealPathDir);
				//根据真实路径创建目录
				final File imgSaveFile = new File(imgRealPathDir);
				//如果文件夹不存在则创建
				if (!imgSaveFile.exists() && !imgSaveFile.isDirectory())
				{
					imgSaveFile.mkdirs();
				}
				else
				{
					logger.error("//目录已存在");
				}

				//转换成图片的base64不要data:image/jpg;base64,这个头文件
				final String header = imgSrc.substring(0, imgSrc.indexOf(","));
				final String suffix = header.substring(header.indexOf("/") + 1, header.indexOf(";"));

				//拿到输出流，同时重命名上传的文件
				final String fileName = "/" + new Date().getTime() + "_" + GenerateUUID.getUUID() + "." + suffix;
				final FileOutputStream os = new FileOutputStream(imgRealPathDir + fileName);

				final int delLength = imgSrc.indexOf(',') + 1;
				final String imgBase64 = imgSrc.substring(delLength, imgSrc.length() - delLength);
				byte[] fileByteArr = imgBase64.getBytes("UTF-8");
				//拿到上传文件的输入流
				fileByteArr = Base64.decodeBase64(fileByteArr);
				final ByteArrayInputStream bai = new ByteArrayInputStream(fileByteArr);
				final InputStream in = bai;

				//以写字节的方式写文件
				int b = 0;
				while ((b = in.read()) != -1)
				{
					os.write(b);
				}
				os.flush();
				os.close();
				in.close();
				final String serverRealPath = syscfg.getRelativePath() + dateformat.format(new Date()) + fileName;

				return new ResultMessage(true, "上传成功!", serverRealPath);
			}
			catch (final Exception e)
			{
				logger.error("ArticleController method save error:" + e.getMessage());
				return new ResultMessage(false, "上传失败!");
			}

		}
		else
		{
			logger.error("ArticleController method save error: no file upload!");
			return new ResultMessage(false, "上传文件不存在!");
		}

	}

	@RequestMapping("statistics/list")
	public ModelAndView statisticsList(final HttpServletRequest request)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "statistics");

		return view;
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	@ResponseBody
	public Object statistics(final HttpServletRequest request,
			@RequestParam(value = "beginDate", defaultValue = "") final String beginDate,
			@RequestParam(value = "endDate", defaultValue = "") final String endDate,
			@RequestParam(value = "userName", defaultValue = "") final String userName)
	{
		final HttpSession session = request.getSession();
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		return articleService.findListArticleStatistics(userId, groupId, userName, beginDate, endDate);
	}

	@RequestMapping(value = "/exportExcel")
	public void exportExcel(final HttpServletResponse response, final HttpServletRequest request)
	{
		final HttpSession session = request.getSession();
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		OutputStream out = null;
		try
		{
			final String fileName = "文章统计_" + DateUtils.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");

			final String title = "文章统计";

			final String[] header = new String[]
			{ "用户名称", "待发布", "已发布", "待审核", "已驳回" };

			final List<Map<String, Object>> datas = articleService.findListArticleStatistics(userId, groupId);

			final HSSFWorkbook wb = ExcelUtils.generateExcel(title, header, datas);
			out = response.getOutputStream();
			wb.write(out);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			logger.error("导出文章统计excel失败");
		}
		finally
		{
			try
			{
				out.close();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
				logger.error("输出流关闭失败");
			}
		}

	}
}
