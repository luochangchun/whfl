package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Article;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IArticleDao;
import org.springframework.stereotype.Repository;




@Repository(DAO.ARTICLE)
public class ArticleDaoImpl extends DaoEngine implements IArticleDao
{

	@Override
	public boolean update(final Article a)
	{
		final String prefix = dbConfig.getPrefix();//获取数据库表前缀

		final StringBuilder sql = new StringBuilder();
		sql.append("update ").append(prefix).append("article").append(
				" set cid=?, title=?,keywords=?,description=?,author=?, content=?,status=?,source=?,icon=?,time=?,reject=?,auditTime=?,auditId=? where id=?");

		final int status = jdbcTemplate.update(sql.toString(), a.getCid(), a.getTitle(), a.getKeywords(), a.getDescription(),
				a.getAuthor(), a.getContent(), a.getStatus(), a.getSource(), a.getIcon(), a.getTime(), a.getReject(),
				a.getAuditTime(), a.getAuditId(), a.getId());

		return status > 0;
	}

	@Override
	public boolean topArticle(final String rid, final String level)
	{
		final String prefix = dbConfig.getPrefix(); //获取数据库前缀

		final StringBuffer sql = new StringBuffer();

		sql.append("update ");
		sql.append(prefix).append("article ");
		sql.append(" set top = ");
		sql.append(level);
		sql.append(" where 1=1 and id in (");
		sql.append(rid);
		sql.append(")");

		final int status = jdbcTemplate.update(sql.toString());

		return status > 0 ? true : false;
	}


}
