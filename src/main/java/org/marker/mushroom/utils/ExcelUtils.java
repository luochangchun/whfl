package org.marker.mushroom.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;


public class ExcelUtils
{
	private static final String defaultTitle = "武汉妇联";

	private static final Integer defaultColumnWidth = 15;

	public static HSSFWorkbook generateExcel(String title, final String[] header, final List<Map<String, Object>> datas)
	{
		// 声明一个工作薄
		final HSSFWorkbook wb = new HSSFWorkbook();
		if (title.equals(""))
		{
			title = defaultTitle;
		}
		// 生成一个表格
		final HSSFSheet sheet = wb.createSheet(title);

		sheet.setDefaultColumnWidth(defaultColumnWidth);

		// 生成一个样式
		final HSSFCellStyle style = wb.createCellStyle();

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		createExcelHeader(header, sheet, style);

		if (CollectionUtils.isNotEmpty(datas))
		{
			createExcelRow(datas, sheet, style);
		}
		else
		{
			sheet.createRow(1).createCell(0).setCellValue("暂无记录");
		}

		return wb;
	}

	/**
	 * @param header
	 * @param sheet
	 * @param style
	 */
	private static void createExcelHeader(final String[] header, final HSSFSheet sheet, final HSSFCellStyle style)
	{
		// 生成表格标题行
		final HSSFRow row = sheet.createRow(0);
		if (header != null && header.length > 0)
		{
			for (int i = 0; i < header.length; i++)
			{
				final HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(header[i]);
			}
		}
	}

	/**
	 * @param datas
	 * @param sheet
	 * @param style
	 */
	private static void createExcelRow(final List<Map<String, Object>> datas, final HSSFSheet sheet, final HSSFCellStyle style)
	{
		int i = 1;

		for (final Map<String, Object> data : datas)
		{
			int j = 0;
			final HSSFRow row = sheet.createRow(i);

			for (final Map.Entry<String, Object> entry : data.entrySet())
			{
				final HSSFCell cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(entry.getValue() == null ? "" : entry.getValue().toString());
				j++;
			}
			i++;
		}
	}


}
