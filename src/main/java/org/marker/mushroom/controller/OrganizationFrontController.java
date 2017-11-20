package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Organization_points_log;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 文章管理
 *
 * @author marker
 */
@Controller
@RequestMapping("/front/organization")
public class OrganizationFrontController extends SupportController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationFrontController.class);

	@ResponseBody
	@RequestMapping(value = "/points/add", method = RequestMethod.POST)
	public Object add(@RequestParam("id") final Integer id,
					  @RequestParam("uuid") final String uuid,
					  final HttpServletRequest req) {

		final String ipv4 = HttpUtils.getRemoteHost(req);// 用户真实IP，处理了ngnix的代理IP

		final Organization_points_log organization_points_log = new Organization_points_log();
		organization_points_log.setAid(id);
		organization_points_log.setIp(ipv4);
		organization_points_log.setPoints(2);
		organization_points_log.setTime(new Date());

		if (commonDao.save(organization_points_log)) {
			final String sql = "update " + dbConfig.getPrefix() + "user_organization set onlinePoint = onlinePoint+"
				+ organization_points_log.getPoints() + " where orgUid=?";
			commonDao.update(sql, uuid);
			return new ResultMessage(true, "点赞成功!");
		} else {
			return new ResultMessage(false, "点赞失败!");
		}
	}

	@RequestMapping("/view")
	public ModelAndView view(final HttpServletRequest request) {
		final ModelAndView view = new ModelAndView("/organization_d");

		final String orgUid = request.getParameter("orgUid");
		final String cid2 = request.getParameter("cid2");


		view.addObject("orgUid", orgUid);
		view.addObject("cid2", cid2);
		view.addObject("articleList", request.getSession().getAttribute("articleList"));

		return view;
	}
}
