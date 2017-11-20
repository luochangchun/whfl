package org.marker.mushroom.ext.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.alias.SQL;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.WebParam;
import org.marker.mushroom.ext.model.ContentModel;
import org.marker.mushroom.template.tags.res.SqlDataSource;



/**
 * 文章模型实现
 * 
 * @author marker
 * @version 1.0
 */
public class ArticleModelImpl extends ContentModel
{


	public ArticleModelImpl()
	{
		final Map<String, Object> config = new HashMap<String, Object>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "文章模型");
		config.put("author", "marker");
		config.put("version", "0.1");
		config.put("type", "article");
		config.put("template", "article.html");
		config.put("description", "系统内置模型");
		configure(config);
	}



	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable cid)
	{
		final String prefix = getPrefix();//表前缀，如："yl_"
		final HttpServletRequest request = ActionContext.getReq();



		final String sql = "select  M.*,C.name cname, concat('/cms?','type=article','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,'%Y%m%d')) url from mr_category C "
				+ "right join mr_article M on M.cid = C.id  where  M.id=?";
		final Object article = commonDao.queryForMap(sql, cid);
		commonDao.update("update " + prefix + "article set views = views+1 where id=?", cid);// 更新浏览量

		// 必须发送数据
		request.setAttribute("article", article);
	}



	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param)
	{
		final String prefix = getPrefix();//表前缀，如："yl_" 


		final StringBuilder sql = new StringBuilder();
		sql.append(
				"select A.*,C.name as cname,concat('type=article&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as url from ")
				.append(prefix).append("article").append(SQL.QUERY_FOR_ALIAS).append(" join ").append(prefix).append("category")
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
				"select  M.*,C.name cname, concat('/cms?','type=article','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,'%Y%m%d')) url from mr_category C "
						+ "right join mr_article M on M.cid = C.id");

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
