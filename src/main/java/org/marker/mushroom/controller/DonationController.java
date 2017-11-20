package org.marker.mushroom.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.marker.mushroom.beans.Donation;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.DonationService;
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
 * 家风博物馆管理
 * 
 * @author dd
 */
@Controller
@RequestMapping("/admin/donation")
public class DonationController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

	@Autowired
	private DonationService donationService;
	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public DonationController()
	{
		this.viewPath = "/admin/donation/";

	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		return view;

	}

	/**
	 * 家风博物馆列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = donationService.find(currentPageNo, pageSize);

		return page;
	}

	//编辑文章
	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "detail");
		final Map<String, Object> donationMap = commonDao.findById(Donation.class, id);
		final List<Map<String, Object>> imgSrcList = new ArrayList<>();
		if (!donationMap.isEmpty() && donationMap.containsKey("imgSrc"))
		{
			final String[] imgSrcs = donationMap.get("imgSrc").toString().split(";");
			for (final String imgSrc : imgSrcs)
			{
				final Map<String, Object> tmpMap = new HashMap<>();
				tmpMap.put("imgSrc", imgSrc);

				imgSrcList.add(tmpMap);
			}
		}
		view.addObject("donation", donationMap);
		view.addObject("imgSrcList", imgSrcList);

		return view;
	}

	@RequestMapping(value = "/exportExcel")
	public void exportExcel(final HttpServletResponse response)
	{
		OutputStream out = null;
		try
		{
			final String fileName = "家风博物馆_" + DateUtils.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");

			final String title = "家风博物馆";

			final String[] header = new String[]
			{ "姓名", "联系方式", "联系地址", "简介", "捐赠方式", "时间" };

			final List<Map<String, Object>> datas = donationService.find();

			final HSSFWorkbook wb = ExcelUtils.generateExcel(title, header, datas);
			out = response.getOutputStream();
			wb.write(out);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			logger.error("导出家风博物馆excel失败");
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
