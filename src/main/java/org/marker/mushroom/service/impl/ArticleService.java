package org.marker.mushroom.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Article;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service(Services.ARTICLE)
public class ArticleService extends BaseService
{


	@Autowired
	private ISupportDao commonDao;



	public Page find(final int currentPageNo, final int pageSize, final Map<String, Object> condition)
	{
		String cids = "";
		String keyword = (String) condition.get("keyword");
		final String status = (String) condition.get("status");
		final String cid = (String) condition.get("cid");
		final Integer userId = (Integer) condition.get("userid");
		final Integer groupid = (Integer) condition.get("groupid");
		final String audit = (String) condition.get("audit");
		final String usercategory = (String) condition.get("usercategory");
		try
		{
			keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		final Page page;
		if (groupid == 1 && StringUtils.isEmpty(cid) && StringUtils.isEmpty(audit))
		{

			final String sql = "select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d'))  url, c.name as cname ,c.model,u.name as uname from "
					+ config.getPrefix() + "article as a " + "left join " + config.getPrefix()
					+ "category c on c.id = a.cid left join " + config.getPrefix() + "user as u on a.auditId=u.id "
					+ "where a.status in (" + status + ") and a.title like ? order by a.id desc";
			page = commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%');
		}
		else if (!StringUtils.isEmpty(audit) && audit.equals("1") || (!StringUtils.isEmpty(cid) && groupid == 1))
		{
			if (!StringUtils.isEmpty(cid))
			{
				cids += cid;
			}
			else
			{
				cids += usercategory;
			}
			final String sql = "select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d'))  url, c.name  as cname ,c.model,u.name as uname from "
					+ config.getPrefix() + "article as a " + "left join " + config.getPrefix()
					+ "category c on c.id = a.cid left join " + config.getPrefix() + "user as u on a.auditId=u.id  "
					+ "where a.status in (" + status + ") and a.cid in (" + cids + ") " + " and a.title like ? order by a.id desc";

			page = commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%');
		}
		else
		{
			if (!StringUtils.isEmpty(cid))
			{
				cids += cid;
			}
			else
			{
				cids += usercategory;
			}
			final String sql = "select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d'))  url, c.name  as cname ,c.model,u.name as uname from "
					+ config.getPrefix() + "article as a " + "left join " + config.getPrefix()
					+ "category c on c.id = a.cid left join " + config.getPrefix() + "user as u on a.auditId=u.id  "
					+ "where a.status in (" + status + ") and a.cid in (" + cids + ") and a.userId = " + userId
					+ " and a.title like ? order by a.id desc";

			page = commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%');
		}
		return page;
	}

	public Page getMyAuditForPage(final int currentPageNo, final int pageSize, final Map<String, Object> condition)
	{
		String cids = "";
		String keyword = (String) condition.get("keyword");
		final String status = (String) condition.get("status");
		final String cid = (String) condition.get("cid");
		final Integer userId = (Integer) condition.get("userid");
		final String usercategory = (String) condition.get("usercategory");
		try
		{
			keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		if (cid != null && cid.isEmpty())
		{
			cids = usercategory;
		}
		else
		{
			cids = cid;
		}
		final String sql = "select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d'))  url, c.name as cname ,c.model,u.name as uname from "
				+ config.getPrefix() + "article as a " + "left join " + config.getPrefix() + "category c on c.id = a.cid left join "
				+ config.getPrefix() + "user u on u.id=a.auditId " + "where a.status in (" + status + ") and a.cid in (" + cids
				+ ")  and a.title like ? and a.auditId = ? order by a.id desc";

		return commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%', userId);

	}

	public List<Map<String, Object>> findMapArticleByCid(final int cid)
	{
		final String sql = "select * from " + config.getPrefix() + "article as m1 where 1=1 and m1.status=1 and m1.cid=?";
		return commonDao.queryForList(sql, cid);
	}

	public Article findArticleById(final Serializable id)
	{
		final String sql = "select * from " + config.getPrefix() + "article as m1 where 1=1 and m1.status=1 and m1.id=?";
		return commonDao.queryForObject(sql, Article.class, id);
	}

	public List<Map<String, Object>> findListArticleStatistics(final Integer userId, final Integer groupId, String userName,
			final String beginDate, final String endDate)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append(" select u.name as userName, ").append("\n");
		sql.append(" sum(if(m.status=0,1,0)) as wait_release, ").append("\n");
		sql.append(" sum(if(m.status=1,1,0)) as already_release, ").append("\n");
		sql.append(" sum(if(m.status=2,1,0)) as wait_audit, ").append("\n");
		sql.append(" sum(if(m.status=3,1,0)) as rejected ").append("\n");
		sql.append(" from ");
		sql.append(config.getPrefix());
		sql.append("article m ");
		sql.append(" join ");
		sql.append(config.getPrefix());
		sql.append("user u on m.userId = u.id ");
		sql.append(" where 1=1 ");
		if (groupId != null && groupId != 1)
		{
			sql.append(" and m.userId = ").append(userId);
		}
		if (!StringUtils.isEmpty(userName))
		{
			try
			{
				userName = new String(userName.getBytes("ISO-8859-1"), "utf-8");
			}
			catch (final UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			sql.append(" and u.name like '%" + userName + "%' ");
		}

		if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate))
		{
			sql.append(" and DATE_FORMAT(m.createTime,'%Y-%m-%d') BETWEEN '" + beginDate + "' and '" + endDate + "' ");
		}

		sql.append(" group by u.name ");
		return commonDao.queryForList(sql.toString());
	}

	public List<Map<String, Object>> findListArticleStatistics(final Integer userId, final Integer groupId)
	{
		return findListArticleStatistics(userId, groupId, "", "", "");
	}

	public Map<String, Object> totalAccess()
	{
		final String sql = "select count(*) views from " + config.getPrefix() + "visited_his";
		return commonDao.queryForMap(sql);
	}

	public Map<String, Object> todayAccess()
	{
		final String sql = "select count(*) views from " + config.getPrefix()
				+ "visited_his_today vh where DATE_FORMAT(vh.time,'%y%m%d')= DATE_FORMAT(now(),'%y%m%d')";
		return commonDao.queryForMap(sql);
	}

	public void addPoints(final Article article)
	{
		String sql = "select u.uuid from " + config.getPrefix() + "user u join " + config.getPrefix() + "category c join "
				+ config.getPrefix() + "article a  where u.id=a.userid and c.id=a.cid and c.sort=99 and a.id=?";
		try
		{
			final Map<String, Object> map = commonDao.queryForMap(sql, article.getId());
			if (!map.isEmpty())
			{
				final String uuid = map.get("uuid") == null ? "" : map.get("uuid").toString();
				if (!uuid.equals(""))
				{
					sql = "update " + config.getPrefix()
							+ "user_organization uo set uo.onlinePoint = uo.onlinePoint+1 where uo.uuid=?";
					commonDao.update(sql, uuid);
				}
			}
		}
		catch (final Exception e)
		{
		}

	}

}
