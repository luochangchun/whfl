/**
 *  
 *  吴伟 版权所有
 */
package org.marker.mushroom.servlet;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 
 * 统计访问数据接口
 * 
 * @author marker
 * @date 2013-12-2 下午8:02:53
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class FetchServlet extends HttpServlet
{

	private static final long serialVersionUID = 2990324920926049103L;


	private static final List<String> list = Collections.synchronizedList(new ArrayList<String>(30));


	private final DataBaseConfig dbconfig = DataBaseConfig.getInstance();

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
	{

		//		String baseURL = req.getAttribute(AppStatic.WEB_APP_URL).toString();

		final String leave = req.getParameter("leave");// 是否离开

		if (!"1".equals(leave))
		{
			//总会有值是空的情况，如果是空的值就给设置为-1
			String url = req.getParameter("v0");// 当前url
			if (url == null || url.equals(""))
			{
				url = "-1";
			}
			String referer = req.getParameter("v1");// 旧URL
			if (referer == null || referer.equals(""))
			{
				referer = "-1";
			}
			String system = req.getParameter("v2");// 操作系统
			if (system == null || system.equals(""))
			{
				system = "-1";
			}
			String screen = req.getParameter("v3");// 屏幕分辨率
			if (screen == null || screen.equals(""))
			{
				screen = "-1";
			}
			String browser = req.getParameter("v4");// 浏览器
			if (browser == null || browser.equals(""))
			{
				browser = "-1";
			}
			String bVersion = req.getParameter("v5");// 浏览器
			if (bVersion == null || bVersion.equals(""))
			{
				bVersion = "-1";
			}
			String language = req.getParameter("v6");// 浏览器语言
			if (language == null || language.equals(""))
			{
				language = "-1";
			}
			final String isFlash = req.getParameter("v7");// 是否安装Flash
			String ipv4 = HttpUtils.getRemoteHost(req);// 用户真实IP，处理了ngnix的代理IP
			if (ipv4 == null || ipv4.equals(""))
			{
				ipv4 = "-1";
			}
			final String visitorId = HttpUtils.getCookie(req, "FETCHSESSIONID");// UV统计使用


			final StringBuilder data = new StringBuilder();
			data.append(ipv4).append(",");
			data.append(language).append(",");
			data.append(browser).append(",");
			data.append(screen).append(",");
			data.append(url).append(",");
			data.append(referer).append(",");
			data.append(bVersion).append(",");
			data.append(system).append(",");
			//			data.append(visitorId).append(",");



			final String prefix = dbconfig.getPrefix();
			final ISupportDao dao = SpringContextHolder.getBean(DAO.COMMON);

			if (list.size() < 5 - 1)
			{
				list.add(data.toString());
			}
			else
			{// 满了，就批量推入数据库
				list.add(data.toString());
				final List<Object[]> params = new ArrayList<>();
				for (final String f : list)
				{
					params.add(f.split(","));
				}

				final StringBuilder sql = new StringBuilder();
				sql.append("insert into ").append(prefix).append("visited_his");
				sql.append("(`ip`,`language`,`browser`,`screen`,`url`,`referer`, `version`,`system`,`time`)")
						.append(" values(?,?,?,?,?,?,?,?,now())");

				dao.batchUpdate(sql.toString(), params);

				sql.delete(0, sql.length());
				sql.append(" select count(*) views from ");
				sql.append(prefix).append("visited_his_today ");
				sql.append(" vht where DATE_FORMAT(vht.time,'%y%m%d')= DATE_FORMAT(now(),'%y%m%d') ");
				final Map<String, Object> map = dao.queryForMap(sql.toString());
				if (map != null)
				{
					final String views = map.get("views") == null ? "0" : map.get("views").toString();
					if (views.equals("0"))
					{
						sql.delete(0, sql.length());
						sql.append("truncate mr_visited_his_today");
						dao.execute(sql.toString());
					}
				}
				sql.delete(0, sql.length());
				sql.append("insert into ").append(prefix).append("visited_his_today");
				sql.append("(`ip`,`language`,`browser`,`screen`,`url`,`referer`, `version`,`system`,`time`)")
						.append(" values(?,?,?,?,?,?,?,?,now())");

				dao.batchUpdate(sql.toString(), params);

				list.clear();
			}
		}
		else
		{// 离开

			System.out.println("用户已经离开");

		}

	}
}
