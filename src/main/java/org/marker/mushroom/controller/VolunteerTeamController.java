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
import org.marker.mushroom.service.impl.DictionariesService;
import org.marker.mushroom.service.impl.VolunteerTeamService;
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
@RequestMapping("/admin/volunteer/team")
public class VolunteerTeamController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(VolunteerTeamController.class);

	@Autowired
	private VolunteerTeamService volunteerTeamService;

	@Autowired
	private DictionariesService dictionariesService;
	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public VolunteerTeamController()
	{
		this.viewPath = "/admin/volunteer/team/";

	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 志愿者团体列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = volunteerTeamService.find(currentPageNo, pageSize);
		final List<Map<String, Object>> datas = page.getData();
		if (datas != null && datas.size() > 0)
		{
			for (final Map<String, Object> map : datas)
			{
				dictionariesService.convertDictionaries(map, "description", "volunteer_team");
			}
		}
		return page;
	}

	@RequestMapping(value = "/exportExcel")
	public void exportExcel(final HttpServletResponse response)
	{
		OutputStream out = null;
		try
		{
			final String fileName = "志愿者申请（团队）_" + DateUtils.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");

			final String title = "志愿者申请（团队）";

			final String[] header = new String[]
			{ "团队名称", "代表人身份证号", "团队人数", "联系方式", "团队专长", "所属区", "申请时间", "服务方向" };

			final List<Map<String, Object>> datas = volunteerTeamService.find();
			if (datas != null && datas.size() > 0)
			{
				for (final Map<String, Object> map : datas)
				{
					dictionariesService.convertDictionaries(map, "description", "volunteer_team");
				}
			}
			final HSSFWorkbook wb = ExcelUtils.generateExcel(title, header, datas);
			out = response.getOutputStream();
			wb.write(out);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			logger.error("导出志愿者申请（团队）excel失败");
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
