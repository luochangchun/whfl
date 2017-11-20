package org.marker.mushroom.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.ActivityService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.marker.mushroom.utils.ExcelUtils;
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
@RequestMapping("/admin/activity")
public class ActivityController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	private ActivityService activityService;
	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public ActivityController()
	{
		this.viewPath = "/admin/activity/";

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
		final Page page = activityService.find(currentPageNo, pageSize);

		return page;
	}

	@RequestMapping(value = "/exportExcel")
	public void exportExcel(final HttpServletResponse response)
	{
		OutputStream out = null;
		try
		{
			final String fileName = "活动信息管理_" + DateUtils.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");

			final String title = "活动信息管理";

			final String[] header = new String[]
			{ "活动标题", "姓名", "身份证号", "联系方式", "申请时间" };

			final List<Map<String, Object>> datas = activityService.find();

			final HSSFWorkbook wb = ExcelUtils.generateExcel(title, header, datas);
			out = response.getOutputStream();
			wb.write(out);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			logger.error("导出活动信息管理excel失败");
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
