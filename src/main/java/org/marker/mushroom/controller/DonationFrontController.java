package org.marker.mushroom.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Donation;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.GenerateUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/front/donation")
public class DonationFrontController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(DonationFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public DonationFrontController()
	{
		this.viewPath = "/front/donation/";

	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object upload(final Donation donation, @RequestParam(value = "imgSrcs", required = false) final MultipartFile[] imgSrcs,
			final HttpServletRequest request)
	{

		final StringBuilder tempImgSrc = new StringBuilder();
		donation.setTime(new Date());

		if (imgSrcs != null && imgSrcs.length > 0)
		{
			for (final MultipartFile imgSrc : imgSrcs)
			{
				try
				{
					final SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHH");
					//构建图片保存的目录
					String webAppPath = request.getSession().getServletContext().getRealPath("");
					//				System.out.println("webAppPath--old_" + webAppPath);
					final int index = webAppPath.lastIndexOf("/") > 0 ? webAppPath.lastIndexOf("/") : 0;
					webAppPath = webAppPath.substring(0, index);
					//				System.out.println("webAppPath--new_" + webAppPath);

					final String imgRealPathDir = webAppPath + "/" + syscfg.getMuseumPath() + dateformat.format(new Date());
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

					//拿到输出流，同时重命名上传的文件
					final String fileName = "/" + new Date().getTime() + "_" + GenerateUUID.getUUID() + ".jpg";
					final FileOutputStream os = new FileOutputStream(imgRealPathDir + fileName);

					//拿到上传文件的输入流
					final byte[] fileByteArr = imgSrc.getBytes();
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
					final String serverRealPath = syscfg.getMuseumPath() + dateformat.format(new Date()) + fileName;
					tempImgSrc.append(serverRealPath).append(";");
				}
				catch (final Exception e)
				{
					logger.error("DonationFrontController method save error: no file upload!");
				}
			}
		}

		if (tempImgSrc.toString().endsWith(";"))
		{
			donation.setImgSrc(tempImgSrc.toString().substring(0, tempImgSrc.length() - 1));
		}
		if (commonDao.save(donation))
		{
			return new ResultMessage(true, "提交成功!");
		}
		else
		{
			return new ResultMessage(false, "提交失败!");
		}
	}
}
