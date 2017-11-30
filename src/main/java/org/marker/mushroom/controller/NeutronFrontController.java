package org.marker.mushroom.controller;

import org.apache.commons.codec.binary.Base64;
import org.marker.mushroom.beans.ApplyForIncubator;
import org.marker.mushroom.beans.EnrolActivity;
import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.Womanwork;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.ApplyForIncubatorService;
import org.marker.mushroom.service.impl.EnrolActivityService;
import org.marker.mushroom.service.impl.MentorApplicationService;
import org.marker.mushroom.service.impl.WomanworkService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 30 on 2017/11/24.
 */
@Controller
@RequestMapping("/front/neutron")
public class NeutronFrontController extends SupportController {

	@Autowired
	private ApplyForIncubatorService incubatorService;

	@Autowired
	private EnrolActivityService activityService;

	@Autowired
	private MentorApplicationService mentorService;

	@Autowired
	private WomanworkService neutronService;

	private SystemConfig syscfg = SystemConfig.getInstance();

	@RequestMapping(value = "/incubator", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage incubator(ApplyForIncubator incubator) throws Exception {
		return incubatorService.createApplyIncubator(incubator);
	}

	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage activity(EnrolActivity activity) throws Exception {
		return activityService.createEnrolActivity(activity);
	}

	@RequestMapping(value = "/mentor", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage mentor(@RequestBody MentorApplication mentor) throws Exception {
		return mentorService.createMentorApplication(mentor);
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage join(Womanwork womanwork) throws Exception {
		return neutronService.createWomanwork(womanwork);
	}

	@ResponseBody
	@RequestMapping("/upload")
	public Object upload(@RequestBody final String imgSrc, final HttpServletRequest request)
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
				return new ResultMessage(false, "上传失败!");
			}

		}
		else
		{
			return new ResultMessage(false, "上传文件不存在!");
		}

	}
}
