package org.marker.mushroom.ext.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.alias.SQL;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.WebParam;
import org.marker.mushroom.ext.model.ContentModel;
import org.marker.mushroom.template.tags.res.SqlDataSource;
import org.marker.mushroom.utils.HttpUtils;


/**
 * 文章模型实现
 * 
 * @author marker
 * @version 1.0
 */
public class OrganizationModelImpl extends ContentModel
{


	public OrganizationModelImpl()
	{
		final Map<String, Object> config = new HashMap<String, Object>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "组织模型");
		config.put("author", "dd");
		config.put("version", "0.1");
		config.put("type", "organization");
		config.put("template", "organization.html");
		config.put("description", "系统定制模型");
		configure(config);
	}



	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable cid)
	{
		final String prefix = getPrefix();//表前缀，如："yl_"

		final HttpServletRequest req = ActionContext.getReq();

		String sql = "select  O.*,C.name cname, concat('/cms?','type=organization','&id=',CAST(O.id as char),'&time=',DATE_FORMAT(O.time,'%Y%m%d')) url from "
				+ prefix + "organization O left join " + prefix + "category C on O.cid = C.id where O.id=?";
		final Map<String, Object> map = commonDao.queryForMap(sql, cid);

		sql = "select sum(uo.offlinePoint) as offlinePonint,sum(uo.onlinePoint) as onlinePoint \n" + "from " + prefix
				+ "organization o join " + prefix + "user_organization uo on o.uuid = uo.orguid  where 1=1 and o.id=?";
		final Map<String, Object> tempMap = commonDao.queryForMap(sql, cid);

		if (!tempMap.isEmpty())
		{
			map.put("offlinePonint", tempMap.get("offlinePonint") == null ? "0" : tempMap.get("offlinePonint").toString());
			map.put("onlinePoint", tempMap.get("onlinePoint") == null ? "0" : tempMap.get("onlinePoint").toString());
		}

		if (map.get("cids") != null)
		{

			final String ipv4 = HttpUtils.getRemoteHost(req);// 用户真实IP，处理了ngnix的代理IP

			final String cids = map.get("cids").toString(); //cids字段中只放入两个值用逗号分隔，第一个为文章活动的cid,第二个是文章其它的cid
			sql = "select a.* from " + prefix + "article a where  a.cid = ? and a.status=1 order by time desc limit 0,10 ";
			final List<Map<String, Object>> activityList = commonDao.queryForList(sql, cids.split(",")[0]);

			sql = "select a.id,if(opl.points is null,0,opl.points) as points from " + prefix + "article a left join " + prefix
					+ "organization_points_log opl on a.id=opl.aid and opl.ip = ? where a.cid = ? ";
			final List<Map<String, Object>> articleList = commonDao.queryForList(sql, ipv4, cids.split(",")[1]);

			map.put("activity", activityList);
			map.put("articleList", articleList);
			map.put("cid2", cids.split(",")[1]);

			req.getSession().setAttribute("cid1", cids.split(",")[0]);
			req.getSession().setAttribute("cid2", cids.split(",")[1]);
			req.getSession().setAttribute("orgUid", map.get("uuid").toString());
			req.getSession().setAttribute("articleList", articleList);

		}

		commonDao.update("update " + prefix + "organization set views = views+1 where id=?", cid);// 更新浏览量

		// 必须发送数据
		req.setAttribute("organization", map);
	}



	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param)
	{
		final String prefix = getPrefix();//表前缀，如："yl_" 

		final StringBuilder sql = new StringBuilder();
		sql.append(
				"select A.*,C.name as cname,concat('type=organization&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as url from ")
				.append(prefix).append("organization").append(SQL.QUERY_FOR_ALIAS).append(" join ").append(prefix).append("category")
				.append(" C on A.cid=C.id ").append("where 1=1 ").append(param.extendSql).append(param.orderSql);

		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql.toString());
		//		request.setAttribute(AppStatic.WEB_APP_PAGE, );
		//		
		//		URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		//传递分页信息
		//		String nextPage = "p="+param.pageName+"&page="+currentPage.getNextPageNo();
		//		String prevPage = "p="+param.pageName+"&page="+currentPage.getPrevPageNo();
		//		request.setAttribute("nextpage", urlRewrite.encoder(nextPage));
		//		request.setAttribute("prevpage", urlRewrite.encoder(prevPage));

	}



	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 * 
	 * @param tableName
	 *           表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource)
	{
		final String prefix = dbconfig.getPrefix();// 表前缀，如："yl_" 
		final StringBuilder sql = new StringBuilder(
				"select  M.*,C.name cname, concat('/cms?','type=organization','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,'%Y%m%d')) url from "
						+ prefix + "category C " + "right join " + prefix + "organization M on M.cid = C.id");

		return sql;
	}



	/**
	 * 备份数据
	 */
	public void backup()
	{


	}



	/**
	 * 恢复数据
	 */
	public void recover()
	{

	}

}
